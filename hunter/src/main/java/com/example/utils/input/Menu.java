package com.example.utils.input;

import java.util.List;
import java.util.Set;

import com.example.utils.ui.Cleaner;
import com.example.utils.ui.Prettier;

/**
 * Console menu helper for numbered option lists.
 */
public final class Menu {
    private Menu() {
        throw new UnsupportedOperationException("Utility class");
    }

    private static final java.util.Scanner scanner = Console.scanner();
    private static final Cleaner cls = new Cleaner();

    /**
     * Displays a numbered menu and keeps asking until the user chooses a valid
     * option.
     *
     * @param options options to display
     * @param title   menu title
     * @return selected option number, starting at {@code 1}
     */
    public static int getOption(List<String> options, String title) {
        return getOption(options, title, Set.of());
    }

    /**
     * Displays a numbered menu with descriptive text below the title and keeps
     * asking until the user chooses a valid option.
     *
     * @param options options to display
     * @param title   menu title
     * @param details descriptive lines to print before the options
     * @return selected option number, starting at {@code 1}
     */
    public static int getOption(List<String> options, String title, List<String> details) {
        return getOption(options, title, details, Set.of());
    }

    /**
     * Displays a numbered menu with optional visual gaps after specific options and
     * keeps asking until the user chooses a valid option.
     *
     * @param options   options to display
     * @param title     menu title
     * @param gapsAfter option numbers after which a blank line is printed
     * @return selected option number, starting at {@code 1}
     */
    public static int getOption(List<String> options, String title, Set<Integer> gapsAfter) {
        return getOption(options, title, List.of(), gapsAfter);
    }

    /**
     * Displays a numbered menu with descriptive text and optional visual gaps after
     * specific options, then keeps asking until the user chooses a valid option.
     *
     * @param options   options to display
     * @param title     menu title
     * @param details   descriptive lines to print before the options
     * @param gapsAfter option numbers after which a blank line is printed
     * @return selected option number, starting at {@code 1}
     */
    public static int getOption(List<String> options, String title, List<String> details, Set<Integer> gapsAfter) {
        boolean loop = true;
        int option = -1;

        while (loop) {
            cls.clear();
            Prettier.printTitle(title);
            printDetails(details);
            printArr(options, gapsAfter);
            System.out.println();

            System.out.print("Seleccioni una opció, si us plau: ");
            option = getInteger();

            if (option == -1) {
                pause();
                continue;
            }

            if (option >= 1 && option <= options.size()) {
                loop = false;
            } else {
                Prettier.warn("L'opció introduïda ha de estar entre 1 i %d. Si us plau, torni a intentar-ho.",
                        options.size());
                pause();
            }
        }

        return option;
    }

    private static void printDetails(List<String> details) {
        if (details.isEmpty()) {
            return;
        }

        for (String detail : details) {
            System.out.println(detail);
        }
        System.out.println();
    }

    private static void printArr(List<String> arr, Set<Integer> gapsAfter) {
        for (int i = 0; i < arr.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, arr.get(i));

            if (gapsAfter.contains(i + 1)) {
                System.out.println();
            }
        }
    }

    private static int getInteger() {
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            Prettier.warn("L'opció introduïda no pot estar en blanc. Si us plau, torni a intentar-ho.");
            return -1;
        }

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            Prettier.warn("L'opció introduïda ha de ser un nombre enter positiu. Si us plau, torni a intentar-ho.");
        } catch (Exception e) {
            Prettier.warn("Ha hagut un error. Si us plau, torni a intentar-ho.");
        }

        return -1;
    }

    /**
     * Pauses execution until the user presses Enter.
     */
    public static void pause() {
        System.out.print("Prem enter per continuar... ");
        scanner.nextLine();
    }
}
