package com.example.utils.input;

import java.util.Scanner;

import com.example.utils.ui.Cleaner;

/**
 * Shared console input stream for every input helper.
 */
public final class Console {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Cleaner CLEANER = new Cleaner();

    private Console() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Returns the scanner shared by the input helpers.
     *
     * @return the scanner backed by standard input
     */
    static Scanner scanner() {
        return SCANNER;
    }

    /** Clears the terminal using the shared console cleaner. */
    public static void clean() {
        CLEANER.clear();
    }
}
