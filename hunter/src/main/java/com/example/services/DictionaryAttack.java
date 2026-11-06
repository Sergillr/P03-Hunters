package com.example.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import com.example.models.UnlockResult;
import com.example.utils.io.PdfUnlocker;
import com.example.utils.io.ProjectPaths;

/** Searches for PDF passwords using one dictionary entry per line. */
public final class DictionaryAttack {
    private DictionaryAttack() {
    }

    /**
     * Tests UTF-8 dictionary entries in file order until the PDF opens.
     * Each line is used as a candidate exactly as read, excluding its line
     * terminator. Blank lines are tested as empty passwords.
     *
     * @param path the PDF path relative to the Maven project directory
     * @param dictionary the UTF-8 dictionary path, resolved against the working directory if relative
     * @return the accepted password, the number of candidates tested, and the elapsed search time in milliseconds
     * @throws IllegalArgumentException if the PDF path is invalid or is not a regular file
     * @throws IllegalStateException if the project root cannot be located or no entry is accepted
     * @throws UncheckedIOException if an I/O error occurs while processing the dictionary or PDF
     */
    public static UnlockResult breakDictionary(String path, Path dictionary) {
        Path resolvedPath = validatePath(path);

        try {
            return breakPassword(resolvedPath, dictionary);
        } catch (IOException exception) {
            throw new UncheckedIOException("Failed to search PDF " + resolvedPath + " using dictionary " + dictionary, exception);
        }
    }

    private static UnlockResult breakPassword(Path path, Path dictionary) throws IOException {
        long startTime = System.nanoTime();
        int attempts = 0;

        try (BufferedReader reader = Files.newBufferedReader(dictionary, StandardCharsets.UTF_8)) {
            String candidate;
            while ((candidate = reader.readLine()) != null) {
                attempts++;

                if (PdfUnlocker.unlockPdf(path, candidate)) {
                    long elapsedMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
                    return new UnlockResult(candidate, attempts, elapsedMillis);
                }
            }
        }

        throw new IllegalStateException("No dictionary entry opened the PDF: " + path);

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
