package com.example.utils.ui;

/**
 * Utility that clears the console using the current operating system command.
 */
public final class Cleaner {
    /**
     * Creates a console cleaner configured for the current operating system.
     */
    public Cleaner() {
        cls = getCls();
    }

    private ProcessBuilder cls;
    private static final int DEFAULT_AUX = 20;

    /**
     * Builds the command used to clear the console.
     *
     * @return a process builder configured with the clear-screen command
     */
    private static ProcessBuilder getCls() {
        boolean isWindows = System.getProperty("os.name", "").startsWith("Windows");
        ProcessBuilder processBuilder;

        if (isWindows) {
            processBuilder = new ProcessBuilder("cmd.exe", "/d", "/q", "/c", "cls");
        } else {
            processBuilder = new ProcessBuilder("clear");
        }

        return processBuilder.inheritIO();
    }

    /**
     * Clears the console. If the operating system command fails, it prints blank
     * lines to emulate the clear operation.
     *
     * @param aux number of blank lines to print when the clear command fails
     */
    public void clear(int aux) {
        try {
            cls.start().waitFor();
            return;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception ignored) {
            // Ignore clear command failures and use the fallback below.
        }

        emulate(aux); // Fallback emulation.
    }

    /**
     * Clears the console. If the operating system command fails, it prints
     * {@value Cleaner#DEFAULT_AUX} blank lines to emulate the clear operation.
     */
    public void clear() {
        clear(DEFAULT_AUX);
    }

    private void emulate(int space) {
        if (space <= 0)
            return;
        System.out.print("\n".repeat(space));
    }
}
