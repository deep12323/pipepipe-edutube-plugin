package org.pipepipe.plugin.edutube.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schabi.newpipe.extractor.plugin.PluginPreferences;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.stream.StreamType;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EducationalClassifierTest {

    private EducationalClassifier classifier;

    private static class MockPreferences implements PluginPreferences {
        @Override
        public String getString(String key) {
            if ("min_duration_seconds".equals(key)) return "180";
            return "";
        }

        @Override
        public boolean getBoolean(String key) {
            return true; // strict mode on
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
        MockPreferences prefs = new MockPreferences();
        AntiBrainrotFilter filter = new AntiBrainrotFilter(prefs);
        classifier = new EducationalClassifier(filter, prefs);
    }

    @Test
    void testVerifiedUniversitiesPass() {
        StreamInfoItem item = new StreamInfoItem(0, "https://youtube.com/watch?v=10", "Introduction to Algorithms", StreamType.VIDEO_STREAM);
        item.setDuration(3200);
        item.setUploaderName("MIT OpenCourseWare");
        assertTrue(classifier.isAllowed(item));

        StreamInfoItem stanford = new StreamInfoItem(0, "https://youtube.com/watch?v=11", "Machine Learning CS229", StreamType.VIDEO_STREAM);
        stanford.setDuration(4000);
        stanford.setUploaderName("Stanford Online");
        assertTrue(classifier.isAllowed(stanford));
    }

    @Test
    void testAcademicKeywordsPassInStrictMode() {
        StreamInfoItem item = new StreamInfoItem(0, "https://youtube.com/watch?v=12", "Calculus 1 Full College Course - Derivatives and Integrals", StreamType.VIDEO_STREAM);
        item.setDuration(7200);
        item.setUploaderName("Independent Math Educator");
        assertTrue(classifier.isAllowed(item));
    }

    @Test
    void testEntertainmentFailsInStrictMode() {
        // Not brain-rot, but general entertainment (e.g. gameplay or vlog)
        StreamInfoItem item = new StreamInfoItem(0, "https://youtube.com/watch?v=13", "My Day in Tokyo Shopping and Coffee", StreamType.VIDEO_STREAM);
        item.setDuration(1200);
        item.setUploaderName("RandomTraveler");
        assertFalse(classifier.isAllowed(item));
    }
}
