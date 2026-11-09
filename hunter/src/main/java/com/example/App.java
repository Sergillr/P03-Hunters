package com.example;

/**
 * Entry point for the PDF Hunters application.
 */
import com.example.utils.input.Menu;

public class App {
    /** Creates the command-line application. */
    public App() {
    }

    /**
     * Creates and runs the application.
     *
     * @param args command-line arguments, currently unused
     */
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    /** Runs the application. The command-line workflow is currently empty. */
    public void run() {
        boolean exit = false;

        while (!exit) {
            int option = Menus.mainMenu();

            switch (option) {
                case 1 -> Menus.runPinBreaker();
                case 2 -> Menus.runAlphanumericHunter();
                case 3 -> Menus.runDictionaryAttack();
                case 4 -> Menus.runFinalBoss();
                case 5 -> {
                    Menus.showInfo("Les estadístiques encara no estan disponibles.");
                    Menu.pause();
                }
                case Menus.MAIN_EXIT_OPTION -> exit = true;
                default -> {
                    // Menu.getOption() només retorna opcions vàlides.
                }
            }
        }

        Menus.showInfo("Fins aviat!");
    }
}
