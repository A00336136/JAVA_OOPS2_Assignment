package com.gradetracker.util;

import com.gradetracker.model.*;

import java.util.List;

/**
 * Scoped Values demo (JEP 487)
 * Shows how to use ScopedValue for context sharing
 */
public class TrackerContext {

    // Scoped value to hold the current user (student) context
    public static final ScopedValue<Student> CURRENT_USER = ScopedValue.newInstance();

    // Scoped value to hold the active locale name
    public static final ScopedValue<String> ACTIVE_LOCALE = ScopedValue.newInstance();

    // Method that reads from scoped value
    private static void printCurrentContext() {
        if (CURRENT_USER.isBound()) {
            Student user = CURRENT_USER.get();
            System.out.printf("    Context → User: %s (%s), Programme: %s%n",
                    user.name(), user.id(), user.programme());
        } else {
            System.out.println("    Context → No user bound");
        }

        if (ACTIVE_LOCALE.isBound()) {
            System.out.printf("    Context → Locale: %s%n", ACTIVE_LOCALE.get());
        }
    }

    // Nested call that also reads scoped value
    private static void generateUserReport() {
        System.out.println("    [generateUserReport] Reading scoped context...");
        printCurrentContext();
        // The scoped value is accessible here without passing parameters
        if (CURRENT_USER.isBound()) {
            Student user = CURRENT_USER.get();
            System.out.printf("    [generateUserReport] GPA for %s: %.2f — %s%n",
                    user.name(), user.gpa(),
                    user.gpa() >= 3.5 ? "Honour Roll" : "Standard");
        }
    }

    // Demo
    public static void demonstrateScopedValues() {
        System.out.println("\n=== Scoped Values (JEP 487) ===\n");

        List<Student> students = SampleData.getStudents();

        // Show that the scoped value is unbound initially
        System.out.println("  Before binding:");
        System.out.println("    CURRENT_USER bound? " + CURRENT_USER.isBound());

        // Run with different students bound as context
        for (int i = 0; i < Math.min(3, students.size()); i++) {
            Student student = students.get(i);
            System.out.printf("%n  ── Running with scoped context for %s ──%n", student.name());

            ScopedValue.where(CURRENT_USER, student)
                    .where(ACTIVE_LOCALE, "en-IE")
                    .run(() -> {
                        printCurrentContext();
                        generateUserReport();
                    });
        }

        // Show the scoped value is unbound again
        System.out.println("\n  After all scopes exited:");
        System.out.println("    CURRENT_USER bound? " + CURRENT_USER.isBound());
        System.out.println("    (Automatically cleaned up — safer than ThreadLocal!)");
    }
}
