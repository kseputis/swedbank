package com.swedbank.decathlon.result.calculator.api.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "calculatedResultResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class CalculatedResultsResponse {
    @XmlElement
    private List<CalculatedResult> calculatedResults;

    public CalculatedResultsResponse() {
    }

    public CalculatedResultsResponse(List<CalculatedResult> calculatedResults) {
        this.calculatedResults = calculatedResults;
    }

    public List<CalculatedResult> getCalculatedResults() {
        return calculatedResults;
    }

    public void setCalculatedResults(List<CalculatedResult> calculatedResults) {
        this.calculatedResults = calculatedResults;
    }
}
