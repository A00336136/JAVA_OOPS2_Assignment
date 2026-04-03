package com.gradetracker.util;

import com.gradetracker.model.*;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Lambda / Functional Interfaces demo
 * Consumer, Predicate, Supplier, Function examples
 */
public class StudentLambdaUtils {

    // Consumer: prints student info card
    public static final Consumer<Student> PRINT_STUDENT_CARD = student ->
            System.out.printf("  [%s] %-20s | %-22s | GPA %.2f%n",
                    student.id(), student.name(), student.programme(), student.gpa());

    // Consumer: prints result with grade
    public static final Consumer<Result> PRINT_RESULT = result ->
            System.out.printf("  Student %s → Assessment %s : %.1f (%s)%n",
                    result.studentId(), result.assessmentId(),
                    result.score(), Grade.fromScore(result.score()).getLabel());

    // Predicate: student is on honour roll (GPA >= 3.5)
    public static final Predicate<Student> IS_HONOUR_ROLL =
            student -> student.gpa() >= 3.5;

    // Predicate: student is on academic probation (GPA < 2.0)
    public static final Predicate<Student> IS_PROBATION =
            student -> student.gpa() < 2.0;

    // Predicate: result is a pass (score >= 40)
    public static final Predicate<Result> IS_PASS =
            result -> result.score() >= 40.0;

    // Predicate: result is distinction (score >= 70)
    public static final Predicate<Result> IS_DISTINCTION =
            result -> result.score() >= 70.0;

    // Supplier: generates a default "unknown" student
    public static final Supplier<Student> DEFAULT_STUDENT =
            () -> new Student("STU000", "Unknown Student", "Undeclared",
                    java.time.LocalDate.now(), 0.0);

    // Function: extract display string from Student
    public static final Function<Student, String> TO_DISPLAY_NAME =
            student -> student.name() + " (" + student.id() + ")";

    // Function: score to Grade conversion
    public static final Function<Double, Grade> SCORE_TO_GRADE =
            Grade::fromScore;

    // Function: student to programme category
    public static final Function<Student, String> PROGRAMME_TAG =
            student -> switch (student.programme()) {
                case "Software Development" -> "[SWD] Software Development";
                case "Data Science"         -> "[DS] Data Science";
                case "Cyber Security"       -> "[SEC] Cyber Security";
                default                     -> "[GEN] General";
            };

    // Demo method showing all four functional interfaces
    public static void demonstrateLambdas(List<Student> students, List<Result> results) {
        System.out.println("\n=== Lambda / Functional Interface Demo ===\n");

        // Consumer demo
        System.out.println("── Consumer<Student> — printing student cards ──");
        students.forEach(PRINT_STUDENT_CARD);

        // Predicate demo
        System.out.println("\n── Predicate<Student> — honour roll filter ──");
        students.stream()
                .filter(IS_HONOUR_ROLL)
                .forEach(s -> System.out.printf("  * %s (GPA %.2f)%n", s.name(), s.gpa()));

        System.out.println("\n── Predicate<Student> — academic probation ──");
        students.stream()
                .filter(IS_PROBATION)
                .forEach(s -> System.out.printf("  [!] %s (GPA %.2f)%n", s.name(), s.gpa()));

        // Supplier demo
        System.out.println("\n── Supplier<Student> — default student ──");
        Student fallback = DEFAULT_STUDENT.get();
        System.out.println("  Generated: " + TO_DISPLAY_NAME.apply(fallback));

        // Function demo
        System.out.println("\n── Function<Student, String> — display names ──");
        students.stream()
                .map(TO_DISPLAY_NAME)
                .forEach(name -> System.out.println("  → " + name));

        System.out.println("\n── Function<Student, String> — programme tags ──");
        students.stream()
                .map(s -> PROGRAMME_TAG.apply(s) + " " + s.name())
                .forEach(tag -> System.out.println("  " + tag));

        // Composed predicates
        System.out.println("\n── Predicate composition (negate, and, or) ──");
        Predicate<Student> notHonour = IS_HONOUR_ROLL.negate();
        Predicate<Student> probationOrHonour = IS_PROBATION.or(IS_HONOUR_ROLL);
        System.out.println("  Students NOT on honour roll:");
        students.stream().filter(notHonour)
                .forEach(s -> System.out.printf("    %s (%.2f)%n", s.name(), s.gpa()));
        System.out.println("  Students on probation OR honour roll:");
        students.stream().filter(probationOrHonour)
                .forEach(s -> System.out.printf("    %s (%.2f)%n", s.name(), s.gpa()));

        // Consumer for results
        System.out.println("\n── Consumer<Result> + Predicate<Result> — distinctions ──");
        results.stream()
                .filter(IS_DISTINCTION)
                .forEach(PRINT_RESULT);
    }
}
