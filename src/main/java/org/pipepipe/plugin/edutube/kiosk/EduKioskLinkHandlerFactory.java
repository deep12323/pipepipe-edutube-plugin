package org.pipepipe.plugin.edutube.kiosk;

import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.search.filter.FilterItem;

import java.util.List;

public final class EduKioskLinkHandlerFactory extends ListLinkHandlerFactory {

    private final String kioskId;

    public EduKioskLinkHandlerFactory(final String kioskId) {
        this.kioskId = kioskId;
    }

    @Override
    public String getUrl(final String id,
                         final List<FilterItem> contentFilter,
                         final List<FilterItem> sortFilter) throws ParsingException {
        return "https://edutube.local/kiosk/" + (id != null && !id.isEmpty() ? id : kioskId);
    }

    @Override
    public String getId(final String url) throws ParsingException {
        if (url != null && url.startsWith("https://edutube.local/kiosk/")) {
            return url.substring("https://edutube.local/kiosk/".length());
        }
        return kioskId;
    }

    @Override
    public boolean onAcceptUrl(final String url) throws ParsingException {
        return url != null && url.startsWith("https://edutube.local/kiosk/" + kioskId);
    }
}
