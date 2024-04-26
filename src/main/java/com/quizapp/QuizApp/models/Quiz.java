package com.quizapp.QuizApp.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Quiz {
    private String quizFileName;
    private int noOfQuestions;
    private int timeLimit;
}
