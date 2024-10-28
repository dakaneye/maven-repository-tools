package com.simpligility.maven;

import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GavPatternTest {

    @Test
    public void testMatches_ValidPattern() {
        GavPattern pattern = new GavPattern(Pattern.compile("org\\.apache\\.maven\\.resolver:.*:.*:.*"));
        Gav gav = new Gav("org.apache.maven.resolver", "artifactId", "version", "jar");
        assertTrue(pattern.matches(gav));
    }

    @Test
    public void testMatches_InvalidPattern() {
        GavPattern pattern = new GavPattern(Pattern.compile("com\\.simpligility:.*:.*:.*"));
        Gav gav = new Gav("org.apache.maven.resolver", "artifactId", "version", "jar");
        assertFalse(pattern.matches(gav));
    }

    @Test
    public void testMatches_EmptyPattern() {
        GavPattern pattern = new GavPattern(Pattern.compile(""));
        Gav gav = new Gav("org.apache.maven.resolver", "artifactId", "version", "jar");
        assertFalse(pattern.matches(gav));
    }

    @Test
    public void testMatches_NullGav() {
        GavPattern pattern = new GavPattern(Pattern.compile("org\\.apache\\.maven\\.resolver:.*:.*:.*"));
        assertFalse(pattern.matches(null));
    }
}
