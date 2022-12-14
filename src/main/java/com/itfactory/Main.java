package com.itfactory;

import com.itfactory.data.DataLoader;
import com.itfactory.model.Course;
import com.itfactory.model.Student;
import com.itfactory.utils.DataLoaderUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {


        while (true) {
            System.out.println("\n\t\t\tMENIUL PRINCIPAL" +
                    "\n\t1 - Afiseaza cursuri" +
                    "\n\t2 - Introduceti un curs nou" +
                    "\n\t3 - Introduceti un student nou si inrolati-l la un curs" +
                    "\n\t4 - Cautati un student dupa nume (optional)" +
                    "\n\t5 - Afiseaza studentii si cursul la care participa (optional)" +
                    "\n\t0 - Iesire din program");
            System.out.print("\nIntroduceti o optiune:");

            Scanner scanner = new Scanner(System.in);
            int option = Integer.parseInt(scanner.nextLine());
            System.out.println("\n --------------------------------------------------------------------- ");
            switch (option) {
                case 0: {
                    System.out.println("\nProgramul se va inchide! La revedere!");
                    System.out.println("\n --------------------------------------------------------------------- ");
                    System.exit(0);
                }
                break;
                case 1: {
                    System.out.println("\nCursuri disponibile\n");
                    showCourses();
                    pressEnterKey();
                }
                break;
                case 2: {
                    System.out.println("\nIntroducere curs nou\n");
                    addCourse();
                    pressEnterKey();
                }
                break;
                case 3: {
                    System.out.println("\nIntroducere student nou\n");
                    addStudent();
                    pressEnterKey();
                }
                break;
                case 4: {
                    System.out.println("\nCautam un student dupa nume\n");
                    searchStudentByName();
                    pressEnterKey();
                }
                break;
                case 5: {
                    System.out.println("\nAfisam studentii si cursul la care participa\n");
                    showStudents();
                    pressEnterKey();
                }
                break;
                default: {
                    System.out.println("\nOptiune invalida\n");
                    pressEnterKey();
                }
            }
            System.out.println("\n --------------------------------------------------------------------- ");
        }

    }

    public static void showCourses() throws IOException {
        DataLoader dataLoader = new DataLoader();
        dataLoader.loadData();
        for (int i = 0; i< dataLoader.getCourses().size(); i++) {
            System.out.println(dataLoader.getCourses().get(i));
        }
    }

    public static void addCourse() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nume curs: ");
        String numeCurs = scanner.nextLine();
        System.out.print("Pret: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Data inceperii (yyyy-MM-dd) : ");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());
        //scanner.close();
        DataLoader dataLoader = new DataLoader();
        dataLoader.loadData();
        int lastCourseId = 0;
        for (Course course : dataLoader.getCourses()) {
            if (course.getCourseId() > lastCourseId) {
                lastCourseId = course.getCourseId();
            }
        }
        int courseId = ++lastCourseId;
        String addCourseLine = courseId + "," + numeCurs + ","  + price + "," + startDate + "\r\n";
        Path path = Paths.get(DataLoaderUtils.COURSE_FILE_PATH);
        Files.write(path, addCourseLine.getBytes(), StandardOpenOption.APPEND);
        System.out.println("\nCursul a fost adaugat cu succes!");
    }

    public static void addStudent() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nume: ");
        String studentName = scanner.nextLine();
        System.out.print("Buget: ");
        double budget = Double.parseDouble(scanner.nextLine());
        DataLoader dataLoader = new DataLoader();
        dataLoader.loadData();
        int lastStudentId = 0;
        for (Student student: dataLoader.getStudents()) {
            if (student.getStudentId() > lastStudentId) {
                lastStudentId = student.getStudentId();
            }
        }
        int studentId = ++lastStudentId;
        String addStudentLine = studentId + "," + studentName + "," + budget + "\r\n";
        Path path = Paths.get(DataLoaderUtils.STUDENT_FILE_PATH);
        Files.write(path, addStudentLine.getBytes(), StandardOpenOption.APPEND);
        System.out.println("Studentul a fost adaugat cu succes!");
        System.out.print("\nInscrieti acest student la unul dintre cursuri ? ( da / nu ): ");
        if ((scanner.nextLine().equals("da"))) {
            System.out.println("\nCursuri disponibile: ");
            showCourses();
            System.out.print("\nId curs: ");
            int courseId = Integer.parseInt(scanner.nextLine());

            String courseName = "";
            String mapOk = "";

            for (Course course : dataLoader.getCourses()) {
                if (course.getCourseId()==courseId) {
                    courseName = course.getCourseName();
                    if (budget >= course.getPrice()) {
                        String addMapLine = studentId + "," + courseId + "\r\n";
                        path = Paths.get(DataLoaderUtils.MAPPING_FILE_PATH);
                        Files.write(path, addMapLine.getBytes(), StandardOpenOption.APPEND);
                        mapOk = "Studentul " + studentName + " a fost inscris cu succes la cursul " + courseName;
                    }
                    else {
                        mapOk = "Bugetul studentului nu acopera costurile cursului. Inscrierea nu a fost efectuata!";
                    }
                } else {
                    mapOk = "Ati introdus un id de curs invalid, studentul nu a fost inscris.";
                }
            }
            System.out.println(mapOk);

        }
    }

    public static void showStudents() throws IOException {
        DataLoader dataLoader = new DataLoader();
        dataLoader.loadData();
        for (Student student : dataLoader.getStudents()) {
            System.out.println("( id:" + student.getStudentId() + " )  " + student.getStudentName() + " ");
            String showCourseName = "";
            for (Map.Entry<Course, List<Student>> entry : dataLoader.getDataMap().entrySet()) {
                for (Student studentEnroled : entry.getValue()) {
                    if (student.getStudentId() == studentEnroled.getStudentId()) {
                        showCourseName += " - inscris la " + entry.getKey().getCourseName() + "\n";
                    }
                }
            }
            System.out.println(showCourseName.equals("")? " - nu este inscris la nici un curs.\n" : showCourseName);
        }
    }

    public static void searchStudentByName() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduceti nume student: ");
        String studentName = scanner.nextLine();
        DataLoader dataLoader = new DataLoader();
        dataLoader.loadData();
        int results = 0;
        List<Integer> resultsIds = new ArrayList<>();
        for (Student student : dataLoader.getStudents()) {
            if (student.getStudentName().toLowerCase().contains(studentName.toLowerCase())) {
                results++;
                resultsIds.add(student.getStudentId());
            }
        }
        if (results != 0) {
            System.out.println("\nS-au gasit " + results + " rezultate:");
            for (int id : resultsIds) {
                for (Student student : dataLoader.getStudents()) {
                    if (student.getStudentId() == id) {
                        System.out.println("( id:" + student.getStudentId() + " )  |  " + student.getStudentName());
                    }
                }
            }
        } else {
            System.out.println("\nNu s-a gasit nici un rezultat!");
        }
    }

    public static void pressEnterKey() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\napasa ENTER pentru a continua...");
        scanner.nextLine();
    }
}
