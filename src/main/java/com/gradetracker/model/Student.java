package com.gradetracker.model;

import java.time.LocalDate;

// F14: Record — immutable data carrier for student info
public record Student(
    String id,
    String name,
    String programme,
    LocalDate enrollDate,
    double gpa
) {}
