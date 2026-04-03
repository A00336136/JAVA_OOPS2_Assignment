package com.gradetracker.model;

public enum Grade {
    A_PLUS("A+", 90),
    A("A", 80),
    B_PLUS("B+", 70),
    B("B", 60),
    C("C", 50),
    D("D", 40),
    F("F", 0);

    private final String label;
    private final int minScore;

    Grade(String label, int minScore) {
        this.label = label;
        this.minScore = minScore;
    }

    public String getLabel() { return label; }
    public int getMinScore() { return minScore; }

    // convert a numeric score to a Grade
    public static Grade fromScore(double score) {
        if (score >= 90) return A_PLUS;
        if (score >= 80) return A;
        if (score >= 70) return B_PLUS;
        if (score >= 60) return B;
        if (score >= 50) return C;
        if (score >= 40) return D;
        return F;
    }
}
