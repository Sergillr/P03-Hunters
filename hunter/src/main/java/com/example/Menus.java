package com.example;

import static com.example.utils.ui.Ansi.BOLD;
import static com.example.utils.ui.Ansi.CYAN;
import static com.example.utils.ui.Ansi.MAGENTA;
import static com.example.utils.ui.Ansi.RED;
import static com.example.utils.ui.Ansi.RESET;
import static com.example.utils.ui.Ansi.WHITE;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.example.models.UnlockResult;
import com.example.services.AlphanumericHunter;
import com.example.services.PinBreaker;
import com.example.utils.input.Console;
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

    private static final Set<Integer> MAIN_GAPS_AFTER = Set.of(6);

    private static final String PIN_BREAKER_PDF = "data/01_caixa_forta.pdf";
    private static final String ALPHANUMERIC_PDF = "data/02_projecte_secret.pdf";

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
     * Executa la missió 1: cerca el PIN de quatre dígits del PDF corresponent.
     */
    public static void runPinBreaker() {
        runMission("MISSIÓ 1 - PIN BREAKER", PIN_BREAKER_PDF, PinBreaker::breakPin);
    }

    /**
     * Executa la missió 2: cerca la contrasenya alfanumèrica del PDF corresponent.
     */
    public static void runAlphanumericHunter() {
        runMission("MISSIÓ 2 - ALPHANUMERIC HUNTER", ALPHANUMERIC_PDF,
                AlphanumericHunter::breakAlphanumeric);
    }

    private static void runMission(String title, String path, Function<String, UnlockResult> search) {
        Console.clean();
        showHeader(title);
        showInfo("Fitxer: %s%nCercant la contrasenya...", path);

        try {
            UnlockResult result = search.apply(path);
            printResult(result);
        } catch (RuntimeException exception) {
            String message = exception.getMessage();
            showError("%s", message == null ? exception.getClass().getSimpleName() : message);
        }

        Menu.pause();
    }

    private static void printResult(UnlockResult result) {
        printSection("RESULTAT");
        System.out.printf("  Contrasenya trobada: %s%n", result.password());
        System.out.printf("  Intents realitzats:  %,d%n", result.attempts());
        System.out.printf("  Temps:               %.3f s%n", result.getTimeTakenSeconds());
        System.out.printf("  Velocitat:           %,.0f intents/s%n", result.getAttemptsPerSecond());
        System.out.println();
    }

    /**
     * Mostra una capçalera emmarcada amb el títol indicat.
     *
     * @param title títol de la capçalera
     */
    public static void showHeader(String title) {
        int innerWidth = Math.max(title.length() + 2, 52);
        String border = "+" + "=".repeat(innerWidth) + "+";
        String content = " " + title + " ".repeat(innerWidth - title.length() - 1);

        System.out.println();
        System.out.println(BOLD + CYAN + border + RESET);
        System.out.println(BOLD + CYAN + "|" + RESET + BOLD + WHITE + content + RESET + BOLD + CYAN + "|" + RESET);
        System.out.println(BOLD + CYAN + border + RESET);
        System.out.println();
    }

    /**
     * Mostra un missatge informatiu.
     *
     * @param message missatge o format
     * @param args    valors del format
     */
    public static void showInfo(String message, Object... args) {
        System.out.printf("%n%s %s%n", CYAN + BOLD + "[i]" + RESET, format(message, args));
    }

    /**
     * Mostra un missatge d'error.
     *
     * @param message missatge o format
     * @param args    valors del format
     */
    public static void showError(String message, Object... args) {
        System.out.printf("%n%s %s%n", RED + BOLD + "[ERR]" + RESET, format(message, args));
    }

    private static void printSection(String title) {
        System.out.printf("%n%s== %s ==%s%n%n", BOLD + MAGENTA, title, RESET);
    }

    private static String format(String message, Object... args) {
        return args.length == 0 ? message : String.format(message, args);
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
