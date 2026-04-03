package com.gradetracker.service;

import com.gradetracker.model.*;

import java.util.List;
import java.util.Objects;

/**
 * JEP 513: Flexible Constructor Bodies demo
 * Demonstrates statements BEFORE super() in constructors
 */
public class TrackerManager extends BaseTracker {

    private final List<Student> students;
    private final String        department;

    /**
     * JEP 513 - Flexible Constructor Body
     * Validates arguments BEFORE calling super()
     */
    public TrackerManager(String name, List<Student> students, String department) {
        // Statements BEFORE super() - this is JEP 513!
        Objects.requireNonNull(name, "Tracker name must not be null");
        Objects.requireNonNull(students, "Student list must not be null");

        // Validate and compute a derived value before super()
        int capacity = students.size();
        if (capacity == 0) {
            throw new IllegalArgumentException("Student list must not be empty");
        }

        // Log before super — previously impossible!
        System.out.printf("  [JEP 513] Pre-super validation passed: name='%s', students=%d%n",
                name, capacity);

        // Now call super with the computed value
        super(name, capacity);

        // Post-super field assignments
        this.students   = students;
        this.department  = department;
    }

    public List<Student> getStudents() { return students; }
    public String getDepartment() { return department; }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.printf("  Department: %s | Students loaded: %d%n",
                department, students.size());
    }

    // Demo
    public static void demonstrateJEP513() {
        System.out.println("\n=== JEP 513 - Flexible Constructor Bodies ===\n");

        System.out.println("  Creating TrackerManager with pre-super() validation...\n");

        List<Student> students = SampleData.getStudents();
        TrackerManager mgr = new TrackerManager(
                "Grade Tracker v2", students, "Computer Science");

        System.out.println();
        mgr.displayInfo();

        System.out.println("\n  Testing null-name validation...");
        try {
            new TrackerManager(null, students, "CS");
        } catch (NullPointerException e) {
            System.out.println("  [OK] Caught expected error: " + e.getMessage());
        }

        System.out.println("\n  Testing empty-list validation...");
        try {
            new TrackerManager("Empty Tracker", List.of(), "CS");
        } catch (IllegalArgumentException e) {
            System.out.println("  [OK] Caught expected error: " + e.getMessage());
        }
    }
}
