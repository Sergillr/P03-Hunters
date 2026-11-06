package com.example.services;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.example.models.UnlockResult;
import com.example.utils.io.PdfUnlocker;
import com.example.utils.io.ProjectPaths;

/**
 * Searches for four-digit PINs that open PDF files.
 */
public final class PinBreaker {
    private PinBreaker() {
    }

    /**
     * Tests PINs from {@code 0000} through {@code 9999} in ascending order.
     *
     * @param path the PDF path relative to the Maven project directory
     * @return the accepted PIN, the number of attempts, and the elapsed time in milliseconds
     * @throws IllegalArgumentException if the path is invalid or does not identify a regular file
     * @throws IllegalStateException if the project root cannot be located or no PIN is accepted
     * @throws UncheckedIOException if the PDF cannot be read, parsed, or closed
     */
    public static UnlockResult breakPin(String path) {
        Path resolvedPath = validatePath(path);

        try {
            return breakPassword(resolvedPath);
        } catch (IOException exception) {
            throw new UncheckedIOException("Failed to process PDF: " + resolvedPath, exception);
        }
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

    private static UnlockResult breakPassword(Path path) throws IOException {
        long startTime = System.nanoTime();

        for (int candidate = 0; candidate < 10_000; candidate++) {
            String pin = String.format(Locale.ROOT, "%04d", candidate);
            if (PdfUnlocker.unlockPdf(path, pin)) {
                long elapsedMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
                return new UnlockResult(pin, candidate + 1, elapsedMillis);
            }
        }

        throw new IllegalStateException("No valid four-digit PIN found");
    }
}