package com.example;

import com.example.models.UnlockResult;
import com.example.services.PinBreaker;

public class App {
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    public void run() {
        UnlockResult result = PinBreaker.breakPin("data/01_caixa_forta.pdf");
        System.out.println(result.getAttemptsPerSecond());
    }
}
