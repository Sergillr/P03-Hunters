package com.example.services;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import com.example.models.UnlockResult;
import com.example.utils.io.PdfUnlocker;
import com.example.utils.io.ProjectPaths;

/**
 * Searches six-character passwords with a fixed prefix pattern and an alphanumeric suffix.
 */
public final class AlphanumericHunter {
    private AlphanumericHunter() {
    }

    /**
     * Tests candidates beginning with {@code c}, followed by one of {@code aeioutr}
     * and four characters from {@code abcdefghijklmnopqrstuvwxyz0123456789}.
     * The second character follows the listed order, and the rightmost suffix
     * character changes fastest.
     *
     * @param path the PDF path relative to the Maven project directory
     * @return the accepted password and the attempt count and duration of the search
     * @throws IllegalArgumentException if the path is invalid or does not identify a regular file
     * @throws IllegalStateException if the project root cannot be located or no candidate is accepted
     * @throws UncheckedIOException if the PDF cannot be read, parsed, or closed
     */
    public static UnlockResult breakAlphanumeric(String path) {
        Path resolvedPath = validatePath(path);

        try {
            return breakPassword(resolvedPath);
        } catch (IOException exception) {
            throw new UncheckedIOException("Failed to process PDF: " + resolvedPath, exception);
        }
    }

    private static UnlockResult breakPassword(Path path) throws IOException {
        long startTime = System.nanoTime();
        String alphabet = "abcdefghijklmnopqrstuvwxyz0123456789";
        int base = alphabet.length();
        int combinations = base * base * base * base;
        int attempts = 0;

        char[] candidate = new char[6];
        candidate[0] = FIRST_CHARACTER;

        for (char second : SECOND_CHARACTER) {
            candidate[1] = second;

            for (int suffix = 0; suffix < combinations; suffix++) {
                int value = suffix;

                for (int position = 5; position >= 2; position--) {
                    candidate[position] = alphabet.charAt(value % base);
                    value /= base;
                }

                String password = new String(candidate);
                attempts++;
                if (PdfUnlocker.unlockPdf(path, password)) {
                    long elapsedMillis = TimeUnit.NANOSECONDS.toMillis(
                            System.nanoTime() - startTime);
                    return new UnlockResult(password, attempts, elapsedMillis);
                }
            }
        }

        throw new IllegalStateException("No valid alphanumeric password was found");
    }

    private static Path validatePath(String path) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Path cannot be null or empty");
        }

        Path resolvedPath = ProjectPaths.resolve(path);
        if (!Files.isRegularFile(resolvedPath)) {
            throw new IllegalArgumentException("Path must identify a regular file: " + resolvedPath);
        }

        return resolvedPath;
    }

    private static final char FIRST_CHARACTER = 'c';
    private static final char[] SECOND_CHARACTER = { 'a', 'e', 'i', 'o', 'u', 't', 'r' };
}
