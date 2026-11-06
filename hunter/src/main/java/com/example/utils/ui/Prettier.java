package com.example.utils.ui;

import static com.example.utils.ui.Ansi.*;

/**
 * Console output helper that prints formatted messages with ANSI colors.
 */
public final class Prettier {
    private Prettier() {
        throw new UnsupportedOperationException("Utility class");
    }
    
    private static final String INFO_ICO = CYAN + BOLD + "[i]" + RESET;
    private static final String WARN_ICO = ORANGE + BOLD + "[WARN]" + RESET;
    private static final String ERROR_ICO = RED + BOLD + "[ERR]" + RESET;
    
    // #region Format Helpers

    private static String format(String message, Object... args) {
        return args.length == 0 ? message : String.format(message, args);
    }

    private static void print(String icon, String message) {
        System.out.printf("%n%s %s%n%n", icon, message);
    }

    // #endregion

    /**
     * Prints an informational message.
     *
     * @param message message to print
     */
    public static void info(String message) {
        print(INFO_ICO, message);
    }

    /**
     * Prints a formatted informational message.
     *
     * @param message message format
     * @param args    values used by the format
     */
    public static void info(String message, Object... args) {
        print(INFO_ICO, format(message, args));
    }

    /**
     * Prints a warning message.
     *
     * @param message message to print
     */
    public static void warn(String message) {
        print(WARN_ICO, message);
    }

    /**
     * Prints a formatted warning message.
     *
     * @param message message format
     * @param args    values used by the format
     */
    public static void warn(String message, Object... args) {
        print(WARN_ICO, format(message, args));
    }

    /**
     * Prints an error message.
     *
     * @param message message to print
     */
    public static void error(String message) {
        print(ERROR_ICO, message);
    }

    /**
     * Prints a formatted error message.
     *
     * @param message message format
     * @param args    values used by the format
     */
    public static void error(String message, Object... args) {
        print(ERROR_ICO, format(message, args));
    }

    /**
     * Prints a highlighted title.
     *
     * @param title title text
     */
    public static void printTitle(String title) {
        String baseFormat = BOLD + MAGENTA;
        System.out.printf("%s=== %s ===%s%n", baseFormat, title, RESET);
    }

    /**
     * Prints a highlighted formatted title.
     *
     * @param title title format
     * @param args  values used by the format
     */
    public static void printTitle(String title, Object... args) {
        printTitle(format(title, args));
    }

    /**
     * Appends a highlighted title to an existing string builder.
     *
     * @param sb    destination builder
     * @param title title text
     */
    public static void appendTitle(StringBuilder sb, String title) {
        sb.append(BOLD).append(MAGENTA);
        sb.append("=== ").append(title).append(" ===");
        sb.append(RESET).append("\n");
    }

}
