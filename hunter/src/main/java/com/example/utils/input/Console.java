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

    static Scanner scanner() {
        return SCANNER;
    }

    public static void clean() {
        CLEANER.clear();
    }
}
