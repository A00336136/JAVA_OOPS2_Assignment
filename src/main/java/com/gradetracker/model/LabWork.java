package com.gradetracker.model;

import java.time.LocalDate;

// F14: Record implementing sealed interface
public record LabWork(
    String id,
    String moduleCode,
    LocalDate date,     // scheduled date
    String labRoom
) implements Assessment {}
