package com.example.models;

public record UnlockResult(String password, int attempts, long timeTakenMillis) {
    public double getTimeTakenSeconds() {
        return timeTakenMillis / 1000.0;
    }

    public double getAttemptsPerSecond() {
        return attempts / getTimeTakenSeconds();
    }
}
