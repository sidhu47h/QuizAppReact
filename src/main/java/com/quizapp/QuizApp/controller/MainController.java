package com.quizapp.QuizApp.controller;

import com.quizapp.QuizApp.models.*;
import com.quizapp.QuizApp.quizLogic.CumulativeResultGenerator;
import com.quizapp.QuizApp.quizLogic.FinalReportGenerator;
import com.quizapp.QuizApp.quizLogic.QuestionsGenerator;
import com.quizapp.QuizApp.utils.Authenticator;
import com.quizapp.QuizApp.utils.QuestionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "https://quiz-frontend-sandy.vercel.app", "http://sidhusportablebucket.s3-website-us-west-1.amazonaws.com"})
@RestController
public class MainController {

    @PostMapping("/authenticate")
    public ResponseEntity<Boolean> authenticate(@RequestBody User user) {
        Boolean userExists = Authenticator.authenticate(user.getUsername(), user.getPassword());
        System.out.println(user.getUsername() + " " + user.getPassword() + " " + userExists);
        return new ResponseEntity<>(userExists, HttpStatus.OK);
    }

    @PostMapping("/storeResult")
    public ResponseEntity<String> storeResult(@RequestBody Result result) {
        // Store the result in the database
        FinalReportGenerator.storeResultInTheLog(result);
        return new ResponseEntity<>("Result stored successfully", HttpStatus.OK);
    }

    @PostMapping("/questions")
    public ResponseEntity<List<Question>> getQuestions(@RequestBody Quiz quiz) throws NoSuchAlgorithmException, IOException {
        List<Question> questions = QuestionsGenerator.parseAndGenerateQuestions(quiz.getQuizFileName(), quiz.getNoOfQuestions(), "usedQuestions.txt");

        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @PostMapping("/cumulativeResults")
    public ResponseEntity<String> getCumulativeResults(@RequestBody User user) {
        // Get the cumulative results from the database
        String results = CumulativeResultGenerator.showCumulativeResult(user.getUsername());
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/getQuestionFiles")
    public ResponseEntity<QuestionFiles> getQuestionFiles() throws IOException {
        // Get the question files from the database
        QuestionFiles questionFiles = QuestionUtils.getQuestionFiles();
        System.out.println(questionFiles.getQuestionFiles());
        return new ResponseEntity<>(questionFiles, HttpStatus.OK);
    }

    @PostMapping("/uploadQuestionFile")
    public ResponseEntity<String> uploadQuestionFile(@RequestParam("file") MultipartFile file) {
        try {
            // Define the path where you want to store the file
            String filePath = System.getProperty("user.dir") + "/questionFiles/" + file.getOriginalFilename();

            // Transfer the received file to the defined path
            file.transferTo(new File(filePath));

            return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to upload the file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
