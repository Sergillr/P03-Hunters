package com.example;

import com.example.utils.input.Menu;
import com.example.utils.ui.Prettier;

public class App {
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    public void run() {
        boolean exit = false;

        while (!exit) {
            int option = Menus.mainMenu();

            switch (option) {
                case 1, 2, 3, 4, 5 -> {
                    Prettier.info("La missió %d encara no està disponible.", option);
                    Menu.pause();
                }
                case Menus.MAIN_EXIT_OPTION -> exit = true;
                default -> {
                    // Menu.getOption() només retorna opcions vàlides.
                }
            }
        }

        Prettier.info("Fins aviat!");
    }
}
