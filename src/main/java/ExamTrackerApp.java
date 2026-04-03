/**
 * Student Grade Manager - Main entry point
 * Uses JEP 512 for compact source files
 * Author: Absar Ahammad Shaik
 * Student ID: A00336136
 */

import com.gradetracker.ui.MenuHandler;

// Instance main method — no 'static', no 'String[] args'
void main() {
    System.out.println("""
        ==========================================
        STUDENT GRADE MANAGER
        ==========================================
        OOP2 Assignment - Feature Showcase
        TUS - Technological University of the Shannon

        Student: Absar Ahammad Shaik
        ID: A00336136
        Module: Object Oriented Programming 2

        ==========================================
        """);

    System.out.println("Starting application...\n");

    MenuHandler menu = new MenuHandler();
    menu.run();
}
