package com.gradetracker.model;

import java.time.LocalDateTime;

// F14: Record for exam/assignment results
public record Result(
    String studentId,
    String assessmentId,
    double score,
    LocalDateTime submittedAt
) {}
