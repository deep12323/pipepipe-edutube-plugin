package org.pipepipe.plugin.edutube;

import static org.schabi.newpipe.extractor.StreamingService.ServiceInfo.MediaCapability.VIDEO;

import org.schabi.newpipe.extractor.ServiceList;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.channel.ChannelExtractor;
import org.schabi.newpipe.extractor.channel.ChannelTabExtractor;
import org.schabi.newpipe.extractor.comments.CommentsExtractor;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.kiosk.KioskList;
import org.schabi.newpipe.extractor.linkhandler.LinkHandler;
import org.schabi.newpipe.extractor.linkhandler.LinkHandlerFactory;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandler;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandlerFactory;
import org.schabi.newpipe.extractor.playlist.PlaylistExtractor;
import org.schabi.newpipe.extractor.plugin.PluginPreferences;
import org.schabi.newpipe.extractor.search.SearchExtractor;
import org.schabi.newpipe.extractor.stream.StreamExtractor;
import org.schabi.newpipe.extractor.subscription.SubscriptionExtractor;
import org.schabi.newpipe.extractor.suggestion.SuggestionExtractor;
import org.pipepipe.plugin.edutube.channel.EduChannelExtractor;
import org.pipepipe.plugin.edutube.channel.EduChannelLinkHandlerFactory;
import org.pipepipe.plugin.edutube.filter.AntiBrainrotFilter;
import org.pipepipe.plugin.edutube.filter.EducationalClassifier;
import org.pipepipe.plugin.edutube.kiosk.EduKioskExtractor;
import org.pipepipe.plugin.edutube.kiosk.EduKioskLinkHandlerFactory;
import org.pipepipe.plugin.edutube.playlist.EduPlaylistExtractor;
import org.pipepipe.plugin.edutube.playlist.EduPlaylistLinkHandlerFactory;
import org.pipepipe.plugin.edutube.search.EduSearchExtractor;
import org.pipepipe.plugin.edutube.search.EduSearchQueryHandlerFactory;
import org.pipepipe.plugin.edutube.stream.EduStreamLinkHandlerFactory;

import java.util.List;

public final class EduTubeService extends StreamingService {

    public static final String SERVICE_NAME = "EduTube";
    public static final String BASE_URL = "https://www.youtube.com";

    private final PluginPreferences preferences;
    private final AntiBrainrotFilter antiBrainrotFilter;
    private final EducationalClassifier educationalClassifier;

    public EduTubeService(final int serviceId, final PluginPreferences preferences) {
        super(serviceId, SERVICE_NAME, List.of(VIDEO));
        this.preferences = preferences;
        this.antiBrainrotFilter = new AntiBrainrotFilter(preferences);
        this.educationalClassifier = new EducationalClassifier(antiBrainrotFilter, preferences);
    }

    @Override
    public String getBaseUrl() {
        return BASE_URL;
    }

    @Override
    public LinkHandlerFactory getStreamLHFactory() {
        return new EduStreamLinkHandlerFactory();
    }

    @Override
    public ListLinkHandlerFactory getChannelLHFactory() {
        return new EduChannelLinkHandlerFactory();
    }

    @Override
    public ListLinkHandlerFactory getChannelTabLHFactory() {
        return null;
    }

    @Override
    public ListLinkHandlerFactory getPlaylistLHFactory() {
        return new EduPlaylistLinkHandlerFactory();
    }

    @Override
    public SearchQueryHandlerFactory getSearchQHFactory() {
        return new EduSearchQueryHandlerFactory();
    }

    @Override
    public ListLinkHandlerFactory getCommentsLHFactory() {
        // Disabled by default to prevent distraction & doom-scrolling in comment sections
        return null;
    }

    @Override
    public SearchExtractor getSearchExtractor(final SearchQueryHandler queryHandler) {
        return new EduSearchExtractor(this, queryHandler, educationalClassifier, antiBrainrotFilter);
    }

    @Override
    public SuggestionExtractor getSuggestionExtractor() {
        return null;
    }

    @Override
    public SubscriptionExtractor getSubscriptionExtractor() {
        return null;
    }

    @Override
    public KioskList getKioskList() throws ExtractionException {
        try {
            final KioskList kioskList = new KioskList(this);

            // 1. Featured University Courses (Default)
            final EduKioskLinkHandlerFactory featuredFactory =
                    new EduKioskLinkHandlerFactory(EduKioskExtractor.KIOSK_FEATURED);
            kioskList.addKioskEntry(
                    (streamingService, url, id) -> new EduKioskExtractor(
                            streamingService,
                            featuredFactory.fromUrl(url),
                            EduKioskExtractor.KIOSK_FEATURED,
                            educationalClassifier
                    ),
                    featuredFactory,
                    EduKioskExtractor.KIOSK_FEATURED
            );

            // 2. Computer Science & Programming
            final EduKioskLinkHandlerFactory csFactory =
                    new EduKioskLinkHandlerFactory(EduKioskExtractor.KIOSK_CS);
            kioskList.addKioskEntry(
                    (streamingService, url, id) -> new EduKioskExtractor(
                            streamingService,
                            csFactory.fromUrl(url),
                            EduKioskExtractor.KIOSK_CS,
                            educationalClassifier
                    ),
                    csFactory,
                    EduKioskExtractor.KIOSK_CS
            );

            // 3. Mathematics & Physics
            final EduKioskLinkHandlerFactory stemFactory =
                    new EduKioskLinkHandlerFactory(EduKioskExtractor.KIOSK_STEM);
            kioskList.addKioskEntry(
                    (streamingService, url, id) -> new EduKioskExtractor(
                            streamingService,
                            stemFactory.fromUrl(url),
                            EduKioskExtractor.KIOSK_STEM,
                            educationalClassifier
                    ),
                    stemFactory,
                    EduKioskExtractor.KIOSK_STEM
            );

            // 4. Engineering & Applied Sciences
            final EduKioskLinkHandlerFactory engFactory =
                    new EduKioskLinkHandlerFactory(EduKioskExtractor.KIOSK_ENGINEERING);
            kioskList.addKioskEntry(
                    (streamingService, url, id) -> new EduKioskExtractor(
                            streamingService,
                            engFactory.fromUrl(url),
                            EduKioskExtractor.KIOSK_ENGINEERING,
                            educationalClassifier
                    ),
                    engFactory,
                    EduKioskExtractor.KIOSK_ENGINEERING
            );

            // 5. Humanities & Philosophy
            final EduKioskLinkHandlerFactory humFactory =
                    new EduKioskLinkHandlerFactory(EduKioskExtractor.KIOSK_HUMANITIES);
            kioskList.addKioskEntry(
                    (streamingService, url, id) -> new EduKioskExtractor(
                            streamingService,
                            humFactory.fromUrl(url),
                            EduKioskExtractor.KIOSK_HUMANITIES,
                            educationalClassifier
                    ),
                    humFactory,
                    EduKioskExtractor.KIOSK_HUMANITIES
            );

            kioskList.setDefaultKiosk(EduKioskExtractor.KIOSK_FEATURED);
            return kioskList;
        } catch (final Exception e) {
            throw new ExtractionException("Failed to register kiosks", e);
        }
    }

    @Override
    public ChannelExtractor getChannelExtractor(final ListLinkHandler linkHandler) throws ExtractionException {
        return new EduChannelExtractor(this, linkHandler, educationalClassifier);
    }

    @Override
    public ChannelTabExtractor getChannelTabExtractor(final ListLinkHandler linkHandler) throws ExtractionException {
        throw new ExtractionException("Channel tabs not supported");
    }

    @Override
    public PlaylistExtractor getPlaylistExtractor(final ListLinkHandler linkHandler) throws ExtractionException {
        return new EduPlaylistExtractor(this, linkHandler, educationalClassifier);
    }

    @Override
    public StreamExtractor getStreamExtractor(final LinkHandler linkHandler) throws ExtractionException {
        final LinkHandler ytHandler = ServiceList.YouTube.getStreamLHFactory().fromUrl(linkHandler.getUrl());
        return ServiceList.YouTube.getStreamExtractor(ytHandler);
    }

    @Override
    public CommentsExtractor getCommentsExtractor(final ListLinkHandler linkHandler) throws ExtractionException {
        throw new ExtractionException("Comments are disabled for distraction-free learning");
    }
}
