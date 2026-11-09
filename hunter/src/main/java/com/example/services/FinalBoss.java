package com.example.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.example.models.UnlockResult;
import com.example.utils.io.PdfUnlocker;
import com.example.utils.io.ProjectPaths;

/**
 * Searches for PDF passwords made from a dictionary word with an initial
 * uppercase letter followed by two digits.
 */
public final class FinalBoss {
    private static final int SUFFIX_LIMIT = 100;

    private FinalBoss() {
    }

    /**
     * Tests dictionary entries in file order. Each entry is capitalised and
     * combined with every numeric suffix from {@code 00} through {@code 99}.
     *
     * @param path       the PDF path relative to the Maven project directory
     * @param dictionary the UTF-8 dictionary path
     * @return the accepted password, the number of candidates tested, and the
     *         elapsed search time in milliseconds
     * @throws IllegalArgumentException if the PDF path is invalid or is not a
     *                                  regular file
     * @throws IllegalStateException    if the project root cannot be located or
     *                                  no candidate is accepted
     * @throws UncheckedIOException     if an I/O error occurs while processing
     *                                  the dictionary or PDF
     */
    public static UnlockResult breakFinalBoss(String path, Path dictionary) {
        Path resolvedPath = validatePath(path);

        try {
            return breakPassword(resolvedPath, dictionary);
        } catch (IOException exception) {
            throw new UncheckedIOException(
                    "Failed to search PDF " + resolvedPath + " using dictionary " + dictionary, exception);
        }
    }

    private static UnlockResult breakPassword(Path path, Path dictionary) throws IOException {
        long startTime = System.nanoTime();
        int attempts = 0;

        try (BufferedReader reader = Files.newBufferedReader(dictionary, StandardCharsets.UTF_8)) {
            String word;
            while ((word = reader.readLine()) != null) {
                if (word.isBlank()) {
                    continue;
                }

                String capitalized = capitalize(word);
                for (int suffix = 0; suffix < SUFFIX_LIMIT; suffix++) {
                    String candidate = String.format(Locale.ROOT, "%s%02d", capitalized, suffix);
                    attempts++;

                    if (PdfUnlocker.unlockPdf(path, candidate)) {
                        long elapsedMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
                        return new UnlockResult(candidate, attempts, elapsedMillis);
                    }
                }
            }
        }

        throw new IllegalStateException("No final-boss password opened the PDF: " + path);
    }

    private static String capitalize(String word) {
        return word.substring(0, 1).toUpperCase(Locale.ROOT) + word.substring(1);
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
}
