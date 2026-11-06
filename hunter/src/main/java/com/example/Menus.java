package com.example;

import java.util.List;
import java.util.Set;

import com.example.utils.input.Menu;

/**
 * Menús de l'aplicació.
 */
public final class Menus {

    private static final String MAIN_TITLE = "HUNTERS - MENÚ PRINCIPAL";

    private static final List<String> MAIN_OPTIONS = List.of(
            "MISSIÓ 1 - PIN BREAKER",
            "MISSIÓ 2 - ALPHANUMERIC HUNTER",
            "MISSIÓ 3 - DICTIONARY ATTACK",
            "MISSIÓ 4 - FINAL BOSS",
            "ESTADÍSTIQUES",
            "SORTIR");

    private static final List<String> MAIN_DETAILS = List.of(
            "Seleccioni la missió que vol executar.");

    private static final Set<Integer> MAIN_GAPS_AFTER = Set.of(5);

    /** Opció del menú principal que tanca l'aplicació. */
    public static final int MAIN_EXIT_OPTION = 6;

    private Menus() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Mostra el menú principal i retorna l'opció seleccionada.
     *
     * @return número de l'opció seleccionada, començant per {@code 1}
     */
    public static int mainMenu() {
        return Menu.getOption(MAIN_OPTIONS, MAIN_TITLE, MAIN_DETAILS, MAIN_GAPS_AFTER);
    }

    /**
     * Mostra un menú amb detalls i espais opcionals.
     *
     * @param options   opcions a mostrar
     * @param title     títol del menú
     * @param details   línies descriptives
     * @param gapsAfter opcions després de les quals es deixa una línia en blanc
     * @return número de l'opció seleccionada, començant per {@code 1}
     */
    public static int getOption(List<String> options, String title, List<String> details, Set<Integer> gapsAfter) {
        return Menu.getOption(options, title, details, gapsAfter);
    }

    /**
     * Mostra un menú amb detalls.
     *
     * @param options opcions a mostrar
     * @param title   títol del menú
     * @param details línies descriptives
     * @return número de l'opció seleccionada, començant per {@code 1}
     */
    public static int getOption(List<String> options, String title, List<String> details) {
        return Menu.getOption(options, title, details);
    }

    /**
     * Mostra un menú simple.
     *
     * @param options opcions a mostrar
     * @param title   títol del menú
     * @return número de l'opció seleccionada, començant per {@code 1}
     */
    public static int getOption(List<String> options, String title) {
        return Menu.getOption(options, title);
    }
}
