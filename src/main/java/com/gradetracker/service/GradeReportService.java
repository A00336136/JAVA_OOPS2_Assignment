package com.gradetracker.service;

import com.gradetracker.model.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Sorting demo (Comparator.comparing, thenComparing, reversed)
 * Multiple sort strategies on students and results
 */
public class GradeReportService {

    private final List<Student> students;
    private final List<Result>  results;

    public GradeReportService(List<Student> students, List<Result> results) {
        this.students = students;
        this.results  = results;
    }

    // Sort by name A-Z
    public List<Student> studentsByName() {
        return students.stream()
                .sorted(Comparator.comparing(Student::name))
                .toList();
    }

    // Sort by GPA descending
    public List<Student> studentsByGpaDesc() {
        return students.stream()
                .sorted(Comparator.comparingDouble(Student::gpa).reversed())
                .toList();
    }

    // Sort by programme, then by name within each programme
    public List<Student> studentsByProgrammeThenName() {
        return students.stream()
                .sorted(Comparator.comparing(Student::programme)
                        .thenComparing(Student::name))
                .toList();
    }

    // Sort results by score descending, then by student ID
    public List<Result> resultsByScoreDesc() {
        return results.stream()
                .sorted(Comparator.comparingDouble(Result::score).reversed()
                        .thenComparing(Result::studentId))
                .toList();
    }

    // Sort results by submission time (earliest first)
    public List<Result> resultsBySubmissionTime() {
        return results.stream()
                .sorted(Comparator.comparing(Result::submittedAt))
                .toList();
    }

    // Pretty-print a leaderboard
    public void printLeaderboard() {
        System.out.println("\n=====================================");
        System.out.println("STUDENT LEADERBOARD (by GPA)");
        System.out.println("=====================================");
        System.out.printf("%-4s %-20s %-12s %5s%n", "Rank", "Name", "Programme", "GPA");
        System.out.println("-------------------------------------");

        List<Student> ranked = studentsByGpaDesc();
        for (int i = 0; i < ranked.size(); i++) {
            Student s = ranked.get(i);
            System.out.printf("%-4d %-20s %-12s %5.2f%n",
                    i + 1, s.name(), s.programme(), s.gpa());
        }
        System.out.println("=====================================");
    }

    // Print top scorers for a given assessment
    public void printTopScorers(String assessmentId, int topN) {
        System.out.printf("%n── Top %d scorers for %s ──%n", topN, assessmentId);
        results.stream()
                .filter(r -> r.assessmentId().equals(assessmentId))
                .sorted(Comparator.comparingDouble(Result::score).reversed())
                .limit(topN)
                .forEach(r -> System.out.printf("  %s : %.1f (%s)%n",
                        r.studentId(), r.score(), Grade.fromScore(r.score()).getLabel()));
    }
}
