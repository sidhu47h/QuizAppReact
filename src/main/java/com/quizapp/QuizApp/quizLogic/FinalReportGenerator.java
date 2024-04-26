package com.quizapp.QuizApp.quizLogic;

import com.quizapp.QuizApp.models.Result;
import com.quizapp.QuizApp.utils.LogUtils;

import java.io.FileWriter;
import java.io.IOException;

public class FinalReportGenerator {
    static void showFinalReport(Result result) {
        System.out.println("Quiz Complete!");
        System.out.println("Correct Answers: " + result.getCorrectAnswers() + " out of " + result.getTotalQuestions() + " Time elapsed: " + result.getTotalTimeTaken() + " seconds");
        double percentage = 100.0 * result.getCorrectAnswers() / result.getTotalQuestions();
        System.out.printf("Score: %.2f%%%n", percentage);

        storeResultInTheLog(result);
    }

    public static void storeResultInTheLog(Result result) {
        String logFileName = LogUtils.getLogFileNameForOS();
        String filepath = System.getProperty("user.dir") + logFileName;
        // Store the result in a log file
        System.out.println("Saving result to your cumulative results");
        try (FileWriter writer = new FileWriter(filepath, true)) {
            writer.write(result.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
