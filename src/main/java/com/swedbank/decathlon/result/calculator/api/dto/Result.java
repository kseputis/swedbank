package com.swedbank.decathlon.result.calculator.api.dto;

import com.swedbank.decathlon.result.calculator.api.dto.enums.CompetitionType;
import com.swedbank.decathlon.result.calculator.api.dto.enums.CompetitionTypeValues;

public class Result {
    private CompetitionType type;
    private String score;
    private CompetitionTypeValues values;

    public Result(CompetitionType type, String score, CompetitionTypeValues values) {
        this.type = type;
        this.score = score;
        this.values = values;
    }

    public CompetitionTypeValues getValues() {
        return values;
    }

    public void setValues(CompetitionTypeValues values) {
        this.values = values;
    }

    public CompetitionType getType() {
        return type;
    }

    public void setType(CompetitionType type) {
        this.type = type;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Competition: "+this.values+" Score: "+this.score;
    }
}
