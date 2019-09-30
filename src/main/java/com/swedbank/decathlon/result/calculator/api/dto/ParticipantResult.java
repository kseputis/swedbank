package com.swedbank.decathlon.result.calculator.api.dto;

import java.util.ArrayList;
import java.util.List;

public class ParticipantResult {
    private String participant;
    private List<Result> results = new ArrayList<>();

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}

