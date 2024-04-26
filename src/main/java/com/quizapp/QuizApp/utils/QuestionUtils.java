package com.quizapp.QuizApp.utils;

import com.quizapp.QuizApp.models.QuestionFiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionUtils {
    public static QuestionFiles getQuestionFiles() throws IOException {
        //get all the filenames in the questionFiles folder.
        String filePath = System.getProperty("user.dir") + "/questionFiles/";
        List<String> questionFileNames = getFilesInDirectory(filePath);
        System.out.println(questionFileNames);
        QuestionFiles questionFiles = new QuestionFiles(questionFileNames);
        return questionFiles;
    }

    public static List<String> getFilesInDirectory(String directoryPath) throws IOException {
        try {
            return Files.walk(Paths.get(directoryPath))
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IOException("Error reading files from directory: " + directoryPath, e);
        }
    }
}
