package org.pipepipe.plugin.edutube.kiosk;

import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.kiosk.KioskExtractor;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.pipepipe.plugin.edutube.data.CuratedCourses;
import org.pipepipe.plugin.edutube.filter.EducationalClassifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

public final class EduKioskExtractor extends KioskExtractor<InfoItem> {

    public static final String KIOSK_FEATURED = "featured_courses";
    public static final String KIOSK_CS = "cs_programming";
    public static final String KIOSK_STEM = "stem_math_physics";
    public static final String KIOSK_ENGINEERING = "engineering_tech";
    public static final String KIOSK_HUMANITIES = "humanities_philosophy";

    private final String kioskId;
    private final EducationalClassifier classifier;
    private List<InfoItem> items = Collections.emptyList();

    public EduKioskExtractor(final @Nonnull StreamingService service,
                             final @Nonnull ListLinkHandler linkHandler,
                             final @Nonnull String kioskId,
                             final @Nonnull EducationalClassifier classifier) {
        super(service, linkHandler, kioskId);
        this.kioskId = kioskId;
        this.classifier = classifier;
    }

    @Override
    public void onFetchPage(@Nonnull final Downloader downloader) throws IOException, ExtractionException {
        final List<InfoItem> raw;
        switch (kioskId) {
            case KIOSK_CS:
                raw = CuratedCourses.getComputerScienceCourses(getServiceId());
                break;
            case KIOSK_STEM:
                raw = CuratedCourses.getStemCourses(getServiceId());
                break;
            case KIOSK_ENGINEERING:
                raw = CuratedCourses.getEngineeringCourses(getServiceId());
                break;
            case KIOSK_HUMANITIES:
                raw = CuratedCourses.getHumanitiesCourses(getServiceId());
                break;
            case KIOSK_FEATURED:
            default:
                raw = CuratedCourses.getUniversityCourses(getServiceId());
                break;
        }

        final List<InfoItem> filtered = new ArrayList<>();
        for (final InfoItem item : raw) {
            if (classifier.isAllowed(item)) {
                filtered.add(item);
            }
        }
        this.items = filtered;
    }

    @Nonnull
    @Override
    public String getName() throws ParsingException {
        switch (kioskId) {
            case KIOSK_CS:
                return "Computer Science & Programming";
            case KIOSK_STEM:
                return "Mathematics & Physics";
            case KIOSK_ENGINEERING:
                return "Engineering & Applied Sciences";
            case KIOSK_HUMANITIES:
                return "Humanities & Philosophy";
            case KIOSK_FEATURED:
            default:
                return "Featured University Courses";
        }
    }

    @Nonnull
    @Override
    public InfoItemsPage<InfoItem> getInitialPage() throws IOException, ExtractionException {
        return new InfoItemsPage<>(items, null, Collections.emptyList());
    }

    @Override
    public InfoItemsPage<InfoItem> getPage(final Page page) throws IOException, ExtractionException {
        return InfoItemsPage.emptyPage();
    }
}
