package com.simpligility.maven;

import java.util.regex.Pattern;

public class GavPattern {
    private final Pattern pattern;

    public GavPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public boolean matches(Gav gav) {
        if (gav == null) {
            return false;
        }
        String gavString =
                gav.getGroupId() + ":" + gav.getArtifactId() + ":" + gav.getVersion() + ":" + gav.getPackaging();
        return pattern.matcher(gavString).matches();
    }
}
