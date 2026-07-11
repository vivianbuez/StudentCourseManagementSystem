package com.mycompany.studentcoursemanagementsystem;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * StudentCourseManagementSystem.java
 * Main class: runs the console menu (recursively) and directs user choices
 * to the appropriate CourseManager methods.
 */
public class StudentCourseManagementSystem {

    private static Scanner scanner = new Scanner(System.in);
    private static CourseManager manager = new CourseManager();

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println(" WELCOME TO THE STUDENT COURSE MANAGEMENT SYSTEM ");
        System.out.println("=================================================");
        runMenu();
        scanner.close();
    }

    private static void runMenu() {
        printMenu();
        int choice = readMenuChoice();

        // Prevent duplicate error messages for non-numeric input
        if (choice == -1) {
            runMenu();
            return;
        }

        switch (choice) {
            case 1:
                handleAddCourse();
                break;
            case 2:
                manager.displayAllCourses();
                break;
            case 3:
                handleSearchCourse();
                break;
            case 4:
                handleComputeTotalUnits();
                break;
            case 5:
                manager.saveToFile();
                break;
            case 6:
                manager.loadFromFile();
                break;
            case 7:
                System.out.println("\nThank you for using the Student Course Management System. Goodbye!");
                return;
            default:
                System.out.println(">> Invalid option. Please choose a number between 1 and 7.");
        }

        runMenu();
    }

    private static void printMenu() {
        System.out.println("\n----------------- MENU -----------------");
        System.out.println("1. Add Course");
        System.out.println("2. View All Courses");
        System.out.println("3. Search Course by Code");
        System.out.println("4. Compute Total Units");
        System.out.println("5. Save to File");
        System.out.println("6. Load from File");
        System.out.println("7. Exit Program");
        System.out.println("-----------------------------------------");
        System.out.print("Enter your choice (1-7): ");
    }

    private static int readMenuChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            return choice;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println(">> Invalid input. Please enter a number.");
            return -1;
        }
    }

    private static void handleAddCourse() {
        try {
            System.out.print("Enter course code (e.g., COS201): ");
            String code = scanner.nextLine();
            if (code.trim().isEmpty()) {
                throw new IllegalArgumentException("Course code cannot be empty.");
            }

            System.out.print("Enter course title: ");
            String title = scanner.nextLine();
            if (title.trim().isEmpty()) {
                throw new IllegalArgumentException("Course title cannot be empty.");
            }

            System.out.print("Enter number of units: ");
            int units = Integer.parseInt(scanner.nextLine().trim());
            if (units <= 0) {
                throw new IllegalArgumentException("Units must be a positive number.");
            }

            manager.addCourse(new Course(code, title, units));

        } catch (NumberFormatException e) {
            System.out.println(">> Error: Units must be a valid whole number.");
        } catch (IllegalArgumentException e) {
            System.out.println(">> Error: " + e.getMessage());
        }
    }

    private static void handleSearchCourse() {
        if (manager.isEmpty()) {
            System.out.println(">> No courses to search. Please add a course first.");
            return;
        }

        System.out.print("Enter the course code to search for: ");
        String code = scanner.nextLine();
        Course found = manager.searchCourseByCode(code);

        if (found != null) {
            System.out.println(">> Course found:");
            System.out.println(found);
        } else {
            System.out.println(">> No course found with code: " + code.trim().toUpperCase());
        }
    }

    private static void handleComputeTotalUnits() {
        if (manager.isEmpty()) {
            System.out.println(">> No courses recorded yet. Total units: 0");
            return;
        }

        int total = manager.computeTotalUnits();
        System.out.println(">> You have " + manager.getCourseCount() + " course(s) recorded.");
        System.out.println(">> Total units: " + total);
    }
}