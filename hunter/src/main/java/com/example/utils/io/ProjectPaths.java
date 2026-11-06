package com.example.utils.io;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Resolves paths against the nearest Maven project containing this class.
 */
public final class ProjectPaths {
    private ProjectPaths() {
    }

    /**
     * Resolves a relative path against the directory containing the nearest {@code pom.xml}.
     * The search starts at the compiled classes directory or the directory containing the JAR
     * from which this class was loaded, then walks up through its parents.
     * The resolved file or directory does not need to exist.
     *
     * @param relativePath the path relative to the directory containing {@code pom.xml}
     * @return the absolute, normalized path
     * @throws NullPointerException if {@code relativePath} is null
     * @throws java.nio.file.InvalidPathException if {@code relativePath} is invalid
     * @throws IllegalArgumentException if the path has a root component
     * @throws IllegalStateException if the class location is unavailable or no POM is found
     */
    public static Path resolve(String relativePath) {
        Path path = Path.of(relativePath);
        if (path.getRoot() != null) {
            throw new IllegalArgumentException("Expected a relative path: " + relativePath);
        }

        var codeSource = ProjectPaths.class.getProtectionDomain().getCodeSource();
        if (codeSource == null || codeSource.getLocation() == null) {
            throw new IllegalStateException("The location of ProjectPaths is unavailable");
        }

        Path origin;
        try {
            origin = Path.of(codeSource.getLocation().toURI());
        } catch (URISyntaxException exception) {
            throw new IllegalStateException("Could not resolve the location of ProjectPaths", exception);
        }

        Path directory = Files.isDirectory(origin) ? origin : origin.getParent();
        while (directory != null) {
            if (Files.isRegularFile(directory.resolve("pom.xml"))) {
                return directory.resolve(path).normalize();
            }
            directory = directory.getParent();
        }

        throw new IllegalStateException("Could not find pom.xml in the directory hierarchy of " + origin);
    }
}
