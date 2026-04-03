package com.gradetracker.util;

import com.gradetracker.model.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Switch Expressions with Pattern Matching demo
 * Shows pattern matching on sealed Assessment types
 */
public class AssessmentProcessor {

    private static final DateTimeFormatter DATE_FMT  = DateTimeFormatter.ofPattern("dd MMM yyyy");
    private static final DateTimeFormatter TIME_FMT  = DateTimeFormatter.ofPattern("HH:mm");

    // Pattern-match on sealed interface subtypes
    public static String describe(Assessment assessment) {
        return switch (assessment) {
            case Exam e when e.durationMinutes() > 90 ->
                    "[EXAM-LONG] Long exam: %s on %s, %d mins in %s (starts %s)"
                            .formatted(e.moduleCode(), e.date().format(DATE_FMT),
                                    e.durationMinutes(), e.venue(),
                                    e.startTime().format(TIME_FMT));

            case Exam e ->
                    "[EXAM] Exam: %s on %s, %d mins in %s (starts %s)"
                            .formatted(e.moduleCode(), e.date().format(DATE_FMT),
                                    e.durationMinutes(), e.venue(),
                                    e.startTime().format(TIME_FMT));

            case Assignment a when a.maxMarks() >= 100 ->
                    "[ASSIGN-MAJOR] Major Assignment: %s due %s (max %,.0f marks)"
                            .formatted(a.moduleCode(), a.date().format(DATE_FMT), a.maxMarks());

            case Assignment a ->
                    "[ASSIGN] Assignment: %s due %s (max %,.0f marks)"
                            .formatted(a.moduleCode(), a.date().format(DATE_FMT), a.maxMarks());

            case LabWork lb ->
                    "[LAB] Lab Work: %s on %s in %s"
                            .formatted(lb.moduleCode(), lb.date().format(DATE_FMT), lb.labRoom());
        };
    }

    // Calculate weighting factor by assessment type
    public static double weightingFactor(Assessment assessment) {
        return switch (assessment) {
            case Exam e when e.durationMinutes() >= 120 -> 0.50;
            case Exam e                                  -> 0.35;
            case Assignment a when a.maxMarks() >= 100   -> 0.30;
            case Assignment a                            -> 0.15;
            case LabWork lb                              -> 0.10;
        };
    }

    // Urgency level based on type
    public static String urgencyLevel(Assessment assessment) {
        return switch (assessment) {
            case Exam e      -> "[HIGH] exam preparation required";
            case Assignment a -> "[MEDIUM] submission deadline";
            case LabWork lb  -> "[LOW] lab attendance";
        };
    }

    // Demo method
    public static void demonstrateSwitchPatterns(List<Assessment> assessments) {
        System.out.println("\n=== Switch Expressions + Pattern Matching (Sealed Types) ===\n");

        System.out.println("── describe() with guarded patterns ──");
        assessments.forEach(a -> System.out.println("  " + describe(a)));

        System.out.println("\n── weightingFactor() ──");
        assessments.forEach(a ->
                System.out.printf("  %-6s (%s): weight = %.0f%%%n",
                        a.id(), a.type(), weightingFactor(a) * 100));

        System.out.println("\n── urgencyLevel() ──");
        assessments.forEach(a ->
                System.out.printf("  %-6s → %s%n", a.id(), urgencyLevel(a)));
    }
}
