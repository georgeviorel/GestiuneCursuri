package com.itfactory.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DataLoaderUtils {
    public static final String COURSE_FILE_PATH = "cursuri.csv";
    public static final String STUDENT_FILE_PATH = "studenti.csv";
    public static final String MAPPING_FILE_PATH = "mapari.csv";

    public static List<String> readFile(String filePathStr) throws IOException {
        Path path = Paths.get(filePathStr);
        // file exists ?
        return Files.readAllLines(path);
    }
}
