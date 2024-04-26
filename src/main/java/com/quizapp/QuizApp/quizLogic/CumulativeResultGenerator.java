package com.quizapp.QuizApp.quizLogic;

import com.quizapp.QuizApp.utils.LogUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CumulativeResultGenerator {
    public static String showCumulativeResult(String username) {
        String logFileName = LogUtils.getLogFileNameForOS();
        String filepath = System.getProperty("user.dir") + logFileName;
//        System.out.println(filepath);
        File file = new File(filepath);
        String result = "\n";

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains(username)) {
                        result += line;
                        result += "\n";
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return "File does not exist.";
        }
        if (!System.getProperty("os.name").toLowerCase().contains("win")) {
            System.out.println(result);
        }
        return result;
    }
}
