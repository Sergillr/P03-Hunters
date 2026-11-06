package com.example.utils.ui;

/**
 * ANSI escape codes used to style console output.
 */
public final class Ansi {
    private Ansi() {
        throw new UnsupportedOperationException("Utility class");
    }

    // Escape sequence prefix.
    private static final String ESC = "\u001b[";

    /** Resets all ANSI styles. */
    public static final String RESET = ESC + "0m";

    /** Enables bold text. */
    public static final String BOLD = ESC + "1m";

    /** Enables dim text. */
    public static final String DIM = ESC + "2m";

    /** Sets the foreground color to red. */
    public static final String RED = ESC + "31m";

    /** Sets the foreground color to green. */
    public static final String GREEN = ESC + "32m";

    /** Sets the foreground color to yellow. */
    public static final String YELLOW = ESC + "33m";

    /** Sets the foreground color to blue. */
    public static final String BLUE = ESC + "34m";

    /** Sets the foreground color to magenta. */
    public static final String MAGENTA = ESC + "35m";

    /** Sets the foreground color to cyan. */
    public static final String CYAN = ESC + "36m";

    /** Sets the foreground color to white. */
    public static final String WHITE = ESC + "37m";

    /** Sets the foreground color to bright red. */
    public static final String BRIGHT_RED = "\u001B[91m";

    /** Sets the foreground color to bright blue. */
    public static final String BRIGHT_BLUE = "\u001B[94m";

    /** Sets the foreground color to orange. */
    public static final String ORANGE = ESC + "38;2;255;165;0m";

    /** Sets the foreground color to dark gray. */
    public static final String DARK_GRAY = ESC + "38;2;120;120;120m";

}
