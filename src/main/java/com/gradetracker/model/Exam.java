package com.gradetracker.model;

import java.time.LocalDate;
import java.time.LocalTime;

// F14: Record implementing sealed interface
public record Exam(
    String id,
    String moduleCode,
    LocalDate date,
    LocalTime startTime,
    int durationMinutes,
    String venue
) implements Assessment {}
