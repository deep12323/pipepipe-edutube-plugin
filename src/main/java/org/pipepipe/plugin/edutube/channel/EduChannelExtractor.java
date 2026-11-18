package org.pipepipe.plugin.edutube.channel;

import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.ServiceList;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.channel.ChannelExtractor;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.pipepipe.plugin.edutube.filter.EducationalClassifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public final class EduChannelExtractor extends ChannelExtractor {

    private final ChannelExtractor delegate;
    private final EducationalClassifier classifier;

    public EduChannelExtractor(final StreamingService service,
                               final ListLinkHandler linkHandler,
                               final EducationalClassifier classifier) throws ExtractionException {
        super(service, linkHandler);
        this.classifier = classifier;
        final ListLinkHandler ytHandler = ServiceList.YouTube.getChannelLHFactory().fromUrl(linkHandler.getUrl());
        this.delegate = ServiceList.YouTube.getChannelExtractor(ytHandler);
    }

    @Override
    public void onFetchPage(@Nonnull final Downloader downloader) throws IOException, ExtractionException {
        if (delegate != null) {
            delegate.fetchPage();
        }
    }

    @Nonnull
    @Override
    public String getName() throws ParsingException {
        return delegate != null ? delegate.getName() : "Educational Channel";
    }

    @Override
    public String getAvatarUrl() throws ParsingException {
        return delegate != null ? delegate.getAvatarUrl() : "";
    }

    @Override
    public String getBannerUrl() throws ParsingException {
        return delegate != null ? delegate.getBannerUrl() : "";
    }

    @Override
    public long getSubscriberCount() throws ParsingException {
        return delegate != null ? delegate.getSubscriberCount() : UNKNOWN_SUBSCRIBER_COUNT;
    }

    @Override
    public String getDescription() throws ParsingException {
        return delegate != null ? delegate.getDescription() : "";
    }

    @Override
    public boolean isVerified() throws ParsingException {
        return delegate != null && delegate.isVerified();
    }

    @Override
    public List<ListLinkHandler> getTabs() throws ParsingException {
        return delegate != null ? delegate.getTabs() : List.of();
    }

    @Nonnull
    @Override
    public InfoItemsPage<StreamInfoItem> getInitialPage() throws IOException, ExtractionException {
        if (delegate == null) {
            return InfoItemsPage.emptyPage();
        }
        return filterPage(delegate.getInitialPage());
    }

    @Override
    public InfoItemsPage<StreamInfoItem> getPage(final Page page) throws IOException, ExtractionException {
        if (delegate == null) {
            return InfoItemsPage.emptyPage();
        }
        return filterPage(delegate.getPage(page));
    }

    private InfoItemsPage<StreamInfoItem> filterPage(final InfoItemsPage<StreamInfoItem> raw) {
        if (raw == null || raw.getItems() == null) {
            return InfoItemsPage.emptyPage();
        }
        final List<StreamInfoItem> filtered = new ArrayList<>();
        int filteredCount = raw.getFilteredCount();

        for (final StreamInfoItem item : raw.getItems()) {
            if (classifier.isAllowed(item)) {
                final StreamInfoItem adapted = new StreamInfoItem(
                        getServiceId(),
                        item.getUrl(),
                        item.getName(),
                        item.getStreamType()
                );
                adapted.setUploaderName(item.getUploaderName());
                adapted.setUploaderUrl(item.getUploaderUrl());
                adapted.setThumbnailUrl(item.getThumbnailUrl());
                adapted.setDuration(item.getDuration());
                adapted.setViewCount(item.getViewCount());
                adapted.setShortDescription(item.getShortDescription());
                adapted.setUploaderVerified(item.isUploaderVerified());
                filtered.add(adapted);
            } else {
                filteredCount++;
            }
        }

        return new InfoItemsPage<>(filtered, raw.getNextPage(), raw.getErrors(), filteredCount);
    }
}
