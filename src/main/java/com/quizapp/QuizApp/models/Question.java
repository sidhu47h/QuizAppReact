package com.quizapp.QuizApp.models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Question {
    private String questionText;
    private String answer;
    private final List<String> options = new ArrayList<>();
    private String hashCode;

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void addOption(String option) {
        options.add(option);
    }

    public String getQuestionText() {
        return this.questionText;
    }

    public String getAnswer() {
        return this.answer;
    }

    public List<String> getOptions() {
        return this.options;
    }

    public String getHashCode() {
        return this.hashCode;
    }

    public void setHashCode(String questionText) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        byte[] hashBytes = digest.digest(questionText.getBytes());
        this.hashCode = Base64.getEncoder().encodeToString(hashBytes);
    }

    @Override
    public String toString() {
        return "Question: " + questionText + "\nAnswer: " + answer + "\nOptions: " + String.join(", ", options);
    }
}
