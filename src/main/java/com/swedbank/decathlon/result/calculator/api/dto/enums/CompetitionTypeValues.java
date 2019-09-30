package com.swedbank.decathlon.result.calculator.api.dto.enums;

public enum CompetitionTypeValues {

    ONE_HUNDRED_METER_RACE(25.4347, 18, 1.81, "100 m"),
    LONG_JUMP(0.14354, 220, 1.4, "Long jump"),
    SHOT_PUT(51.39, 1.5, 1.05, "Shot put"),
    HIGH_JUMP(0.8465, 75, 1.42, "High jump"),
    FOUR_HUNDRED_METER_RACE(1.53775, 82, 1.81, "400 m"),
    HURDLES_RACE(5.74352, 28.5, 1.92, "110 m hurdles"),
    DISCUS_THROW(12.91, 4, 1.1, "Discus throw"),
    POLE_VAULT(0.2797, 100, 1.35, "Pole vault"),
    JAVELIN_THROW(10.14, 7, 1.08, "Javelin throw"),
    FIFTEEN_HUNDRED_METERS_RACE(0.03768, 480, 1.85, "1500 m");

    private double a;
    private double b;
    private double c;
    private String name;

    CompetitionTypeValues(double a, double b, double c, String name) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.name = name;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public String getName() {
        return name;
    }
}
