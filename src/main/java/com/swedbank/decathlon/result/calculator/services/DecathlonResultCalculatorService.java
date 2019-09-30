package com.swedbank.decathlon.result.calculator.services;

import com.swedbank.decathlon.result.calculator.api.dto.CalculatedResult;
import com.swedbank.decathlon.result.calculator.api.dto.CalculatedResultsResponse;
import com.swedbank.decathlon.result.calculator.api.dto.ParticipantResult;
import com.swedbank.decathlon.result.calculator.api.dto.Result;
import com.swedbank.decathlon.result.calculator.api.dto.enums.CompetitionType;
import com.swedbank.decathlon.result.calculator.api.dto.enums.CompetitionTypeValues;
import com.swedbank.decathlon.result.calculator.constants.Messages;
import com.swedbank.decathlon.result.calculator.exceptions.ApplicationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DecathlonResultCalculatorService {

    public CalculatedResultsResponse calculateResults(List<ParticipantResult> results) {
        List<CalculatedResult> calculatedResultList = new ArrayList<>();
        Set<BigDecimal> uniqueScores = new HashSet<>();
        results.forEach(res -> {
            CalculatedResult calculatedResult = new CalculatedResult();
            calculatedResult.setParticipant(res.getParticipant());
            res.getResults().forEach(result -> calculatedResult.setTotalScore(calculatedResult.getTotalScore().add(calculateScore(result))));
            uniqueScores.add(calculatedResult.getTotalScore());
            calculatedResult.setResults(res.getResults().toString());
            calculatedResultList.add(calculatedResult);
        });
        return new CalculatedResultsResponse(countParticipantsPlaces(calculatedResultList, uniqueScores));
    }

    private BigDecimal calculateScore(Result result) {
        double score;
        if (CompetitionType.JUMP_EVENT.equals(result.getType())) {
            score = countJumpEventPoints(Double.parseDouble(result.getScore()), result.getValues());
        } else if (CompetitionType.TRACK_EVENT.equals(result.getType())) {
            double sc;
            if (CompetitionTypeValues.FIFTEEN_HUNDRED_METERS_RACE.equals(result.getValues())) {
                sc = parseTime(result.getScore());
            } else {
                sc = Double.parseDouble(result.getScore());
            }
            score = countTrackEventPoints(sc, result.getValues());
        } else if (CompetitionType.THROW_EVENT.equals(result.getType())) {
            score = countThrowEventPoints(Double.parseDouble(result.getScore()), result.getValues());
        } else {
            throw new ApplicationException(Messages.ERROR_WHILE_CALCULATING_SCORE);
        }
        return BigDecimal.valueOf(score).setScale(0, RoundingMode.HALF_UP);
    }

    private double countTrackEventPoints(double result, CompetitionTypeValues type) {
        return Math.pow(type.getB() - result, type.getC()) * type.getA();
    }

    private double countJumpEventPoints(double result, CompetitionTypeValues type) {
        return Math.pow(result * 100 - type.getB(), type.getC()) * type.getA();
    }

    private double countThrowEventPoints(double result, CompetitionTypeValues type) {
        return Math.pow(result - type.getB(), type.getC()) * type.getA();
    }

    private double parseTime(String time) {
        return Double.parseDouble(time.substring(0, time.indexOf('.'))) * 60 + Double.parseDouble(time.substring(time.indexOf('.') + 1));
    }

    private List<CalculatedResult> countParticipantsPlaces(List<CalculatedResult> results, Set<BigDecimal> uniqueScores) {
        List<BigDecimal> sortedScores = uniqueScores.stream()
                .sorted(Comparator.comparing(BigDecimal::longValue).reversed())
                .collect(Collectors.toList());
        List<CalculatedResult> sortedResults = results.stream()
                .sorted(Comparator.comparing(CalculatedResult::getTotalScore).reversed())
                .collect(Collectors.toList());
        int placeTaken = 1;
        for (BigDecimal score : sortedScores) {
            List<CalculatedResult> participantsWithEqualScores = sortedResults.stream()
                    .filter(res -> score.equals(res.getTotalScore()))
                    .collect(Collectors.toList());
            if (participantsWithEqualScores.size() == 1) {
                participantsWithEqualScores.get(0).setPlaceTaken(String.valueOf(placeTaken));
                placeTaken++;
            } else {
                int sumOfEqualScores = participantsWithEqualScores.size();
                String equalPlaces = placeTaken + "-" + (placeTaken + sumOfEqualScores - 1);
                for (CalculatedResult result : participantsWithEqualScores) {
                    result.setPlaceTaken(equalPlaces);
                }
                placeTaken = placeTaken + sumOfEqualScores;
            }
        }
        return sortedResults;
    }
}
