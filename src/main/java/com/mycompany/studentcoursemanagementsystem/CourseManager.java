
package com.mycompany.studentcoursemanagementsystem;

import java.io.*;
import java.util.ArrayList;

/**
 * CourseManager.java
 * Handles the internal storage and business logic:
 * adding, displaying, searching, totaling, saving, and loading courses.
 */
public class CourseManager {
    private ArrayList<Course> courses;
    private static final String FILE_NAME = "courses.txt";

    public CourseManager() {
        courses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
        System.out.println(">> Course added successfully: " + course.getCourseCode());
    }

    public void displayAllCourses() {
        if (courses.isEmpty()) {
            System.out.println(">> No courses recorded yet.");
            return;
        }
        System.out.println("\n----- REGISTERED COURSES -----");
        for (Course c : courses) {
            System.out.println(c);
        }
        System.out.println("-------------------------------");
    }

    public Course searchCourseByCode(String code) {
        return recursiveSearch(code.trim().toUpperCase(), 0);
    }

    private Course recursiveSearch(String code, int index) {
        if (index >= courses.size()) {
            return null;
        }
        if (courses.get(index).getCourseCode().equals(code)) {
            return courses.get(index);
        }
        return recursiveSearch(code, index + 1);
    }

    public int computeTotalUnits() {
        return recursiveSum(0);
    }

    private int recursiveSum(int index) {
        if (index >= courses.size()) {
            return 0;
        }
        return courses.get(index).getUnits() + recursiveSum(index + 1);
    }

    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Course c : courses) {
                writer.println(c.toFileString());
            }
            System.out.println(">> Courses saved successfully to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println(">> Error saving to file: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println(">> No saved file found (" + FILE_NAME + "). Nothing to load.");
            return;
        }

        ArrayList<Course> loadedCourses = new ArrayList<>();
        int lineNumber = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue;
                try {
                    loadedCourses.add(Course.fromFileString(line));
                } catch (IllegalArgumentException e) {
                    System.out.println(">> Skipping bad record on line " + lineNumber + ": " + e.getMessage());
                }
            }
            courses = loadedCourses;
            System.out.println(">> Courses loaded successfully from " + FILE_NAME);
        } catch (IOException e) {
            System.out.println(">> Error loading file: " + e.getMessage());
        }
    }

    public boolean isEmpty() {
        return courses.isEmpty();
    }

    public int getCourseCount() {
        return courses.size();
    }
}
