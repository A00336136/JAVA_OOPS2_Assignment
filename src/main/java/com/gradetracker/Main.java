package com.gradetracker;

import com.gradetracker.ui.MenuHandler;

/**
 * Main class for Student Grade Manager
 * Author: Absar Ahammad Shaik
 * Student ID: A00336136
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("STUDENT GRADE MANAGER");
        System.out.println("==========================================");
        System.out.println("OOP2 Assignment");
        System.out.println("Student: Absar Ahammad Shaik");
        System.out.println("ID: A00336136");
        System.out.println("==========================================\n");

        MenuHandler menu = new MenuHandler();
        menu.run();
    }
}
