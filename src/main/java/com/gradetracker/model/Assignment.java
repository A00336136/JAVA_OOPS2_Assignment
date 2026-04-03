package com.gradetracker.model;

import java.time.LocalDate;

// F14: Record implementing sealed interface
public record Assignment(
    String id,
    String moduleCode,
    LocalDate date,   // due date
    double maxMarks
) implements Assessment {}
