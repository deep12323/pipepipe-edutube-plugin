package org.pipepipe.plugin.edutube.filter;

import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.plugin.PluginPreferences;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.stream.StreamType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * High-performance, multi-layered Anti-Brainrot Filter.
 * Strips out:
 * 1. Mindless brain-rot memes, slang, and short-form internet garbage.
 * 2. Sensational clickbait, pranks, and reality trash.
 * 3. YouTube Shorts and micro-content (< 3 minutes by default).
 * 4. Tabloid gossip, celebrity drama, and gaming streamer spam.
 * 5. Excessive caps and emoji-abuse patterns.
 */
public final class AntiBrainrotFilter {

    // Word boundary patterns for Gen-Z / Gen-Alpha brainrot slang & low-effort meme loops
    private static final List<String> BRAINROT_TERMS = List.of(
            "skibidi", "gyatt", "rizz", "fanum tax", "sigma male", "mewing",
            "grimace shake", "kai cenat", "ishowspeed", "baby gronk", "livvy dunne",
            "smurf cat", "brainrot", "brain rot", "goon cave", "gooning",
            "looksmax", "looksmaxxing", "bussin", "glazing", "npc stream",
            "sussy", "amogus", "level 10 gyatt", "only in ohio", "chud"
    );

    // Sensationalist, prank, reaction, and click-trap entertainment phrases
    private static final List<String> CLICKBAIT_ENTERTAINMENT_TERMS = List.of(
            "prank", "gone wrong", "in the hood", "cops called", "arrested",
            "you wont believe", "you won't believe", "i spent 24 hours",
            "i bought everything", "last to leave", "100 days in", "mrbeast",
            "mr beast", "giving away", "reacting to", "reaction mashup",
            "tiktok drama", "drama alert", "exposed", "apology video",
            "cheating drama", "tea spill", "tiktok compilation", "try not to laugh",
            "asmr eating", "mukbang", "satisfying slime", "soap cutting",
            "toy unboxing", "surprise egg", "cocomelon", "diana and roma",
            "vlad and niki", "elsagate", "fails compilation", "instant karma",
            "blind date", "speed dating", "gold digger", "tinder in real life"
    );

    // Tabloid gossip & mindless streaming drama
    private static final List<String> GOSSIP_AND_TRASH_TERMS = List.of(
            "celebrity gossip", "paparazzi", "tmz", "bollywood gossip",
            "hollywood drama", "cringe compilation", "road rage fight",
            "roblox trolling", "among us mod", "gta 5 funny", "fortnite funny moments"
    );

    private static final Pattern MULTIPLE_EXCLAMATION_PATTERN = Pattern.compile("[!?]{3,}");
    private static final Pattern CLICKBAIT_EMOJI_PATTERN = Pattern.compile("([😱🚨🤯💀🔥💥]){3,}");

    private final Pattern combinedBrainrotPattern;
    private final PluginPreferences preferences;

    public AntiBrainrotFilter(final PluginPreferences preferences) {
        this.preferences = preferences;
        this.combinedBrainrotPattern = buildCombinedPattern(preferences);
    }

    private static Pattern buildCombinedPattern(final PluginPreferences preferences) {
        final List<String> allTerms = new ArrayList<>();
        allTerms.addAll(BRAINROT_TERMS);
        allTerms.addAll(CLICKBAIT_ENTERTAINMENT_TERMS);
        allTerms.addAll(GOSSIP_AND_TRASH_TERMS);

        if (preferences != null) {
            final String custom = preferences.getString("custom_blocked_keywords");
            if (custom != null && !custom.trim().isEmpty()) {
                final String[] split = custom.split(",");
                for (final String s : split) {
                    final String trimmed = s.trim().toLowerCase(Locale.ROOT);
                    if (!trimmed.isEmpty()) {
                        allTerms.add(Pattern.quote(trimmed));
                    }
                }
            }
        }

        final StringBuilder regex = new StringBuilder("(?i)\\b(");
        for (int i = 0; i < allTerms.size(); i++) {
            if (i > 0) {
                regex.append("|");
            }
            regex.append(Pattern.quote(allTerms.get(i)));
        }
        regex.append(")\\b");
        return Pattern.compile(regex.toString());
    }

    /**
     * Inspects an info item. Returns true if the item should be BLOCKED / DISCARDED.
     */
    public boolean isBrainrotOrEntertainment(final InfoItem item) {
        if (item == null) {
            return true;
        }

        // 1. YouTube Shorts check
        final String url = item.getUrl() != null ? item.getUrl().toLowerCase(Locale.ROOT) : "";
        if (url.contains("/shorts/")) {
            return true;
        }

        if (item instanceof StreamInfoItem) {
            final StreamInfoItem stream = (StreamInfoItem) item;
            if (stream.isShortFormContent()) {
                return true;
            }

            // Duration check: true academic courses / lectures are rarely under 3 minutes
            final boolean blockShorts = preferences == null || preferences.getBoolean("block_shorts");
            if (blockShorts) {
                final long duration = stream.getDuration();
                final int minDuration = getMinDurationSeconds();
                if (duration > 0 && duration < minDuration) {
                    return true;
                }
            }
        }

        // 2. Title & Uploader text analysis
        final String name = item.getName() != null ? item.getName() : "";
        if (name.isEmpty()) {
            return false;
        }

        // Check against compiled regex
        if (combinedBrainrotPattern.matcher(name).find()) {
            return true;
        }

        // Check uploader name if available
        if (item instanceof StreamInfoItem) {
            final StreamInfoItem stream = (StreamInfoItem) item;
            final String uploader = stream.getUploaderName();
            if (uploader != null && combinedBrainrotPattern.matcher(uploader).find()) {
                return true;
            }
        }

        // 3. Sensationalist styling heuristic
        final boolean blockSensationalism = preferences == null
                || preferences.getBoolean("block_sensationalism");
        if (blockSensationalism && isSensationalClickbait(name)) {
            return true;
        }

        return false;
    }

    /**
     * Checks for high-probability clickbait patterns: excessive caps or repeated clickbait symbols.
     */
    public static boolean isSensationalClickbait(final String title) {
        if (title.length() > 20) {
            int uppercaseCount = 0;
            int letterCount = 0;
            for (int i = 0; i < title.length(); i++) {
                final char c = title.charAt(i);
                if (Character.isLetter(c)) {
                    letterCount++;
                    if (Character.isUpperCase(c)) {
                        uppercaseCount++;
                    }
                }
            }
            if (letterCount > 15 && ((double) uppercaseCount / letterCount) > 0.65) {
                return true;
            }
        }

        if (MULTIPLE_EXCLAMATION_PATTERN.matcher(title).find()) {
            return true;
        }

        if (CLICKBAIT_EMOJI_PATTERN.matcher(title).find()) {
            return true;
        }

        return false;
    }

    private int getMinDurationSeconds() {
        if (preferences == null) {
            return 180;
        }
        final String val = preferences.getString("min_duration_seconds");
        if (val == null || val.trim().isEmpty()) {
            return 180;
        }
        try {
            return Math.max(30, Integer.parseInt(val.trim()));
        } catch (final NumberFormatException e) {
            return 180;
        }
    }
}
