package com.example.utils.input;

import java.io.Console;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;

import com.example.utils.ui.Prettier;

/**
 * Console input helper that reads and validates typed values.
 */
public final class Getters {
    /** Creates input helpers that read from the shared standard input scanner. */
    public Getters() {
    }


    private final Scanner scanner = com.example.utils.input.Console.scanner();
    private boolean acceptCommaAsDecimalSeparator = false;

    /**
     * Indicates whether commas (,) are accepted as decimal separators in numeric
     * decimal input.
     *
     * @return {@code true} when commas are accepted as decimal separators;
     *         {@code false} when only dots (.) are accepted
     */
    public boolean isAcceptCommaAsDecimalSeparator() {
        return acceptCommaAsDecimalSeparator;
    }

    /**
     * Enables or disables accepting commas (,) as decimal separators in numeric
     * decimal input.
     *
     * @param value {@code true} to accept commas as decimal separators;
     *              {@code false} to accept only dots (.)
     */
    public void setAcceptCommaAsDecimalSeparator(boolean value) {
        acceptCommaAsDecimalSeparator = value;
    }

    private void pause() {
        System.out.print("Prem la tecla Enter per continuar... ");
        scanner.nextLine();
    }

    private String readLineNormalizedForDouble(String input) {
        if (acceptCommaAsDecimalSeparator) {
            return input.replace(',', '.');
        }
        return input;
    }

    // #region Single-Getters

    /**
     * Reads non-empty text.
     *
     * @param prompt message shown before reading input
     * @param name   field name used in validation messages
     * @return entered text, trimmed with {@code trim()}
     */
    public String getString(String prompt, String name) {
        return getString(prompt, name, 0, Integer.MAX_VALUE, Set.of());
    }

    /**
     * Reads non-empty text with a length constraint.
     *
     * @param prompt message shown before reading input
     * @param name   field name used in validation messages
     * @param minLen minimum length, inclusive
     * @param maxLen maximum length, inclusive
     * @return entered text, trimmed with {@code trim()}
     */
    public String getString(String prompt, String name, int minLen, int maxLen) {
        return getString(prompt, name, minLen, maxLen, Set.of());
    }

    /**
     * Reads non-empty text with a length constraint and one prohibited value.
     *
     * @param prompt           message shown before reading input
     * @param name             field name used in validation messages
     * @param minLen           minimum length, inclusive
     * @param maxLen           maximum length, inclusive
     * @param singleProhibited value that will not be accepted, or {@code null}
     * @return entered text, trimmed with {@code trim()}
     */
    public String getString(String prompt, String name, int minLen, int maxLen, String singleProhibited) {
        Set<String> p = singleProhibited == null ? Set.of() : Set.of(singleProhibited);
        return getString(prompt, name, minLen, maxLen, p);
    }

    /**
     * Reads non-empty text with a length constraint and a set of prohibited values.
     *
     * @param prompt      message shown before reading input
     * @param name        field name used in validation messages
     * @param minLen      minimum length, inclusive
     * @param maxLen      maximum length, inclusive
     * @param prohibiteds values that are not accepted
     * @return entered text, trimmed with {@code trim()}
     */
    public String getString(String prompt, String name, int minLen, int maxLen, Set<String> prohibiteds) {
        String text = "";
        boolean loop = true;

        do {
            System.out.print(prompt);
            text = scanner.nextLine().trim();

            if (text.isEmpty()) {
                Prettier.warn("%s cannot be blank. Please try again.", name);
            } else if (text.length() < minLen || text.length() > maxLen) {
                Prettier.warn("%s must be between %d and %d characters long. Please try again.",
                        name, minLen, maxLen);
            } else if (prohibiteds.contains(text)) {
                Prettier.warn("%s is unavailable. Please try again.", name);
            } else {
                loop = false;
            }

            if (loop) {
                pause();
                System.out.println();
            }
        } while (loop);

        return text;
    }

    /**
     * Reads an integer.
     *
     * @param prompt message shown before reading input
     * @param name   field name used in validation messages
     * @return entered integer value
     */
    public int getInteger(String prompt, String name) {
        return getInteger(prompt, name, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Reads an integer within a range.
     *
     * @param prompt message shown before reading input
     * @param name   field name used in validation messages
     * @param min    minimum value, inclusive
     * @param max    maximum value, inclusive
     * @return entered integer value
     */
    public int getInteger(String prompt, String name, int min, int max) {
        int value = -1;
        boolean loop = true;

        do {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            boolean skip = true;
            if (input.isEmpty()) {
                Prettier.warn("%s cannot be blank. Please try again.", name);
            } else if (!isInteger(input)) {
                Prettier.warn("%s must be an integer. Please try again.", name);
            } else {
                skip = false;
            }

            if (skip) {
                pause();
                System.out.println();
                continue;
            }

            value = Integer.parseInt(input);

            if (value >= min && value <= max) {
                loop = false;
            } else {
                Prettier.warn("%s must be between %d and %d. Please try again.", name, min, max);
                pause();
                System.out.println();
            }
        } while (loop);

        return value;
    }

    /**
     * Reads a decimal number.
     *
     * @param prompt message shown before reading input
     * @param name   field name used in validation messages
     * @return entered decimal value
     */
    public double getDouble(String prompt, String name) {
        return getDouble(prompt, name, -Double.MAX_VALUE, Double.MAX_VALUE);
    }

    /**
     * Reads a decimal number within a range. If
     * {@link #isAcceptCommaAsDecimalSeparator()} is {@code true}, commas (,) are
     * also accepted as decimal separators.
     *
     * @param prompt message shown before reading input
     * @param name   field name used in validation messages
     * @param min    minimum value, inclusive
     * @param max    maximum value, inclusive
     * @return entered decimal value
     */
    public double getDouble(String prompt, String name, double min, double max) {
        double value = 0;
        boolean loop = true;

        do {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            input = readLineNormalizedForDouble(input);

            boolean skip = true;
            if (input.isEmpty()) {
                Prettier.warn("%s cannot be blank. Please try again.", name);
            } else if (!isDouble(input)) {
                Prettier.warn("%s must be a decimal number. Please try again.", name);
            } else {
                skip = false;
            }

            if (skip) {
                pause();
                System.out.println();
                continue;
            }

            value = Double.parseDouble(input);

            if (value >= min && value <= max) {
                loop = false;
            } else {
                Prettier.warn("%s must be between %f and %f. Please try again.", name, min, max);
                pause();
                System.out.println();
            }
        } while (loop);

        return value;
    }

    /**
     * Reads a long integer.
     *
     * @param prompt message shown before reading input
     * @param name   field name used in validation messages
     * @return entered {@code long} value
     */
    public long getLong(String prompt, String name) {
        return getLong(prompt, name, -Long.MAX_VALUE, Long.MAX_VALUE);
    }

    /**
     * Reads a long integer within a range.
     *
     * @param prompt message shown before reading input
     * @param name   field name used in validation messages
     * @param min    minimum value, inclusive
     * @param max    maximum value, inclusive
     * @return entered {@code long} value
     */
    public long getLong(String prompt, String name, long min, long max) {
        long value = 0;
        boolean loop = true;

        do {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            boolean skip = true;
            if (input.isEmpty()) {
                Prettier.warn("%s cannot be blank. Please try again.", name);
            } else if (!isLong(input)) {
                Prettier.warn("%s must be a 64-bit integer. Please try again.", name);
            } else {
                skip = false;
            }

            if (skip) {
                pause();
                System.out.println();
                continue;
            }

            value = Long.parseLong(input);

            if (value >= min && value <= max) {
                loop = false;
            } else {
                Prettier.warn("%s must be between %d and %d. Please try again.", name, min, max);
                pause();
                System.out.println();
            }
        } while (loop);

        return value;
    }

    /**
     * Reads a boolean value from two text options, such as {@code "s"} and
     * {@code "n"}. If the user presses Enter, the default value is returned.
     *
     * @param prompt       message shown before reading input
     * @param defaultValue value returned when the user presses Enter
     * @param trueText     text that represents {@code true}
     * @param falseText    text that represents {@code false}
     * @return the boolean value read from the user
     */
    public boolean getBoolean(String prompt, boolean defaultValue, String trueText, String falseText) {
        boolean result = defaultValue;
        boolean loop = true;

        do {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            boolean stop = true;
            if (input.isEmpty()) {
                loop = false;
                continue;
            } else if (input.equalsIgnoreCase(trueText)) {
                result = true;
            } else if (input.equalsIgnoreCase(falseText)) {
                result = false;
            } else {
                stop = false;
                Prettier.warn("Invalid value. Enter \"%s\" or \"%s\", or press Enter to use the default.", trueText,
                        falseText);
                pause();
                System.out.println();
            }

            loop = !stop;
        } while (loop);

        return result;
    }

    // #endregion

    // #region Default-Getters

    /**
     * Reads text. If the user leaves the field blank, the default value is
     * returned.
     *
     * @param prompt       message shown before reading input
     * @param name         field name used in validation messages
     * @param defaultValue value returned when the user presses Enter
     * @return entered text, trimmed with {@code trim()}, or the default value
     */
    public String getStringOrDefault(String prompt, String name, String defaultValue) {
        return getStringOrDefault(prompt, name, defaultValue, 0, Integer.MAX_VALUE, Set.of());
    }

    /**
     * Reads text. If the user leaves the field blank, the default value is
     * returned. Also validates the length.
     *
     * @param prompt       message shown before reading input
     * @param name         field name used in validation messages
     * @param defaultValue value returned when the user presses Enter
     * @param minLen       minimum length, inclusive
     * @param maxLen       maximum length, inclusive
     * @return entered text, trimmed with {@code trim()}, or the default value
     */
    public String getStringOrDefault(String prompt, String name, String defaultValue, int minLen, int maxLen) {
        return getStringOrDefault(prompt, name, defaultValue, minLen, maxLen, Set.of());
    }

    /**
     * Reads text. If the user leaves the field blank, the default value is
     * returned. Also validates the length and one prohibited value.
     *
     * @param prompt           message shown before reading input
     * @param name             field name used in validation messages
     * @param defaultValue     value returned when the user presses Enter
     * @param minLen           minimum length, inclusive
     * @param maxLen           maximum length, inclusive
     * @param singleProhibited value that will not be accepted, or {@code null}
     * @return entered text, trimmed with {@code trim()}, or the default value
     */
    public String getStringOrDefault(String prompt, String name, String defaultValue, int minLen, int maxLen,
            String singleProhibited) {
        Set<String> p = singleProhibited == null ? Set.of() : Set.of(singleProhibited);
        return getStringOrDefault(prompt, name, defaultValue, minLen, maxLen, p);
    }

    /**
     * Reads text. If the user leaves the field blank, the default value is
     * returned. Also validates the length and a set of prohibited values.
     *
     * @param prompt       message shown before reading input
     * @param name         field name used in validation messages
     * @param defaultValue value returned when the user presses Enter
     * @param minLen       minimum length, inclusive
     * @param maxLen       maximum length, inclusive
     * @param prohibiteds  values that are not accepted
     * @return entered text, trimmed with {@code trim()}, or the default value
     */
    public String getStringOrDefault(String prompt, String name, String defaultValue, int minLen, int maxLen,
            Set<String> prohibiteds) {
        String text = "";
        boolean loop = true;

        do {
            System.out.print(prompt);
            text = scanner.nextLine().trim();

            if (text.isEmpty()) {
                loop = false;
                text = defaultValue;
            } else if (text.length() < minLen || text.length() > maxLen) {
                Prettier.warn("%s must be between %d and %d characters long. Please try again.",
                        name, minLen, maxLen);
            } else if (prohibiteds.contains(text)) {
                Prettier.warn("%s is unavailable. Please try again.", name);
            } else {
                loop = false;
            }

            if (loop) {
                pause();
                System.out.println();
            }
        } while (loop);

        return text;
    }

    /**
     * Reads text. If the user leaves the field blank, an empty string is returned.
     *
     * @param prompt message shown before reading input
     * @param name   field name used in validation messages
     * @return entered text, trimmed with {@code trim()}, or an empty string
     */
    public String getStringAllowEmpty(String prompt, String name) {
        return getStringAllowEmpty(prompt, name, 0, Integer.MAX_VALUE, Set.of());
    }

    /**
     * Reads a password. If the console supports it, typed characters are hidden.
     * If the user leaves the field blank, an empty string is returned.
     *
     * @param prompt message shown before reading input
     * @param name   field name used in validation messages
     * @return entered password or an empty string
     */
    public String getPasswordAllowEmpty(String prompt, String name) {
        Console console = System.console();

        if (console == null) {
            return getStringAllowEmpty(prompt, name);
        }

        char[] chars = console.readPassword(prompt);

        if (chars == null) {
            return "";
        }

        String password = new String(chars).trim();
        Arrays.fill(chars, '\0');
        return password;
    }

    /**
     * Reads text. If the user leaves the field blank, an empty string is returned.
     * Also validates the length.
     *
     * @param prompt message shown before reading input
     * @param name   field name used in validation messages
     * @param minLen minimum length, inclusive
     * @param maxLen maximum length, inclusive
     * @return entered text, trimmed with {@code trim()}, or an empty string
     */
    public String getStringAllowEmpty(String prompt, String name, int minLen, int maxLen) {
        return getStringAllowEmpty(prompt, name, minLen, maxLen, Set.of());
    }

    /**
     * Reads text. If the user leaves the field blank, an empty string is returned.
     * Also validates the length and one prohibited value.
     *
     * @param prompt           message shown before reading input
     * @param name             field name used in validation messages
     * @param minLen           minimum length, inclusive
     * @param maxLen           maximum length, inclusive
     * @param singleProhibited value that will not be accepted, or {@code null}
     * @return entered text, trimmed with {@code trim()}, or an empty string
     */
    public String getStringAllowEmpty(String prompt, String name, int minLen, int maxLen, String singleProhibited) {
        Set<String> p = singleProhibited == null ? Set.of() : Set.of(singleProhibited);
        return getStringAllowEmpty(prompt, name, minLen, maxLen, p);
    }

    /**
     * Reads text. If the user leaves the field blank, an empty string is returned.
     * Also validates the length and a set of prohibited values.
     *
     * @param prompt      message shown before reading input
     * @param name        field name used in validation messages
     * @param minLen      minimum length, inclusive
     * @param maxLen      maximum length, inclusive
     * @param prohibiteds values that are not accepted
     * @return entered text, trimmed with {@code trim()}, or an empty string
     */
    public String getStringAllowEmpty(String prompt, String name, int minLen, int maxLen, Set<String> prohibiteds) {
        String text = "";
        boolean loop = true;

        do {
            System.out.print(prompt);
            text = scanner.nextLine().trim();

            if (text.isEmpty()) {
                loop = false;
                text = "";
            } else if (text.length() < minLen || text.length() > maxLen) {
                Prettier.warn("%s must be between %d and %d characters long. Please try again.",
                        name, minLen, maxLen);
            } else if (prohibiteds.contains(text)) {
                Prettier.warn("%s is unavailable. Please try again.", name);
            } else {
                loop = false;
            }

            if (loop) {
                pause();
                System.out.println();
            }
        } while (loop);

        return text;
    }

    /**
     * Reads an integer. If the user presses Enter, the default value is returned.
     *
     * @param prompt       message shown before reading input
     * @param name         field name used in validation messages
     * @param defaultValue value returned when the user presses Enter
     * @return entered integer value or the default value
     */
    public int getIntegerOrDefault(String prompt, String name, int defaultValue) {
        return getIntegerOrDefault(prompt, name, defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Reads an integer within a range. If the user presses Enter, the default value
     * is returned.
     *
     * @param prompt       message shown before reading input
     * @param name         field name used in validation messages
     * @param defaultValue value returned when the user presses Enter
     * @param min          minimum value, inclusive
     * @param max          maximum value, inclusive
     * @return entered integer value or the default value
     */
    public int getIntegerOrDefault(String prompt, String name, int defaultValue, int min, int max) {
        int value = defaultValue;
        boolean loop = true;

        do {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            boolean skip = true;
            if (input.isEmpty()) {
                value = defaultValue;
                loop = false;
                continue;
            } else if (!isInteger(input)) {
                Prettier.warn("%s must be an integer. Please try again.", name);
            } else {
                skip = false;
            }

            if (skip) {
                pause();
                System.out.println();
                continue;
            }

            value = Integer.parseInt(input);

            if (value >= min && value <= max) {
                loop = false;
            } else {
                Prettier.warn("%s must be between %d and %d. Please try again.", name, min, max);
                pause();
                System.out.println();
            }
        } while (loop);

        return value;
    }

    /**
     * Reads a decimal number. If the user presses Enter, the default value is
     * returned.
     *
     * @param prompt       message shown before reading input
     * @param name         field name used in validation messages
     * @param defaultValue value returned when the user presses Enter
     * @return entered decimal value or the default value
     */
    public double getDoubleOrDefault(String prompt, String name, double defaultValue) {
        return getDoubleOrDefault(prompt, name, defaultValue, -Double.MAX_VALUE, Double.MAX_VALUE);
    }

    /**
     * Reads a decimal number within a range. If the user presses Enter, the default
     * value is returned. If {@link #isAcceptCommaAsDecimalSeparator()} is
     * {@code true}, commas (,) are also accepted as decimal separators.
     *
     * @param prompt       message shown before reading input
     * @param name         field name used in validation messages
     * @param defaultValue value returned when the user presses Enter
     * @param min          minimum value, inclusive
     * @param max          maximum value, inclusive
     * @return entered decimal value or the default value
     */
    public double getDoubleOrDefault(String prompt, String name, double defaultValue, double min, double max) {
        double value = defaultValue;
        boolean loop = true;

        do {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            input = readLineNormalizedForDouble(input);

            boolean skip = true;
            if (input.isEmpty()) {
                value = defaultValue;
                loop = false;
                continue;
            } else if (!isDouble(input)) {
                Prettier.warn("%s must be a decimal number. Please try again.", name);
            } else {
                skip = false;
            }

            if (skip) {
                pause();
                System.out.println();
                continue;
            }

            value = Double.parseDouble(input);

            if (value >= min && value <= max) {
                loop = false;
            } else {
                Prettier.warn("%s must be between %f and %f. Please try again.", name, min, max);
                pause();
                System.out.println();
            }
        } while (loop);

        return value;
    }

    /**
     * Reads a {@code long}. If the user presses Enter, the default value is
     * returned.
     *
     * @param prompt       message shown before reading input
     * @param name         field name used in validation messages
     * @param defaultValue value returned when the user presses Enter
     * @return entered {@code long} value or the default value
     */
    public long getLongOrDefault(String prompt, String name, long defaultValue) {
        return getLongOrDefault(prompt, name, defaultValue, -Long.MAX_VALUE, Long.MAX_VALUE);
    }

    /**
     * Reads a {@code long} within a range. If the user presses Enter, the default
     * value is returned.
     *
     * @param prompt       message shown before reading input
     * @param name         field name used in validation messages
     * @param defaultValue value returned when the user presses Enter
     * @param min          minimum value, inclusive
     * @param max          maximum value, inclusive
     * @return entered {@code long} value or the default value
     */
    public long getLongOrDefault(String prompt, String name, long defaultValue, long min, long max) {
        long value = defaultValue;
        boolean loop = true;

        do {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            boolean skip = true;
            if (input.isEmpty()) {
                value = defaultValue;
                loop = false;
                continue;
            } else if (!isLong(input)) {
                Prettier.warn("%s must be a 64-bit integer. Please try again.", name);
            } else {
                skip = false;
            }

            if (skip) {
                pause();
                System.out.println();
                continue;
            }

            value = Long.parseLong(input);

            if (value >= min && value <= max) {
                loop = false;
            } else {
                Prettier.warn("%s must be between %d and %d. Please try again.", name, min, max);
                pause();
                System.out.println();
            }
        } while (loop);

        return value;
    }

    /**
     * Reads a boolean value. If the user presses Enter, the default value is
     * returned.
     *
     * @param prompt       message shown before reading input
     * @param defaultValue value returned when the user presses Enter
     * @param trueText     text that represents {@code true}
     * @param falseText    text that represents {@code false}
     * @return the boolean value read from the user or the default value
     */
    public boolean getBooleanOrDefault(String prompt, boolean defaultValue, String trueText, String falseText) {
        boolean result = defaultValue;
        boolean loop = true;

        do {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            boolean stop = true;
            if (input.isEmpty()) {
                return defaultValue;
            } else if (input.equalsIgnoreCase(trueText)) {
                result = true;
            } else if (input.equalsIgnoreCase(falseText)) {
                result = false;
            } else {
                stop = false;
                Prettier.warn("Invalid value. Enter \"%s\" or \"%s\", or press Enter to use the default.", trueText,
                        falseText);
                pause();
                System.out.println();
            }

            loop = !stop;
        } while (loop);

        return result;
    }

    // #endregion

    // #region IsType()

    private boolean isInteger(String input) {
        if (input == null || input.isBlank())
            return false;

        try {
            Integer.parseInt(input.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isDouble(String input) {
        if (input == null || input.isBlank())
            return false;

        try {
            Double.parseDouble(input.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isLong(String input) {
        if (input == null || input.isBlank())
            return false;
        try {
            Long.parseLong(input.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // #endregion
}
