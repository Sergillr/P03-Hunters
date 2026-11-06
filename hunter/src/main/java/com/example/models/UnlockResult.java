package com.example.models;

/**
 * Records a successful PDF password search.
 *
 * @param password the candidate accepted by PDFBox
 * @param attempts the number of candidates tested, including the accepted candidate
 * @param timeTakenMillis the elapsed search time in whole milliseconds
 */
public record UnlockResult(String password, int attempts, long timeTakenMillis) {
    /**
     * Converts the recorded duration to seconds.
     *
     * @return the elapsed search time in seconds, including fractional seconds
     */
    public double getTimeTakenSeconds() {
        return timeTakenMillis / 1000.0;
    }

    /**
     * Computes the average candidate testing rate from the recorded duration.
     *
     * @return attempts per second, or positive infinity if a successful search took
     *         less than one millisecond and the recorded duration is zero
     */
    public double getAttemptsPerSecond() {
        return attempts / getTimeTakenSeconds();
    }
}
