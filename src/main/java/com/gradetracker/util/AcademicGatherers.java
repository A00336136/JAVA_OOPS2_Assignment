package com.gradetracker.util;

import com.gradetracker.model.*;

import java.util.*;
import java.util.stream.*;

/**
 * Stream Gatherers demo (JEP 485)
 * Shows custom intermediate stream operations
 */
public class AcademicGatherers {

    // Built-in Gatherers: windowFixed
    // Groups elements into fixed-size windows (e.g., batches of 3)
    public static void demonstrateWindowFixed(List<Student> students) {
        System.out.println("  ── windowFixed(3) — batch students into groups of 3 ──");
        students.stream()
                .gather(Gatherers.windowFixed(3))
                .forEach(window -> {
                    System.out.print("    Batch: ");
                    window.forEach(s -> System.out.printf("%s (%.1f)  ", s.name(), s.gpa()));
                    System.out.println();
                });
    }

    // Built-in Gatherers: windowSliding
    // Sliding window for computing moving averages
    public static void demonstrateWindowSliding(List<Result> results) {
        System.out.println("\n  ── windowSliding(3) — moving average of scores ──");
        List<Result> sorted = results.stream()
                .sorted(Comparator.comparing(Result::submittedAt))
                .toList();

        sorted.stream()
                .gather(Gatherers.windowSliding(3))
                .forEach(window -> {
                    double avg = window.stream()
                            .mapToDouble(Result::score)
                            .average()
                            .orElse(0);
                    System.out.printf("    Window [%s..%s]: avg = %.1f%n",
                            window.getFirst().studentId(),
                            window.getLast().studentId(), avg);
                });
    }

    // Built-in Gatherers: fold
    // Fold to accumulate a running summary
    public static void demonstrateFold(List<Result> results) {
        System.out.println("\n  ── fold — accumulate total & count for running average ──");
        record Stats(double total, int count) {
            double average() { return count > 0 ? total / count : 0; }
        }

        results.stream()
                .gather(Gatherers.fold(
                        () -> new Stats(0, 0),
                        (stats, result) -> new Stats(
                                stats.total() + result.score(),
                                stats.count() + 1)))
                .forEach(stats ->
                        System.out.printf("    Final: total=%.1f, count=%d, avg=%.2f%n",
                                stats.total(), stats.count(), stats.average()));
    }

    // Built-in Gatherers: scan
    // Running total of scores
    public static void demonstrateScan(List<Result> results) {
        System.out.println("\n  ── scan — running total of scores ──");
        results.stream()
                .limit(6)
                .gather(Gatherers.scan(() -> 0.0, (sum, r) -> sum + r.score()))
                .forEach(runningTotal ->
                        System.out.printf("    Running total: %.1f%n", runningTotal));
    }

    // Built-in Gatherers: mapConcurrent
    // Concurrent mapping (parallel evaluation within stream pipeline)
    public static void demonstrateMapConcurrent(List<Student> students) {
        System.out.println("\n  ── mapConcurrent(3) — parallel GPA classification ──");
        students.stream()
                .gather(Gatherers.mapConcurrent(3, student -> {
                    String classification = student.gpa() >= 3.5 ? "Honours"
                            : student.gpa() >= 2.0 ? "Standard" : "Probation";
                    return "%s → %s (by %s)".formatted(
                            student.name(), classification,
                            Thread.currentThread().getName());
                }))
                .forEach(s -> System.out.println("    " + s));
    }

    // Full demo
    public static void demonstrateGatherers() {
        System.out.println("\n=== Stream Gatherers (JEP 485) ===\n");

        List<Student> students = SampleData.getStudents();
        List<Result>  results  = SampleData.getResults();

        demonstrateWindowFixed(students);
        demonstrateWindowSliding(results);
        demonstrateFold(results);
        demonstrateScan(results);
        demonstrateMapConcurrent(students);

        System.out.println("\n  Gatherers demo complete.");
    }
}
