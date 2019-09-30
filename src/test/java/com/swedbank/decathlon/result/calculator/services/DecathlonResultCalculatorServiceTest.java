package com.swedbank.decathlon.result.calculator.services;

import com.swedbank.decathlon.result.calculator.api.dto.CalculatedResultsResponse;
import com.swedbank.decathlon.result.calculator.api.dto.ParticipantResult;
import com.swedbank.decathlon.result.calculator.api.dto.Result;
import com.swedbank.decathlon.result.calculator.api.dto.enums.CompetitionType;
import com.swedbank.decathlon.result.calculator.api.dto.enums.CompetitionTypeValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class DecathlonResultCalculatorServiceTest {
    @InjectMocks
    private DecathlonResultCalculatorService service;

    @Test
    public void calculateResults_requestedDataWithoutEqualScores_calculatedResultsReturned() {
        List<ParticipantResult> list = new ArrayList<>();

        ParticipantResult participantResult = new ParticipantResult();
        participantResult.setParticipant("Tadas");
        participantResult.setResults(Collections.singletonList(
                createResults(
                        "41.72",
                        CompetitionType.THROW_EVENT,
                        CompetitionTypeValues.DISCUS_THROW)
        ));
        list.add(participantResult);

        ParticipantResult participantResult2 = new ParticipantResult();
        participantResult2.setParticipant("Tomas");
        participantResult2.setResults(Collections.singletonList(
                createResults("42.73", CompetitionType.THROW_EVENT, CompetitionTypeValues.DISCUS_THROW)
        ));
        list.add(participantResult2);

        CalculatedResultsResponse result = service.calculateResults(list);

        assertEquals("Tomas", result.getCalculatedResults().get(0).getParticipant());
        assertEquals("1", result.getCalculatedResults().get(0).getPlaceTaken());
        assertEquals("Tadas", result.getCalculatedResults().get(1).getParticipant());
        assertEquals("2", result.getCalculatedResults().get(1).getPlaceTaken());
    }

    @Test
    public void calculateResults_requestedDataWithTwoEqualScores_calculatedResultsReturned() {
        List<ParticipantResult> list = new ArrayList<>();

        ParticipantResult participantResult = new ParticipantResult();
        participantResult.setParticipant("Tadas");
        participantResult.setResults(Collections.singletonList(
                createResults(
                        "10",
                        CompetitionType.THROW_EVENT,
                        CompetitionTypeValues.DISCUS_THROW)
        ));
        list.add(participantResult);

        ParticipantResult participantResult2 = new ParticipantResult();
        participantResult2.setParticipant("Tomas");
        participantResult2.setResults(Collections.singletonList(
                createResults(
                        "41.73",
                        CompetitionType.THROW_EVENT,
                        CompetitionTypeValues.DISCUS_THROW)
        ));
        list.add(participantResult2);

        ParticipantResult participantResult3 = new ParticipantResult();
        participantResult3.setParticipant("Petras");
        participantResult3.setResults(Collections.singletonList(
                createResults("10", CompetitionType.THROW_EVENT, CompetitionTypeValues.DISCUS_THROW)
        ));
        list.add(participantResult3);

        ParticipantResult participantResult4 = new ParticipantResult();
        participantResult4.setParticipant("Adomas");
        participantResult4.setResults(Collections.singletonList(
                createResults(
                        "49",
                        CompetitionType.THROW_EVENT,
                        CompetitionTypeValues.DISCUS_THROW)
        ));
        list.add(participantResult4);

        CalculatedResultsResponse result = service.calculateResults(list);

        assertEquals("1", result.getCalculatedResults().get(0).getPlaceTaken());
        assertEquals("2", result.getCalculatedResults().get(1).getPlaceTaken());
        assertEquals("3-4", result.getCalculatedResults().get(2).getPlaceTaken());
        assertEquals("3-4", result.getCalculatedResults().get(3).getPlaceTaken());
    }

    @Test
    public void calculateResults_requestedDataWithThreeEqualScores_calculatedResultsReturned() {
        List<ParticipantResult> list = new ArrayList<>();

        ParticipantResult participantResult = new ParticipantResult();
        participantResult.setParticipant("Tadas");
        participantResult.setResults(Collections.singletonList(
                createResults(
                        "10",
                        CompetitionType.THROW_EVENT,
                        CompetitionTypeValues.DISCUS_THROW)
        ));
        list.add(participantResult);

        ParticipantResult participantResult2 = new ParticipantResult();
        participantResult2.setParticipant("Tomas");
        participantResult2.setResults(Collections.singletonList(
                createResults(
                        "41.73",
                        CompetitionType.THROW_EVENT,
                        CompetitionTypeValues.DISCUS_THROW)
        ));
        list.add(participantResult2);

        ParticipantResult participantResult3 = new ParticipantResult();
        participantResult3.setParticipant("Petras");
        participantResult3.setResults(Collections.singletonList(
                createResults("10", CompetitionType.THROW_EVENT, CompetitionTypeValues.DISCUS_THROW)
        ));
        list.add(participantResult3);

        ParticipantResult participantResult4 = new ParticipantResult();
        participantResult4.setParticipant("Adomas");
        participantResult4.setResults(Collections.singletonList(
                createResults(
                        "49",
                        CompetitionType.THROW_EVENT,
                        CompetitionTypeValues.DISCUS_THROW)
        ));
        list.add(participantResult4);

        ParticipantResult participantResult5 = new ParticipantResult();
        participantResult5.setParticipant("Algis");
        participantResult5.setResults(Collections.singletonList(
                createResults(
                        "10",
                        CompetitionType.THROW_EVENT,
                        CompetitionTypeValues.DISCUS_THROW)
        ));
        list.add(participantResult5);

        CalculatedResultsResponse result = service.calculateResults(list);

        assertEquals("1", result.getCalculatedResults().get(0).getPlaceTaken());
        assertEquals("2", result.getCalculatedResults().get(1).getPlaceTaken());
        assertEquals("3-5", result.getCalculatedResults().get(2).getPlaceTaken());
        assertEquals("3-5", result.getCalculatedResults().get(3).getPlaceTaken());
        assertEquals("3-5", result.getCalculatedResults().get(4).getPlaceTaken());
    }

    @Test
    public void calculateResults_requestedDataWithSeveralEqualScores_calculatedResultsReturned() {
        List<ParticipantResult> list = new ArrayList<>();

        ParticipantResult participantResult = new ParticipantResult();
        participantResult.setParticipant("Tadas");
        participantResult.setResults(Collections.singletonList(
                createResults(
                        "10",
                        CompetitionType.THROW_EVENT,
                        CompetitionTypeValues.DISCUS_THROW)
        ));
        list.add(participantResult);

        ParticipantResult participantResult2 = new ParticipantResult();
        participantResult2.setParticipant("Tomas");
        participantResult2.setResults(Collections.singletonList(
                createResults(
                        "41.73",
                        CompetitionType.THROW_EVENT,
                        CompetitionTypeValues.DISCUS_THROW)
        ));
        list.add(participantResult2);

        ParticipantResult participantResult3 = new ParticipantResult();
        participantResult3.setParticipant("Petras");
        participantResult3.setResults(Collections.singletonList(
                createResults("10", CompetitionType.THROW_EVENT, CompetitionTypeValues.DISCUS_THROW)
        ));
        list.add(participantResult3);

        ParticipantResult participantResult4 = new ParticipantResult();
        participantResult4.setParticipant("Adomas");
        participantResult4.setResults(Collections.singletonList(
                createResults(
                        "49",
                        CompetitionType.THROW_EVENT,
                        CompetitionTypeValues.DISCUS_THROW)
        ));
        list.add(participantResult4);

        ParticipantResult participantResult5 = new ParticipantResult();
        participantResult5.setParticipant("Algis");
        participantResult5.setResults(Collections.singletonList(
                createResults(
                        "10",
                        CompetitionType.THROW_EVENT,
                        CompetitionTypeValues.DISCUS_THROW)
        ));
        list.add(participantResult5);

        ParticipantResult participantResult6 = new ParticipantResult();
        participantResult6.setParticipant("Stasys");
        participantResult6.setResults(Collections.singletonList(
                createResults(
                        "49",
                        CompetitionType.THROW_EVENT,
                        CompetitionTypeValues.DISCUS_THROW)
        ));
        list.add(participantResult6);

        ParticipantResult participantResult7 = new ParticipantResult();
        participantResult7.setParticipant("Vaclovas");
        participantResult7.setResults(Collections.singletonList(
                createResults(
                        "5",
                        CompetitionType.THROW_EVENT,
                        CompetitionTypeValues.DISCUS_THROW)
        ));
        list.add(participantResult7);

        CalculatedResultsResponse result = service.calculateResults(list);

        assertEquals("1-2", result.getCalculatedResults().get(0).getPlaceTaken());
        assertEquals("1-2", result.getCalculatedResults().get(1).getPlaceTaken());
        assertEquals("3", result.getCalculatedResults().get(2).getPlaceTaken());
        assertEquals("4-6", result.getCalculatedResults().get(3).getPlaceTaken());
        assertEquals("4-6", result.getCalculatedResults().get(4).getPlaceTaken());
        assertEquals("4-6", result.getCalculatedResults().get(5).getPlaceTaken());
        assertEquals("7", result.getCalculatedResults().get(6).getPlaceTaken());
    }

    private Result createResults(String score, CompetitionType type, CompetitionTypeValues values) {
        return new Result(type, score, values);
    }


}
