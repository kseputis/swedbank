package com.swedbank.decathlon.result.calculator.api.dto;

import java.math.BigDecimal;

public class CalculatedResult {
    private String participant;
    private String placeTaken;
    private BigDecimal totalScore = BigDecimal.ZERO;
    private String results;

    public CalculatedResult() {
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public String getPlaceTaken() {
        return placeTaken;
    }

    public void setPlaceTaken(String placeTaken) {
        this.placeTaken = placeTaken;
    }

    public BigDecimal getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }
}
