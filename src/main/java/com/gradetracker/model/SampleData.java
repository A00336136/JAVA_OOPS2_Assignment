package com.gradetracker.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Hardcoded sample data for testing and demo.
 * In a real app this would come from a database.
 */
public class SampleData {

    public static List<Student> getStudents() {
        return List.of(
            new Student("STU001", "Alice Murphy",   "Software Development", LocalDate.of(2024, 9, 1), 3.8),
            new Student("STU002", "Brian O'Connor",  "Software Development", LocalDate.of(2024, 9, 1), 2.9),
            new Student("STU003", "Ciara Kelly",     "Data Science",         LocalDate.of(2023, 9, 1), 3.5),
            new Student("STU004", "Declan Walsh",    "Software Development", LocalDate.of(2024, 9, 1), 1.8),
            new Student("STU005", "Emma Byrne",      "Cyber Security",       LocalDate.of(2023, 9, 1), 3.2),
            new Student("STU006", "Fiona Doyle",     "Data Science",         LocalDate.of(2024, 9, 1), 2.5),
            new Student("STU007", "Gary Lynch",      "Software Development", LocalDate.of(2023, 9, 1), 3.9),
            new Student("STU008", "Hannah Ryan",     "Cyber Security",       LocalDate.of(2024, 9, 1), 2.1),
            new Student("STU009", "Ian Moran",       "Data Science",         LocalDate.of(2024, 9, 1), 3.0),
            new Student("STU010", "Jenny Collins",   "Software Development", LocalDate.of(2023, 9, 1), 3.6)
        );
    }

    public static List<Module> getModules() {
        return List.of(
            new Module("OOP2",  "Object Oriented Programming 2", 10, "Dr. Smith",   Semester.SPRING),
            new Module("DB2",   "Database Systems 2",             5,  "Dr. Murphy",  Semester.SPRING),
            new Module("WEB2",  "Web Development 2",              5,  "Ms. Kelly",   Semester.SPRING),
            new Module("SEC1",  "Cyber Security Fundamentals",   10,  "Mr. Walsh",   Semester.AUTUMN),
            new Module("MATH2", "Applied Mathematics 2",          5,  "Dr. Brennan", Semester.SPRING)
        );
    }

    // sealed interface subtypes — Exam, Assignment, LabWork
    public static List<Assessment> getAssessments() {
        return List.of(
            new Exam("EX001", "OOP2",  LocalDate.of(2026, 4, 25), LocalTime.of(9, 30), 120, "Hall A"),
            new Exam("EX002", "DB2",   LocalDate.of(2026, 4, 28), LocalTime.of(14, 0),  90, "Hall B"),
            new Exam("EX003", "WEB2",  LocalDate.of(2026, 5, 2),  LocalTime.of(9, 30), 120, "Hall A"),
            new Exam("EX004", "MATH2", LocalDate.of(2026, 4, 25), LocalTime.of(14, 0),  90, "Hall C"),
            new Assignment("AS001", "OOP2",  LocalDate.of(2026, 4, 19), 100),
            new Assignment("AS002", "WEB2",  LocalDate.of(2026, 4, 15),  50),
            new Assignment("AS003", "DB2",   LocalDate.of(2026, 4, 22),  80),
            new LabWork("LB001", "OOP2",  LocalDate.of(2026, 3, 28), "Lab 204"),
            new LabWork("LB002", "SEC1",  LocalDate.of(2026, 3, 20), "Lab 105"),
            new LabWork("LB003", "DB2",   LocalDate.of(2026, 4, 2),  "Lab 204")
        );
    }

    public static List<Result> getResults() {
        return List.of(
            new Result("STU001", "EX001", 88.5, LocalDateTime.of(2026, 4, 25, 11, 30)),
            new Result("STU002", "EX001", 55.0, LocalDateTime.of(2026, 4, 25, 11, 30)),
            new Result("STU003", "EX001", 72.0, LocalDateTime.of(2026, 4, 25, 11, 30)),
            new Result("STU004", "EX001", 35.0, LocalDateTime.of(2026, 4, 25, 11, 30)),
            new Result("STU005", "EX001", 91.0, LocalDateTime.of(2026, 4, 25, 11, 30)),
            new Result("STU006", "EX001", 42.0, LocalDateTime.of(2026, 4, 25, 11, 30)),
            new Result("STU007", "EX001", 95.0, LocalDateTime.of(2026, 4, 25, 11, 30)),
            new Result("STU008", "EX001", 38.0, LocalDateTime.of(2026, 4, 25, 11, 30)),
            new Result("STU001", "AS001", 92.0, LocalDateTime.of(2026, 4, 18, 23, 55)),
            new Result("STU002", "AS001", 61.0, LocalDateTime.of(2026, 4, 19, 10, 30)),
            new Result("STU003", "AS001", 78.5, LocalDateTime.of(2026, 4, 17, 14, 20)),
            new Result("STU005", "AS001", 85.0, LocalDateTime.of(2026, 4, 18, 20, 0)),
            new Result("STU007", "AS001", 97.0, LocalDateTime.of(2026, 4, 16, 9, 15)),
            new Result("STU001", "LB001", 80.0, LocalDateTime.of(2026, 3, 28, 16, 0)),
            new Result("STU003", "LB001", 65.0, LocalDateTime.of(2026, 3, 28, 16, 0)),
            new Result("STU004", "LB001", 45.0, LocalDateTime.of(2026, 3, 28, 16, 0)),
            new Result("STU009", "EX001", 67.0, LocalDateTime.of(2026, 4, 25, 11, 30)),
            new Result("STU010", "EX001", 83.0, LocalDateTime.of(2026, 4, 25, 11, 30))
        );
    }
}
