package org.pipepipe.plugin.edutube.filter;

import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.plugin.PluginPreferences;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Intelligent Academic & Educational Content Classifier.
 * Classifies content into genuine educational material (lectures, full courses,
 * STEM tutorials, deep academic analyses) and filters out entertainment and brain-rot.
 */
public final class EducationalClassifier {

    // Verified university, academic, and non-profit educational institutions
    private static final List<String> VERIFIED_INSTITUTIONS = List.of(
            "mit", "mitocw", "mit opencourseware", "stanford", "stanford online",
            "harvard", "cs50", "yale", "yalecourses", "oxford", "cambridge",
            "uc berkeley", "berkeley", "caltech", "carnegie mellon", "cmu",
            "nptel", "iit", "khan academy", "edx", "coursera", "crashcourse",
            "royal institution", "cern", "nature video", "ieee", "acm"
    );

    // Renowned educators, science communicators, and programming authorities
    private static final List<String> VERIFIED_EDUCATORS = List.of(
            "3blue1brown", "veritasium", "numberphile", "computerphile",
            "statquest", "freecodecamp", "freecodecamp.org", "real engineering",
            "practical engineering", "smartereveryday", "professor leonard",
            "michel van biezen", "neetcode", "the cherno", "corey schafer",
            "fireship", "hussein nasser", "bytebytego", "the coding train",
            "traversy media", "bro code", "web dev simplified", "kurzgesagt",
            "pbs space time", "pbs eons", "minutephysics", "physics girl",
            "steve mould", "nilered", "two minute papers", "domain of science",
            "sabine hossenfelder", "lex fridman", "big think", "academy of ideas",
            "philosophy tube", "overly sarcastic productions", "fall of civilizations",
            "ben eater", "derek banas", "sentdex", "sentdex", "stand-up maths"
    );

    // Academic curricula, course, and educational terminology
    private static final List<String> ACADEMIC_KEYWORDS = List.of(
            "lecture", "course", "full course", "tutorial", "bootcamp",
            "derivation", "mathematical proof", "proof of", "curriculum",
            "syllabus", "theory of", "principles of", "introduction to",
            "fundamentals of", "calculus", "linear algebra", "differential equation",
            "quantum mechanics", "thermodynamics", "organic chemistry",
            "biochemistry", "discrete mathematics", "algorithms", "data structures",
            "compiler design", "operating systems", "computer architecture",
            "distributed systems", "system design", "electromagnetism",
            "astrophysics", "neuroscience", "genetics", "microbiology",
            "macroeconomics", "microeconomics", "epistemology", "metaphysics",
            "deep learning", "machine learning", "neural network", "linear regression",
            "masterclass", "crash course", "study guide", "exam review",
            "solved problems", "step by step guide", "comprehensive guide"
    );

    private final AntiBrainrotFilter brainrotFilter;
    private final PluginPreferences preferences;
    private final Pattern academicKeywordsPattern;

    public EducationalClassifier(final AntiBrainrotFilter brainrotFilter,
                                 final PluginPreferences preferences) {
        this.brainrotFilter = brainrotFilter;
        this.preferences = preferences;
        this.academicKeywordsPattern = buildAcademicPattern();
    }

    private static Pattern buildAcademicPattern() {
        final StringBuilder regex = new StringBuilder("(?i)\\b(");
        for (int i = 0; i < ACADEMIC_KEYWORDS.size(); i++) {
            if (i > 0) {
                regex.append("|");
            }
            regex.append(Pattern.quote(ACADEMIC_KEYWORDS.get(i)));
        }
        regex.append(")\\b");
        return Pattern.compile(regex.toString());
    }

    /**
     * Evaluates whether an InfoItem represents acceptable educational material.
     */
    public boolean isAllowed(final InfoItem item) {
        if (item == null) {
            return false;
        }

        // 1. Hard check: discard any brain-rot, clickbait, sensationalism or shorts
        if (brainrotFilter.isBrainrotOrEntertainment(item)) {
            return false;
        }

        final String title = item.getName() != null ? item.getName() : "";
        String uploader = "";
        if (item instanceof StreamInfoItem) {
            final StreamInfoItem stream = (StreamInfoItem) item;
            if (stream.getUploaderName() != null) {
                uploader = stream.getUploaderName().toLowerCase(Locale.ROOT);
            }
        }

        // 2. Check for verified educational authority (Instant PASS)
        if (isVerifiedAuthority(uploader) || isVerifiedAuthority(title)) {
            return true;
        }

        // 3. Check for academic keywords and structured course indicators
        final boolean hasAcademicSignal = academicKeywordsPattern.matcher(title).find();

        final boolean strictMode = preferences == null
                || preferences.getBoolean("strict_academic_mode");

        if (strictMode) {
            // In strict mode, we insist on verified authority OR strong academic curriculum signals
            return hasAcademicSignal;
        }

        // In normal mode, anything that wasn't brain-rot or entertainment is permitted
        return true;
    }

    /**
     * Checks if the given string represents a verified educational institution or creator.
     */
    public static boolean isVerifiedAuthority(final String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        final String lower = text.toLowerCase(Locale.ROOT);
        for (final String inst : VERIFIED_INSTITUTIONS) {
            if (lower.contains(inst)) {
                return true;
            }
        }
        for (final String edu : VERIFIED_EDUCATORS) {
            if (lower.contains(edu)) {
                return true;
            }
        }
        return false;
    }
}
