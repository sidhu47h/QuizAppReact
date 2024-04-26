package com.quizapp.QuizApp.models;

import lombok.Getter;

@Getter
public class Result {
    private String quizName;
    private String userName;
    private int correctAnswers;
    private int totalQuestions;
    private long totalTimeTaken;
    private String date;

    public Result(String quizName, String userName, int correctAnswers, int totalQuestions, long totalTimeTaken, String date) {
        this.quizName = quizName;
        this.userName = userName;
        this.correctAnswers = correctAnswers;
        this.totalQuestions = totalQuestions;
        this.totalTimeTaken = totalTimeTaken;
        this.date = date;
    }

    @Override
    public String toString() {
        return "QuizFile: " + quizName + ", " +
                "User: " + userName + ", " +
                "Correct Answers: " + correctAnswers + " out of " + totalQuestions + ", " +
                "Time elapsed: " + totalTimeTaken + " milliseconds, " +
                "Date: " + date;
    }
}
