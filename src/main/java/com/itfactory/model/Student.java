package com.itfactory.model;

public class Student {
    private int studentId;
    private String studentName;
    private double budget;

    public Student(int studentId, String studentName, double budget) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.budget = budget;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public double getBudget() {
        return budget;
    }

    @Override
    public String toString() {
        return "\n" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", budget=" + budget;
    }
}
