package com.simpligility.maven.provisioner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import static org.junit.Assert.*;

import com.simpligility.maven.Gav;
import com.simpligility.maven.GavPattern;
import org.junit.Before;
import org.junit.Test;

public class MavenRepositoryDeployerTest {

    private MavenRepositoryDeployer deployer;

    @Before
    public void setUp() {
        deployer = new MavenRepositoryDeployer();
    }

    @Test
    public void testLoadGavsFromFilterFile_ValidFile() throws IOException {
        File tempFile = createTempFile("org.apache.maven.resolver:*:*:*\n");
        Set<GavPattern> gavPatterns = deployer.loadGavPatternsFromFilterFile(tempFile.getAbsolutePath());
        assertEquals(1, gavPatterns.size());
        assertTrue(gavPatterns
                .iterator()
                .next()
                .matches(new Gav("org.apache.maven.resolver", "artifactId", "version", "jar")));
        tempFile.delete();
    }

    @Test
    public void testLoadGavsFromFilterFile_EmptyFile() throws IOException {
        File tempFile = createTempFile("");
        Set<GavPattern> gavPatterns = deployer.loadGavPatternsFromFilterFile(tempFile.getAbsolutePath());
        assertTrue(gavPatterns.isEmpty());
        tempFile.delete();
    }

    @Test
    public void testLoadGavsFromFilterFile_InvalidFormat() throws IOException {
        File tempFile = createTempFile("invalid:format\n");
        Set<GavPattern> gavPatterns = deployer.loadGavPatternsFromFilterFile(tempFile.getAbsolutePath());
        assertTrue(gavPatterns.isEmpty());
        tempFile.delete();
    }

    @Test
    public void testLoadGavsFromFilterFile_MixedValidAndInvalid() throws IOException {
        File tempFile = createTempFile("org.apache.maven:*:*:*\ninvalid:format\n");
        Set<GavPattern> gavPatterns = deployer.loadGavPatternsFromFilterFile(tempFile.getAbsolutePath());
        assertEquals(1, gavPatterns.size());
        assertTrue(gavPatterns.iterator().next().matches(new Gav("org.apache.maven", "artifactId", "version", "jar")));
        tempFile.delete();
    }

    @Test
    public void testLoadGavsFromFilterFile_NonExistentFile() {
        Set<GavPattern> gavPatterns = deployer.loadGavPatternsFromFilterFile("nonexistentfile.txt");
        assertTrue(gavPatterns.isEmpty());
    }

    private File createTempFile(String content) throws IOException {
        File tempFile = File.createTempFile("gavFilter", ".txt");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(content);
        }
        return tempFile;
    }
}
