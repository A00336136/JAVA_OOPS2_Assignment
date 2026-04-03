package com.gradetracker.service;

import com.gradetracker.model.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Date/Time API demo
 * LocalDate, LocalTime, LocalDateTime, Period, Duration, etc.
 */
public class ExamScheduleService {

    private final List<Assessment> assessments;
    private final List<Student>    students;

    private static final DateTimeFormatter FULL_DATE =
            DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");
    private static final DateTimeFormatter SHORT_DATE =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FMT =
            DateTimeFormatter.ofPattern("HH:mm");

    public ExamScheduleService(List<Assessment> assessments, List<Student> students) {
        this.assessments = assessments;
        this.students    = students;
    }

    // Days until each assessment
    public void printDaysUntil() {
        LocalDate today = LocalDate.now();
        System.out.println("\n── Days until each assessment ──");
        assessments.forEach(a -> {
            long days = ChronoUnit.DAYS.between(today, a.date());
            String label = days > 0 ? days + " days away"
                         : days == 0 ? "TODAY"
                         : Math.abs(days) + " days ago";
            System.out.printf("  %s (%s) — %s → %s%n",
                    a.id(), a.type(), a.date().format(SHORT_DATE), label);
        });
    }

    // Enrolment duration for each student
    public void printEnrolmentDurations() {
        LocalDate today = LocalDate.now();
        System.out.println("\n── Student enrolment periods ──");
        students.forEach(s -> {
            Period period = Period.between(s.enrollDate(), today);
            System.out.printf("  %s enrolled %s → %d year(s), %d month(s), %d day(s)%n",
                    s.name(), s.enrollDate().format(SHORT_DATE),
                    period.getYears(), period.getMonths(), period.getDays());
        });
    }

    // Exam timetable with end-time calculation
    public void printExamTimetable() {
        System.out.println("\n── Exam Timetable ──");
        System.out.printf("  %-8s %-28s %-8s %-8s %-10s%n",
                "ID", "Date", "Start", "End", "Venue");
        System.out.println("  " + "─".repeat(66));
        assessments.stream()
                .filter(a -> a instanceof Exam)
                .map(a -> (Exam) a)
                .forEach(e -> {
                    LocalTime end = e.startTime()
                            .plus(Duration.ofMinutes(e.durationMinutes()));
                    System.out.printf("  %-8s %-28s %-8s %-8s %-10s%n",
                            e.id(),
                            e.date().format(FULL_DATE),
                            e.startTime().format(TIME_FMT),
                            end.format(TIME_FMT),
                            e.venue());
                });
    }

    // Upcoming this week
    public void printUpcomingThisWeek() {
        LocalDate today = LocalDate.now();
        LocalDate endOfWeek = today.plusDays(7);
        System.out.println("\n── Assessments in the next 7 days ──");
        assessments.stream()
                .filter(a -> !a.date().isBefore(today) && a.date().isBefore(endOfWeek))
                .forEach(a -> System.out.printf("  %s %s — %s%n",
                        a.id(), a.type(), a.date().format(FULL_DATE)));
        long count = assessments.stream()
                .filter(a -> !a.date().isBefore(today) && a.date().isBefore(endOfWeek))
                .count();
        if (count == 0) System.out.println("  (none)");
    }

    // Full demo
    public void demonstrateDateTimeAPI() {
        System.out.println("\n=== F8: Date/Time API Demo ===");

        // Show current date/time
        LocalDateTime now = LocalDateTime.now();
        System.out.printf("%n  Current date/time: %s%n",
                now.format(DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy, HH:mm:ss")));

        printDaysUntil();
        printEnrolmentDurations();
        printExamTimetable();
        printUpcomingThisWeek();

        // Demonstrate parsing a date string
        String dateStr = "25/04/2026";
        LocalDate parsed = LocalDate.parse(dateStr, SHORT_DATE);
        System.out.printf("%n  Parsed \"%s\" → %s%n", dateStr, parsed.format(FULL_DATE));
    }
}
