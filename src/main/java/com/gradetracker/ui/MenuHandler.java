package com.gradetracker.ui;

import com.gradetracker.model.*;
import com.gradetracker.service.*;
import com.gradetracker.util.*;

import java.util.List;
import java.util.Scanner;

/**
 * Main menu handler for the Student Grade Manager.
 * Provides an interactive console interface to demonstrate all features.
 */
public class MenuHandler {

    private final List<Student>    students;
    private final List<Module>     modules;
    private final List<Assessment> assessments;
    private final List<Result>     results;

    public MenuHandler() {
        this.students    = SampleData.getStudents();
        this.modules     = SampleData.getModules();
        this.assessments = SampleData.getAssessments();
        this.results     = SampleData.getResults();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("\n  Enter choice (0-15): ");
            String input = scanner.nextLine().trim();

            // F6 partial — switch expression on String
            String action = switch (input) {
                case "1"  -> "sorting";
                case "2"  -> "lambdas";
                case "3"  -> "terminal_ops";
                case "4"  -> "collectors";
                case "5"  -> "intermediate_ops";
                case "6"  -> "switch_patterns";
                case "7"  -> "sealed_classes";
                case "8"  -> "datetime";
                case "9"  -> "concurrency";
                case "10" -> "nio2";
                case "11" -> "localisation";
                case "12" -> "jep513";
                case "13" -> "scoped_values";
                case "14" -> "gatherers";
                case "15" -> "run_all";
                case "0"  -> "exit";
                default   -> "invalid";
            };

            switch (action) {
                case "sorting"          -> runSorting();
                case "lambdas"          -> runLambdas();
                case "terminal_ops"     -> runAnalytics();
                case "collectors"       -> runAnalytics();
                case "intermediate_ops" -> runAnalytics();
                case "switch_patterns"  -> runSwitchPatterns();
                case "sealed_classes"   -> runSealedDemo();
                case "datetime"         -> runDateTime();
                case "concurrency"      -> runConcurrency();
                case "nio2"             -> runNIO2();
                case "localisation"     -> runLocalisation();
                case "jep513"           -> TrackerManager.demonstrateJEP513();
                case "scoped_values"    -> TrackerContext.demonstrateScopedValues();
                case "gatherers"        -> AcademicGatherers.demonstrateGatherers();
                case "run_all"          -> runAllDemos();
                case "exit"             -> running = false;
                default -> System.out.println("\n  [!] Invalid choice. Try 0-15.");
            }

            if (running && !action.equals("invalid")) {
                System.out.println("\n  Press Enter to return to menu...");
                scanner.nextLine();
            }
        }
        System.out.println("\n  Goodbye!\n");
        scanner.close();
    }

    private void printMenu() {
        System.out.println("""

        ==========================================
        STUDENT GRADE MANAGER
        OOP2 Feature Showcase
        ==========================================
        FUNDAMENTALS
         1. Sorting (Comparator, thenComparing)
         2. Lambdas (Consumer/Predicate/Supplier/Func)
         3. Streams: Terminal Operations
         4. Streams: Collectors
         5. Streams: Intermediate Operations
         6. Switch Expressions + Pattern Matching
         7. Sealed Classes (Assessment hierarchy)
         8. Date/Time API
        ==========================================
        ADVANCED
         9. Concurrency (ExecutorService + Future)
        10. NIO2 (Files, Paths, walk, copy)
        11. Localisation (ResourceBundle, Locale)
        12. JEP 513: Flexible Constructor Bodies
        ==========================================
        EXTRA CREDIT
        13. Scoped Values (JEP 487)
        14. Stream Gatherers (JEP 485)
        ==========================================
        15. Run ALL demos
         0. Exit
        ==========================================
        """);
    }

    // Individual demo runners

    private void runSorting() {
        GradeReportService svc = new GradeReportService(students, results);
        svc.printLeaderboard();
        svc.printTopScorers("EX001", 5);
    }

    private void runLambdas() {
        StudentLambdaUtils.demonstrateLambdas(students, results);
    }

    private void runAnalytics() {
        AcademicAnalyticsService svc = new AcademicAnalyticsService(students, results, modules);
        svc.demonstrateAll();
    }

    private void runSwitchPatterns() {
        AssessmentProcessor.demonstrateSwitchPatterns(assessments);
    }

    private void runSealedDemo() {
        System.out.println("\n=== F7: Sealed Classes Demo ===\n");
        System.out.println("  Assessment is a sealed interface permitting: Exam, Assignment, LabWork");
        System.out.println("  All three are records implementing the sealed interface.\n");
        System.out.println("  Assessments in the system:");
        assessments.forEach(a -> {
            String detail = switch (a) {
                case Exam e      -> "Exam: %d mins in %s".formatted(e.durationMinutes(), e.venue());
                case Assignment x -> "Assignment: max %.0f marks".formatted(x.maxMarks());
                case LabWork lb  -> "Lab Work: room %s".formatted(lb.labRoom());
            };
            System.out.printf("    [%s] %s — %s — %s%n", a.id(), a.type(), a.moduleCode(), detail);
        });
    }

    private void runDateTime() {
        ExamScheduleService svc = new ExamScheduleService(assessments, students);
        svc.demonstrateDateTimeAPI();
    }

    private void runConcurrency() {
        ResultsProcessingService svc = new ResultsProcessingService(results, students);
        svc.demonstrateConcurrency();
    }

    private void runNIO2() {
        AcademicDataIO svc = new AcademicDataIO(students, results, modules);
        svc.demonstrateNIO2();
    }

    private void runLocalisation() {
        LocaleService svc = new LocaleService();
        svc.demonstrateLocalisation();
    }

    // Run all demos sequentially
    private void runAllDemos() {
        System.out.println("\n  Running ALL feature demos...\n");
        runSorting();
        runLambdas();
        runAnalytics();
        runSwitchPatterns();
        runSealedDemo();
        runDateTime();
        runConcurrency();
        runNIO2();
        runLocalisation();
        TrackerManager.demonstrateJEP513();
        TrackerContext.demonstrateScopedValues();
        AcademicGatherers.demonstrateGatherers();
        System.out.println("\n  All demos completed!");
    }
}
