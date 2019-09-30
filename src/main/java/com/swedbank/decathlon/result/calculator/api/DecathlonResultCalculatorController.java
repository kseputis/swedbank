package com.swedbank.decathlon.result.calculator.api;

import com.swedbank.decathlon.result.calculator.api.dto.CalculatedResultsResponse;
import com.swedbank.decathlon.result.calculator.api.dto.ParticipantResult;
import com.swedbank.decathlon.result.calculator.api.dto.Result;
import com.swedbank.decathlon.result.calculator.api.dto.enums.CompetitionTypeValues;
import com.swedbank.decathlon.result.calculator.constants.Messages;
import com.swedbank.decathlon.result.calculator.exceptions.ApplicationException;
import com.swedbank.decathlon.result.calculator.services.DecathlonResultCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.swedbank.decathlon.result.calculator.api.dto.enums.CompetitionType.JUMP_EVENT;
import static com.swedbank.decathlon.result.calculator.api.dto.enums.CompetitionType.THROW_EVENT;
import static com.swedbank.decathlon.result.calculator.api.dto.enums.CompetitionType.TRACK_EVENT;

@RestController
@RequestMapping("/calculator")
public class DecathlonResultCalculatorController {

    private static final String CSV_FILE_SEPARATOR = ";";

    private DecathlonResultCalculatorService service;

    @Autowired
    public DecathlonResultCalculatorController(DecathlonResultCalculatorService service) {
        this.service = service;
    }

    @PostMapping(value = "/calculate", produces = MediaType.APPLICATION_XML_VALUE)
    public CalculatedResultsResponse calculateResult(@RequestParam("file") MultipartFile file) {
        List<ParticipantResult> results = parseResults(file);
        return service.calculateResults(results);
    }

    private List<ParticipantResult> parseResults(@RequestParam("file") MultipartFile file) {
        List<ParticipantResult> results = new ArrayList<>();
        InputStream is;
        BufferedReader bfReader;
        try {
            is = file.getInputStream();
            bfReader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = bfReader.readLine()) != null && !line.isEmpty()) {
                String[] data = line.split(CSV_FILE_SEPARATOR);
                List<Result> resultList = new ArrayList<>();
                ParticipantResult participantResult = new ParticipantResult();
                participantResult.setParticipant(data[0]);
                resultList.add(new Result(TRACK_EVENT, data[1], CompetitionTypeValues.ONE_HUNDRED_METER_RACE));
                resultList.add(new Result(JUMP_EVENT, data[2], CompetitionTypeValues.LONG_JUMP));
                resultList.add(new Result(THROW_EVENT, data[3], CompetitionTypeValues.SHOT_PUT));
                resultList.add(new Result(JUMP_EVENT, data[4], CompetitionTypeValues.HIGH_JUMP));
                resultList.add(new Result(TRACK_EVENT, data[5], CompetitionTypeValues.FOUR_HUNDRED_METER_RACE));
                resultList.add(new Result(TRACK_EVENT, data[6], CompetitionTypeValues.HURDLES_RACE));
                resultList.add(new Result(THROW_EVENT, data[7], CompetitionTypeValues.DISCUS_THROW));
                resultList.add(new Result(JUMP_EVENT, data[8], CompetitionTypeValues.POLE_VAULT));
                resultList.add(new Result(THROW_EVENT, data[9], CompetitionTypeValues.JAVELIN_THROW));
                resultList.add(new Result(TRACK_EVENT, data[10], CompetitionTypeValues.FIFTEEN_HUNDRED_METERS_RACE));
                participantResult.setResults(resultList);
                results.add(participantResult);
            }
        } catch (IOException e) {
            throw new ApplicationException(Messages.ERROR_WHILE_READING_CSV_FILE);
        }
        return results;
    }
}
