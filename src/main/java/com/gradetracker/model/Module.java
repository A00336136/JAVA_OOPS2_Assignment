package com.gradetracker.model;

// F14: Record for module/course info
public record Module(
    String code,
    String title,
    int credits,
    String lecturer,
    Semester semester
) {}
