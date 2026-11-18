package org.pipepipe.plugin.edutube.search;

import org.junit.jupiter.api.Test;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.ServiceList;
import org.schabi.newpipe.extractor.downloader.CancellableCall;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.downloader.Request;
import org.schabi.newpipe.extractor.downloader.Response;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandler;
import org.schabi.newpipe.extractor.search.filter.FilterItem;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeSearchQueryHandlerFactory;
import org.pipepipe.plugin.edutube.EduTubeService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EduSearchExtractorTest {

    @Test
    void testYoutubeSearchHandler() throws Exception {
        Downloader mockDownloader = new Downloader() {
            @Override
            public Response execute(Request request) throws IOException {
                return new Response(200, "OK", Collections.emptyMap(), "{}", new byte[0], request.url());
            }

            @Override
            public CancellableCall executeAsync(Request request, AsyncCallback callback) {
                return null;
            }
        };
        NewPipe.init(mockDownloader);

        YoutubeSearchQueryHandlerFactory factory = (YoutubeSearchQueryHandlerFactory) ServiceList.YouTube.getSearchQHFactory();
        FilterItem allItem = factory.getFilterItem(0);
        assertNotNull(allItem);

        SearchQueryHandler handler = factory.fromQuery("ethical hacking", List.of(allItem), null);
        assertNotNull(handler.getUrl());
        System.out.println("Handler URL: " + handler.getUrl());

        // Test getting SearchExtractor from YoutubeService
        var ytExtractor = ServiceList.YouTube.getSearchExtractor(handler);
        assertNotNull(ytExtractor);
        System.out.println("SearchExtractor created: " + ytExtractor.getClass().getName());

        // Test EduTubeService search extractor creation
        EduTubeService eduService = new EduTubeService(999, null);
        SearchQueryHandler eduHandler = eduService.getSearchQHFactory().fromQuery("ethical hacking");
        EduSearchExtractor eduExtractor = (EduSearchExtractor) eduService.getSearchExtractor(eduHandler);
        assertNotNull(eduExtractor);
    }
}
