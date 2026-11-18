package org.pipepipe.plugin.edutube.stream;

import org.schabi.newpipe.extractor.ServiceList;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.LinkHandlerFactory;

public final class EduStreamLinkHandlerFactory extends LinkHandlerFactory {

    @Override
    public String getUrl(final String id) throws ParsingException {
        return ServiceList.YouTube.getStreamLHFactory().getUrl(id);
    }

    @Override
    public String getId(final String url) throws ParsingException, IllegalArgumentException {
        return ServiceList.YouTube.getStreamLHFactory().getId(url);
    }

    @Override
    public boolean onAcceptUrl(final String url) throws ParsingException {
        return ServiceList.YouTube.getStreamLHFactory().onAcceptUrl(url);
    }
}
