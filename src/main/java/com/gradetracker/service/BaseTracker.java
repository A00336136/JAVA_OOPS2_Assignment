package com.gradetracker.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A5 — JEP 513: Flexible Constructor Bodies
 * Base class that demonstrates pre-super() statements in constructors.
 * The subclass TrackerManager performs validation and logging BEFORE
 * calling super(), which was not possible before JEP 513.
 */
public class BaseTracker {

    private final String trackerName;
    private final LocalDateTime createdAt;
    private final int maxCapacity;

    public BaseTracker(String trackerName, int maxCapacity) {
        this.trackerName  = trackerName;
        this.createdAt    = LocalDateTime.now();
        this.maxCapacity  = maxCapacity;
        System.out.printf("  [BaseTracker] Initialised '%s' (capacity=%d) at %s%n",
                trackerName, maxCapacity,
                createdAt.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    public String getTrackerName() { return trackerName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public int getMaxCapacity() { return maxCapacity; }

    public void displayInfo() {
        System.out.printf("  Tracker: %s | Capacity: %d | Created: %s%n",
                trackerName, maxCapacity,
                createdAt.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")));
    }
}
