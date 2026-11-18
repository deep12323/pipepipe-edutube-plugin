package org.pipepipe.plugin.edutube.search;

import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.ServiceList;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandler;
import org.schabi.newpipe.extractor.search.SearchExtractor;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.pipepipe.plugin.edutube.filter.AntiBrainrotFilter;
import org.pipepipe.plugin.edutube.filter.EducationalClassifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nonnull;

/**
 * Educational Search Extractor that routes queries through YouTube's search engine,
 * intercepts and filters out entertainment, brain-rot memes, clickbait, and shorts,
 * and surfaces only genuine educational courses and academic videos.
 */
public final class EduSearchExtractor extends SearchExtractor {

    private final EducationalClassifier classifier;
    private final AntiBrainrotFilter brainrotFilter;
    private SearchExtractor delegateYtExtractor;
    private boolean isBlockedBrainrotQuery = false;

    public EduSearchExtractor(final StreamingService service,
                              final SearchQueryHandler queryHandler,
                              final EducationalClassifier classifier,
                              final AntiBrainrotFilter brainrotFilter) {
        super(service, queryHandler);
        this.classifier = classifier;
        this.brainrotFilter = brainrotFilter;
    }

    @Override
    public void onFetchPage(@Nonnull final Downloader downloader) throws IOException, ExtractionException {
        final String query = getSearchString() != null ? getSearchString().trim() : "";

        // Proactively detect if user searches for brain-rot terms directly
        if (isBlatantBrainrotQuery(query)) {
            this.isBlockedBrainrotQuery = true;
            return;
        }

        try {
            final SearchQueryHandler ytHandler = ServiceList.YouTube
                    .getSearchQHFactory()
                    .fromQuery(query);

            this.delegateYtExtractor = ServiceList.YouTube.getSearchExtractor(ytHandler);
            if (this.delegateYtExtractor != null) {
                this.delegateYtExtractor.fetchPage();
            }
        } catch (final Exception e) {
            throw new ExtractionException("Failed to initialize educational search", e);
        }
    }

    private boolean isBlatantBrainrotQuery(final String query) {
        if (query.isEmpty()) {
            return false;
        }
        final String lower = query.toLowerCase(Locale.ROOT);
        final String[] terms = {
                "skibidi", "gyatt", "rizz", "fanum tax", "sigma male", "mewing",
                "kai cenat", "ishowspeed", "prank", "cops called", "gone wrong",
                "mrbeast", "mr beast", "tiktok drama", "asmr eating", "mukbang",
                "try not to laugh", "roblox trolling", "reaction mashup"
        };
        for (final String term : terms) {
            if (lower.contains(term)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected InfoItemsPage<InfoItem> getInitialPageInternal() throws IOException, ExtractionException {
        if (isBlockedBrainrotQuery || delegateYtExtractor == null) {
            return InfoItemsPage.emptyPage();
        }

        final InfoItemsPage<InfoItem> rawPage = delegateYtExtractor.getInitialPage();
        return filterAndAdaptPage(rawPage);
    }

    @Override
    protected InfoItemsPage<InfoItem> getPageInternal(final Page page) throws IOException, ExtractionException {
        if (isBlockedBrainrotQuery || delegateYtExtractor == null) {
            return InfoItemsPage.emptyPage();
        }

        final InfoItemsPage<InfoItem> rawPage = delegateYtExtractor.getPage(page);
        return filterAndAdaptPage(rawPage);
    }

    private InfoItemsPage<InfoItem> filterAndAdaptPage(final InfoItemsPage<InfoItem> rawPage) {
        if (rawPage == null || rawPage.getItems() == null) {
            return InfoItemsPage.emptyPage();
        }

        final List<InfoItem> filtered = new ArrayList<>();
        int filteredCount = rawPage.getFilteredCount();

        for (final InfoItem item : rawPage.getItems()) {
            if (classifier.isAllowed(item)) {
                // Adapt item to use EduTube service ID so navigation stays in EduTube
                filtered.add(adaptItem(item));
            } else {
                filteredCount++;
            }
        }

        return new InfoItemsPage<>(filtered, rawPage.getNextPage(), rawPage.getErrors(), filteredCount);
    }

    private InfoItem adaptItem(final InfoItem item) {
        if (item instanceof StreamInfoItem) {
            final StreamInfoItem orig = (StreamInfoItem) item;
            final StreamInfoItem adapted = new StreamInfoItem(
                    getServiceId(),
                    orig.getUrl(),
                    orig.getName(),
                    orig.getStreamType()
            );
            adapted.setUploaderName(orig.getUploaderName());
            adapted.setUploaderUrl(orig.getUploaderUrl());
            adapted.setThumbnailUrl(orig.getThumbnailUrl());
            adapted.setDuration(orig.getDuration());
            adapted.setViewCount(orig.getViewCount());
            adapted.setShortDescription(orig.getShortDescription());
            adapted.setUploaderVerified(orig.isUploaderVerified());
            return adapted;
        }
        return item;
    }

    @Nonnull
    @Override
    public String getSearchSuggestion() throws ParsingException {
        if (delegateYtExtractor != null) {
            return delegateYtExtractor.getSearchSuggestion();
        }
        return "";
    }

    @Override
    public boolean isCorrectedSearch() throws ParsingException {
        if (delegateYtExtractor != null) {
            return delegateYtExtractor.isCorrectedSearch();
        }
        return false;
    }
}
