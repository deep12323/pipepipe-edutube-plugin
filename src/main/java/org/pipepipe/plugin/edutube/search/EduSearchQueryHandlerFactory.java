package org.pipepipe.plugin.edutube.search;

import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandlerFactory;
import org.schabi.newpipe.extractor.search.filter.FilterItem;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public final class EduSearchQueryHandlerFactory extends SearchQueryHandlerFactory {

    @Override
    public String getUrl(final String query,
                         final List<FilterItem> contentFilter,
                         final List<FilterItem> sortFilter) throws ParsingException {
        try {
            final String encoded = URLEncoder.encode(query != null ? query : "", StandardCharsets.UTF_8.name());
            return "https://www.youtube.com/results?search_query=" + encoded;
        } catch (final UnsupportedEncodingException e) {
            throw new ParsingException("Could not encode search query", e);
        }
    }
}
