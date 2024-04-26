package com.quizapp.QuizApp.utils;

import java.io.File;
import java.io.IOException;

public class LogUtils {
    public static String getLogFileNameForOS() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return "/quizlog.dat";
        } else {
            return "/.quizlog"; // This file will be hidden on macOS
        }
    }

    public static void ensureLogAndUsedQuestionFilesArePresent()  {
        String usedQuestionFileName = "/usedQuestions.txt";
        String logFileNameForOS = getLogFileNameForOS();
        String logFilePath = System.getProperty("user.dir") + logFileNameForOS;
        String usedQuestionFilePath = System.getProperty("user.dir") + usedQuestionFileName;

        File logFile = new File(logFilePath);
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
                if (System.getProperty("os.name").toLowerCase().contains("win")) {
                    hideFileWindows(logFile);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File usedQuestionFile = new File(usedQuestionFilePath);
        if (!usedQuestionFile.exists()) {
            try {
                usedQuestionFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void hideFileWindows(File file) {
        try {
            // Run attrib command to hide file
            Process p = Runtime.getRuntime().exec("attrib +H " + file.getAbsolutePath());
            p.waitFor(); // Wait for command to finish
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}