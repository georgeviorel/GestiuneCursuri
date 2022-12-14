package com.itfactory.data;

import com.itfactory.model.Course;
import com.itfactory.model.Student;
import com.itfactory.utils.DataLoaderUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataLoader {
    private Map<Course, List<Student>> dataMap;
    private List<Student> students;
    private List<Course> courses;

    public DataLoader() {
        dataMap = new HashMap<>();
        students = new ArrayList<>();
        courses = new ArrayList<>();
    }

    public void loadData() throws IOException {
        loadCourses();
        loadStudents();
        mapStudentsToCourses();
    }

    private void loadCourses() throws IOException {
        List<String> courseInputData = DataLoaderUtils.readFile(DataLoaderUtils.COURSE_FILE_PATH);
        for (String courseInputDatum : courseInputData) {
            String[] data = courseInputDatum.split(",");
            courses.add(createCourse(data));
            dataMap.put(createCourse(data), new ArrayList<>());
        }
    }
    private Course createCourse(String[] data) {
        int courseId = Integer.parseInt(data[0]);
        String courseName = data[1];
        double price = Double.parseDouble(data[2]);
        LocalDate startDate = LocalDate.parse(data[3]);
        return new Course(courseId,courseName,price,startDate);
    }

    private void loadStudents() throws IOException {
        List<String> studentInputData = DataLoaderUtils.readFile(DataLoaderUtils.STUDENT_FILE_PATH);
        for (String studentInputDatum : studentInputData) {
            String[] data = studentInputDatum.split(",");
            students.add(createStudent(data));
        }
    }

    private Student createStudent(String[] data) {
        int id = Integer.parseInt(data[0]);
        String studentName = data[1];
        double budget = Double.parseDouble(data[2]);
        return new Student(id, studentName, budget);
    }

    private void mapStudentsToCourses() throws IOException {
        List<String> mapData = DataLoaderUtils.readFile(DataLoaderUtils.MAPPING_FILE_PATH);
        for (String mapDatum: mapData) {
            String[] data = mapDatum.split(",");
            for (Course course : dataMap.keySet()) {
                if (course.getCourseId() == Integer.parseInt(data[1])) {
                    List<Student> studentListTemp;
                    studentListTemp = dataMap.get(course);
                    for (Student student : students) {
                        if (student.getStudentId() == Integer.parseInt(data[0])) {
                            studentListTemp.add(student);
                            dataMap.put(course,studentListTemp);
                        }
                    }
                }
            }
        }
    }

    public Map<Course, List<Student>> getDataMap() {
        return dataMap;
    }

    public List<Student> getStudents() {
        return students;
    }


    public List<Course> getCourses() {
        return courses;
    }

}
