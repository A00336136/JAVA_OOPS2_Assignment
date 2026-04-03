package com.gradetracker.service;

import com.gradetracker.model.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * NIO2 demo (Files, Paths, walk, copy)
 * Modern Java file I/O operations for academic data
 */
public class AcademicDataIO {

    private static final String DATA_DIR = "student_data";

    private final List<Student> students;
    private final List<Result>  results;
    private final List<Module>  modules;

    public AcademicDataIO(List<Student> students, List<Result> results, List<Module> modules) {
        this.students = students;
        this.results  = results;
        this.modules  = modules;
    }

    // Create directory structure
    public Path setupDirectories() throws IOException {
        Path base = Paths.get(DATA_DIR);
        Files.createDirectories(base.resolve("reports"));
        Files.createDirectories(base.resolve("exports"));
        Files.createDirectories(base.resolve("backups"));
        System.out.println("  Created directory tree under: " + base.toAbsolutePath());
        return base;
    }

    // Export students to CSV using Files.write
    public Path exportStudentsCsv(Path base) throws IOException {
        Path csvPath = base.resolve("exports").resolve("students.csv");
        List<String> lines = new java.util.ArrayList<>();
        lines.add("ID,Name,Programme,EnrolDate,GPA");   // header
        students.forEach(s -> lines.add(
                "%s,%s,%s,%s,%.2f".formatted(
                        s.id(), s.name(), s.programme(), s.enrollDate(), s.gpa())));
        Files.write(csvPath, lines);
        System.out.println("  Wrote students CSV → " + csvPath);
        return csvPath;
    }

    // Export results to CSV
    public Path exportResultsCsv(Path base) throws IOException {
        Path csvPath = base.resolve("exports").resolve("results.csv");
        List<String> lines = new java.util.ArrayList<>();
        lines.add("StudentID,AssessmentID,Score,SubmittedAt");
        results.forEach(r -> lines.add(
                "%s,%s,%.1f,%s".formatted(
                        r.studentId(), r.assessmentId(), r.score(), r.submittedAt())));
        Files.write(csvPath, lines);
        System.out.println("  Wrote results CSV → " + csvPath);
        return csvPath;
    }

    // Read back CSV using Files.readAllLines
    public void readAndDisplay(Path csvPath) throws IOException {
        System.out.println("\n  Reading back: " + csvPath.getFileName());
        List<String> lines = Files.readAllLines(csvPath);
        lines.forEach(line -> System.out.println("    " + line));
    }

    // Copy file (backup) using Files.copy
    public void backupFile(Path source, Path base) throws IOException {
        Path backup = base.resolve("backups").resolve("backup_" + source.getFileName());
        Files.copy(source, backup, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("  Backed up → " + backup);
    }

    // Walk the directory tree using Files.walk
    public void walkDataDirectory(Path base) throws IOException {
        System.out.println("\n  Directory tree (Files.walk):");
        try (Stream<Path> paths = Files.walk(base)) {
            paths.forEach(p -> {
                int depth = base.relativize(p).getNameCount();
                String indent = "  " + "  ".repeat(depth);
                String icon = Files.isDirectory(p) ? "[DIR]" : "[FILE]";
                System.out.println(indent + icon + " " + p.getFileName());
            });
        }
    }

    // Generate a text report
    public void generateReport(Path base) throws IOException {
        Path report = base.resolve("reports").resolve("summary_report.txt");
        List<String> content = List.of(
                "=====================================",
                "ACADEMIC SUMMARY REPORT",
                "=====================================",
                "",
                "Total students: " + students.size(),
                "Total results:  " + results.size(),
                "Total modules:  " + modules.size(),
                "",
                "── Students ──",
                students.stream()
                        .map(s -> "  %s %-20s GPA: %.2f".formatted(s.id(), s.name(), s.gpa()))
                        .collect(Collectors.joining("\n")),
                "",
                "── Average score by assessment ──",
                results.stream()
                        .collect(Collectors.groupingBy(
                                Result::assessmentId,
                                Collectors.averagingDouble(Result::score)))
                        .entrySet().stream()
                        .map(e -> "  %s: %.1f".formatted(e.getKey(), e.getValue()))
                        .collect(Collectors.joining("\n")),
                "",
                "Report generated: " + java.time.LocalDateTime.now()
        );
        Files.write(report, content);
        System.out.println("  Generated report → " + report);
    }

    // Full demo
    public void demonstrateNIO2() {
        System.out.println("\n=== NIO2 Demo ===\n");
        try {
            Path base = setupDirectories();
            Path studentCsv = exportStudentsCsv(base);
            Path resultCsv  = exportResultsCsv(base);

            readAndDisplay(studentCsv);
            readAndDisplay(resultCsv);

            backupFile(studentCsv, base);
            backupFile(resultCsv, base);

            generateReport(base);
            walkDataDirectory(base);

            System.out.println("\n  NIO2 demo complete.");
        } catch (IOException e) {
            System.err.println("  [!] NIO2 error: " + e.getMessage());
        }
    }
}
