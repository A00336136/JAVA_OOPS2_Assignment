package com.gradetracker.model;

import java.time.LocalDate;

// F7: Sealed interface — only Exam, Assignment, LabWork can implement this
public sealed interface Assessment permits Exam, Assignment, LabWork {
    String id();
    String moduleCode();
    LocalDate date();

    default String type() {
        return this.getClass().getSimpleName();
    }
}
