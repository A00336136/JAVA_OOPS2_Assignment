package com.gradetracker.service;

import com.gradetracker.model.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Streams demos
 * F3 - Terminal Operations (min, max, count, findAny, etc.)
 * F4 - Collectors (toMap, groupingBy, partitioningBy)
 * F5 - Intermediate Operations (filter, distinct, limit, etc.)
 */
public class AcademicAnalyticsService {

    private final List<Student> students;
    private final List<Result>  results;
    private final List<Module>  modules;

    public AcademicAnalyticsService(List<Student> students,
                                    List<Result> results,
                                    List<Module> modules) {
        this.students = students;
        this.results  = results;
        this.modules  = modules;
    }

    // F3: Terminal Operations

    /** min — lowest score across all results */
    public Optional<Result> lowestResult() {
        return results.stream()
                .min(Comparator.comparingDouble(Result::score));
    }

    /** max — highest score across all results */
    public Optional<Result> highestResult() {
        return results.stream()
                .max(Comparator.comparingDouble(Result::score));
    }

    /** count — number of results for a given assessment */
    public long countResultsFor(String assessmentId) {
        return results.stream()
                .filter(r -> r.assessmentId().equals(assessmentId))
                .count();
    }

    /** findFirst — first student in Software Development */
    public Optional<Student> firstSoftwareDev() {
        return students.stream()
                .filter(s -> s.programme().equals("Software Development"))
                .findFirst();
    }

    /** findAny — any student with GPA above 3.0 */
    public Optional<Student> anyHighGpa() {
        return students.stream()
                .filter(s -> s.gpa() > 3.0)
                .findAny();
    }

    /** allMatch — are ALL results passes (>=40)? */
    public boolean allResultsPass() {
        return results.stream()
                .allMatch(r -> r.score() >= 40);
    }

    /** anyMatch — is there ANY distinction (>=70)? */
    public boolean anyDistinction() {
        return results.stream()
                .anyMatch(r -> r.score() >= 70);
    }

    /** noneMatch — no student has 0.0 GPA? */
    public boolean noneZeroGpa() {
        return students.stream()
                .noneMatch(s -> s.gpa() == 0.0);
    }

    // F4: Collectors

    /** toMap — student ID → Student lookup */
    public Map<String, Student> studentLookup() {
        return students.stream()
                .collect(Collectors.toMap(Student::id, s -> s));
    }

    /** groupingBy — group students by programme */
    public Map<String, List<Student>> studentsByProgramme() {
        return students.stream()
                .collect(Collectors.groupingBy(Student::programme));
    }

    /** groupingBy + counting — count students per programme */
    public Map<String, Long> programmeHeadcount() {
        return students.stream()
                .collect(Collectors.groupingBy(Student::programme, Collectors.counting()));
    }

    /** partitioningBy — partition results into pass / fail */
    public Map<Boolean, List<Result>> partitionPassFail() {
        return results.stream()
                .collect(Collectors.partitioningBy(r -> r.score() >= 40));
    }

    /** groupingBy + averagingDouble — avg score per assessment */
    public Map<String, Double> avgScoreByAssessment() {
        return results.stream()
                .collect(Collectors.groupingBy(
                        Result::assessmentId,
                        Collectors.averagingDouble(Result::score)));
    }

    // F5: Intermediate Operations

    /** filter + map — names of honour-roll students */
    public List<String> honourRollNames() {
        return students.stream()
                .filter(s -> s.gpa() >= 3.5)       // filter
                .map(Student::name)                 // map
                .sorted()                           // sorted
                .toList();
    }

    /** distinct — unique programmes */
    public List<String> uniqueProgrammes() {
        return students.stream()
                .map(Student::programme)
                .distinct()                         // distinct
                .sorted()
                .toList();
    }

    /** limit — top 3 students by GPA */
    public List<Student> top3ByGpa() {
        return students.stream()
                .sorted(Comparator.comparingDouble(Student::gpa).reversed())
                .limit(3)                           // limit
                .toList();
    }

    /** filter + sorted + map — assessment IDs with fails, sorted */
    public List<String> assessmentsWithFails() {
        return results.stream()
                .filter(r -> r.score() < 40)
                .map(Result::assessmentId)
                .distinct()
                .sorted()
                .toList();
    }

    // Demo method - showcases all three features
    public void demonstrateAll() {
        System.out.println("\n=== F3: Stream Terminal Operations Demo ===\n");

        lowestResult().ifPresent(r ->
                System.out.printf("  min  → Lowest score:  %s on %s = %.1f%n",
                        r.studentId(), r.assessmentId(), r.score()));
        highestResult().ifPresent(r ->
                System.out.printf("  max  → Highest score: %s on %s = %.1f%n",
                        r.studentId(), r.assessmentId(), r.score()));
        System.out.printf("  count → Results for EX001: %d%n", countResultsFor("EX001"));
        firstSoftwareDev().ifPresent(s ->
                System.out.printf("  findFirst → First SWD student: %s%n", s.name()));
        anyHighGpa().ifPresent(s ->
                System.out.printf("  findAny → A student with GPA > 3.0: %s (%.2f)%n",
                        s.name(), s.gpa()));
        System.out.printf("  allMatch  → All results pass?      %b%n", allResultsPass());
        System.out.printf("  anyMatch  → Any distinction?        %b%n", anyDistinction());
        System.out.printf("  noneMatch → No student with 0 GPA?  %b%n", noneZeroGpa());

        System.out.println("\n=== F4: Collectors Demo ===\n");

        System.out.println("  toMap — Student lookup:");
        studentLookup().forEach((id, s) ->
                System.out.printf("    %s → %s%n", id, s.name()));

        System.out.println("\n  groupingBy — Students per programme:");
        studentsByProgramme().forEach((prog, list) -> {
            System.out.printf("    %s:%n", prog);
            list.forEach(s -> System.out.printf("      - %s%n", s.name()));
        });

        System.out.println("\n  groupingBy + counting — Headcount:");
        programmeHeadcount().forEach((prog, count) ->
                System.out.printf("    %s: %d students%n", prog, count));

        System.out.println("\n  partitioningBy — Pass/Fail split:");
        Map<Boolean, List<Result>> pf = partitionPassFail();
        System.out.printf("    Passes: %d | Fails: %d%n",
                pf.get(true).size(), pf.get(false).size());

        System.out.println("\n  groupingBy + averagingDouble — Avg score per assessment:");
        avgScoreByAssessment().forEach((aid, avg) ->
                System.out.printf("    %s: %.1f%n", aid, avg));

        System.out.println("\n=== F5: Stream Intermediate Operations Demo ===\n");

        System.out.println("  filter + map + sorted — Honour roll names:");
        honourRollNames().forEach(n -> System.out.println("    * " + n));

        System.out.println("\n  distinct — Unique programmes:");
        uniqueProgrammes().forEach(p -> System.out.println("    • " + p));

        System.out.println("\n  limit — Top 3 by GPA:");
        top3ByGpa().forEach(s ->
                System.out.printf("    %s — %.2f%n", s.name(), s.gpa()));

        System.out.println("\n  filter + distinct + sorted — Assessments with fails:");
        assessmentsWithFails().forEach(a -> System.out.println("    [!] " + a));
    }
}
