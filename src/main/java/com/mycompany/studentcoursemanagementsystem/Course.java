
package com.mycompany.studentcoursemanagementsystem;

/**
 * Course.java
 * Represents a single course a student is enrolled in.
 * Demonstrates: class design, encapsulation, object methods.
 */
public class Course {
    private String courseCode;
    private String courseTitle;
    private int units;

    public Course(String courseCode, String courseTitle, int units) {
        this.courseCode = courseCode.trim().toUpperCase();
        this.courseTitle = courseTitle.trim();
        this.units = units;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public int getUnits() {
        return units;
    }

    public String toFileString() {
        return courseCode + "," + courseTitle + "," + units;
    }

    public static Course fromFileString(String line) throws IllegalArgumentException {
        String[] parts = line.split(",");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Corrupted course record: " + line);
        }
        String code = parts[0].trim();
        String title = parts[1].trim();
        int unit;
        try {
            unit = Integer.parseInt(parts[2].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid unit value in record: " + line);
        }
        return new Course(code, title, unit);
    }

    @Override
    public String toString() {
        return String.format("%-10s | %-35s | %d unit(s)", courseCode, courseTitle, units);
    }
}