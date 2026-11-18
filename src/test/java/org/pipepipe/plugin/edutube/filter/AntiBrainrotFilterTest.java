package org.pipepipe.plugin.edutube.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schabi.newpipe.extractor.plugin.PluginPreferences;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.stream.StreamType;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AntiBrainrotFilterTest {

    private AntiBrainrotFilter filter;

    private static class MockPreferences implements PluginPreferences {
        @Override
        public String getString(String key) {
            if ("min_duration_seconds".equals(key)) return "180";
            return "";
        }

        @Override
        public boolean getBoolean(String key) {
            return true;
        }

        @Override
        public void putString(String key, String value) {}

        @Override
        public void putBoolean(String key, boolean value) {}

        @Override
        public void addChangeListener(ChangeListener listener) {}
    }

    @BeforeEach
    void setUp() {
        filter = new AntiBrainrotFilter(new MockPreferences());
    }

    @Test
    void testBlocksBrainrotSlang() {
        StreamInfoItem item1 = new StreamInfoItem(0, "https://youtube.com/watch?v=1", "Skibidi Toilet Episode 70 Full Fight", StreamType.VIDEO_STREAM);
        item1.setDuration(300);
        assertTrue(filter.isBrainrotOrEntertainment(item1));

        StreamInfoItem item2 = new StreamInfoItem(0, "https://youtube.com/watch?v=2", "Level 10 Gyatt in Ohio Fanum Tax Rizzler", StreamType.VIDEO_STREAM);
        item2.setDuration(450);
        assertTrue(filter.isBrainrotOrEntertainment(item2));
    }

    @Test
    void testBlocksPranksAndClickbait() {
        StreamInfoItem prank = new StreamInfoItem(0, "https://youtube.com/watch?v=3", "GOLD DIGGER PRANK GONE WRONG (COPS CALLED)", StreamType.VIDEO_STREAM);
        prank.setDuration(600);
        assertTrue(filter.isBrainrotOrEntertainment(prank));

        StreamInfoItem beast = new StreamInfoItem(0, "https://youtube.com/watch?v=4", "MrBeast - I Spent 24 Hours Inside a Pyramid", StreamType.VIDEO_STREAM);
        beast.setDuration(1200);
        assertTrue(filter.isBrainrotOrEntertainment(beast));
    }

    @Test
    void testBlocksShortsAndMicroDuration() {
        // Shorts URL
        StreamInfoItem shortsUrl = new StreamInfoItem(0, "https://youtube.com/shorts/abc12345", "Fast Coding Hack", StreamType.VIDEO_STREAM);
        shortsUrl.setDuration(45);
        assertTrue(filter.isBrainrotOrEntertainment(shortsUrl));

        // Short duration (< 180s)
        StreamInfoItem shortVideo = new StreamInfoItem(0, "https://youtube.com/watch?v=5", "Quick Tip", StreamType.VIDEO_STREAM);
        shortVideo.setDuration(90);
        assertTrue(filter.isBrainrotOrEntertainment(shortVideo));
    }

    @Test
    void testAllowsLegitimateLectures() {
        StreamInfoItem mit = new StreamInfoItem(0, "https://youtube.com/watch?v=6", "MIT 18.06 Linear Algebra - Lecture 1 (Prof. Strang)", StreamType.VIDEO_STREAM);
        mit.setDuration(2400);
        mit.setUploaderName("MIT OpenCourseWare");
        assertFalse(filter.isBrainrotOrEntertainment(mit));

        StreamInfoItem cs50 = new StreamInfoItem(0, "https://youtube.com/watch?v=7", "Harvard CS50 - Lecture 1: C and Memory", StreamType.VIDEO_STREAM);
        cs50.setDuration(5400);
        cs50.setUploaderName("CS50");
        assertFalse(filter.isBrainrotOrEntertainment(cs50));
    }
}
