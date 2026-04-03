package com.gradetracker.service;

import com.gradetracker.model.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Concurrency demo with ExecutorService, Callable, and Future
 * Parallel processing of student results
 */
public class ResultsProcessingService {

    private final List<Result>  results;
    private final List<Student> students;

    public ResultsProcessingService(List<Result> results, List<Student> students) {
        this.results  = results;
        this.students = students;
    }

    // Callable: compute average score for a given assessment
    private Callable<String> averageScoreTask(String assessmentId) {
        return () -> {
            String threadName = Thread.currentThread().getName();
            System.out.printf("  [%s] Computing average for %s …%n", threadName, assessmentId);

            // simulate some work
            Thread.sleep(200);

            double avg = results.stream()
                    .filter(r -> r.assessmentId().equals(assessmentId))
                    .mapToDouble(Result::score)
                    .average()
                    .orElse(0.0);

            long count = results.stream()
                    .filter(r -> r.assessmentId().equals(assessmentId))
                    .count();

            return "  %s → avg = %.1f (from %d results) [processed by %s]"
                    .formatted(assessmentId, avg, count, threadName);
        };
    }

    // Callable: find top student for a given assessment
    private Callable<String> topStudentTask(String assessmentId) {
        return () -> {
            String threadName = Thread.currentThread().getName();
            Thread.sleep(150);

            Optional<Result> top = results.stream()
                    .filter(r -> r.assessmentId().equals(assessmentId))
                    .max(Comparator.comparingDouble(Result::score));

            Map<String, Student> lookup = students.stream()
                    .collect(Collectors.toMap(Student::id, s -> s));

            return top.map(r -> {
                String name = lookup.containsKey(r.studentId())
                        ? lookup.get(r.studentId()).name()
                        : r.studentId();
                return "  %s → Top: %s with %.1f [processed by %s]"
                        .formatted(assessmentId, name, r.score(), threadName);
            }).orElse("  %s → No results [processed by %s]"
                    .formatted(assessmentId, threadName));
        };
    }

    // Callable: grade distribution for a given assessment
    private Callable<String> gradeDistributionTask(String assessmentId) {
        return () -> {
            Thread.sleep(100);
            Map<Grade, Long> dist = results.stream()
                    .filter(r -> r.assessmentId().equals(assessmentId))
                    .collect(Collectors.groupingBy(
                            r -> Grade.fromScore(r.score()), Collectors.counting()));

            String distStr = dist.entrySet().stream()
                    .sorted(Comparator.comparing(e -> e.getKey().ordinal()))
                    .map(e -> e.getKey().getLabel() + ":" + e.getValue())
                    .collect(Collectors.joining(", "));

            return "  %s → Grades: [%s] [processed by %s]"
                    .formatted(assessmentId, distStr,
                            Thread.currentThread().getName());
        };
    }

    // Full concurrency demo
    public void demonstrateConcurrency() {
        System.out.println("\n=== Concurrency - ExecutorService + Callable + Future ===\n");

        // Collect unique assessment IDs
        List<String> assessmentIds = results.stream()
                .map(Result::assessmentId)
                .distinct()
                .sorted()
                .toList();

        // Create thread pool with 3 threads
        ExecutorService executor = Executors.newFixedThreadPool(3);
        System.out.println("  Created FixedThreadPool with 3 threads.\n");

        try {
            // Task 1: Submit Callable tasks for average scores
            System.out.println("── Submitting average-score tasks ──");
            List<Future<String>> avgFutures = new ArrayList<>();
            for (String aid : assessmentIds) {
                avgFutures.add(executor.submit(averageScoreTask(aid)));
            }

            // Collect results from futures
            System.out.println("\n── Average Score Results ──");
            for (Future<String> f : avgFutures) {
                System.out.println(f.get()); // blocks until task completes
            }

            // Task 2: Submit Callable tasks for top students
            System.out.println("\n── Submitting top-student tasks ──");
            List<Future<String>> topFutures = new ArrayList<>();
            for (String aid : assessmentIds) {
                topFutures.add(executor.submit(topStudentTask(aid)));
            }

            System.out.println("\n── Top Student Results ──");
            for (Future<String> f : topFutures) {
                System.out.println(f.get());
            }

            // Task 3: invokeAll for grade distributions
            System.out.println("\n── Using invokeAll() for grade distributions ──");
            List<Callable<String>> distTasks = assessmentIds.stream()
                    .map(this::gradeDistributionTask)
                    .toList();

            List<Future<String>> distFutures = executor.invokeAll(distTasks);
            for (Future<String> f : distFutures) {
                System.out.println(f.get());
            }

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("  [!] Concurrency error: " + e.getMessage());
        } finally {
            executor.shutdown();
            System.out.println("\n  ExecutorService shut down.");
        }
    }
}
