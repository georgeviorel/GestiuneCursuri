package com.itfactory.model;

import java.time.LocalDate;

public class Course {
    private int courseId;
    private String courseName;
    private double price;
    private LocalDate startDate;

    public Course (int courseId, String courseName, double price, LocalDate startDate) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.price = price;
        this.startDate = startDate;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    @Override
    public String toString() {
        return " (id: " + courseId + ")  |  " + courseName +
                "  |  Pret: " + price +
                "  |  Data de start: " + startDate;
    }
}
