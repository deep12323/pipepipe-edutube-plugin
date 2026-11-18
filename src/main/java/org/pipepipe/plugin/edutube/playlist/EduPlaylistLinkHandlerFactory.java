package org.pipepipe.plugin.edutube.playlist;

import org.schabi.newpipe.extractor.ServiceList;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.search.filter.FilterItem;

import java.util.List;

public final class EduPlaylistLinkHandlerFactory extends ListLinkHandlerFactory {

    @Override
    public String getUrl(final String id,
                         final List<FilterItem> contentFilter,
                         final List<FilterItem> sortFilter) throws ParsingException {
        return ServiceList.YouTube.getPlaylistLHFactory().getUrl(id, contentFilter, sortFilter);
    }

    @Override
    public String getId(final String url) throws ParsingException {
        return ServiceList.YouTube.getPlaylistLHFactory().getId(url);
    }

    @Override
    public boolean onAcceptUrl(final String url) throws ParsingException {
        return ServiceList.YouTube.getPlaylistLHFactory().onAcceptUrl(url);
    }
}
