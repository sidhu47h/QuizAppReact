package com.quizapp.QuizApp.quizLogic;

import com.quizapp.QuizApp.models.Question;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class QuestionsGenerator {
    public static List<Question> parseAndGenerateQuestions(String fileName, int numQuestions, String usedQuestionsFilePath) throws IOException {
        if(numQuestions == 0) {
            System.out.println("Number of questions requested are 0. Please enter the valid question count");
            return null;
        }

        List<Question> questions = new ArrayList<>();
        final String os = System.getProperty("os.name").toLowerCase();
        System.out.println(fileName);
        String filePath;
        if (os.contains("win")) {
            filePath = fileName;
        } else {
            filePath = System.getProperty("user.dir") + "/questionFiles/" + fileName;
        }

        System.out.println(filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            Question question = null;
            boolean isCollectingOptions = false;
            StringBuilder questionTextBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("@Q")) {
                    question = new Question();
                    isCollectingOptions = false;
                    questionTextBuilder = new StringBuilder();
                } else if (line.startsWith("@A")) {
                    if (question != null) {
                        String questionText = questionTextBuilder.toString().trim();
                        question.setQuestionText(questionText);
                        question.setAnswer(reader.readLine());
                        isCollectingOptions = true;
                        question.setHashCode(questionText);
                    }
                } else if (line.startsWith("@E")) {
                    isCollectingOptions = false;
                    questions.add(question);
                } else if (isCollectingOptions) {
                    question.addOption(line);
                } else {
                    questionTextBuilder.append(line).append(" ");
                }
            }
            if(questions.size() < numQuestions && numQuestions > 0) {
                System.out.println("Number of questions requested are more than the available questions. Please enter the valid question count");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return selectQuestionsRandomly(questions, numQuestions, usedQuestionsFilePath);
    }

    private static List<Question> selectQuestionsRandomly(List<Question> allQuestions, int numQuestions, String usedQuestionsFilePath) throws IOException {
        Set<String> usedQuestionHashCodes = new HashSet<>();
        File file = new File(usedQuestionsFilePath);
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    usedQuestionHashCodes.add(scanner.nextLine());
                }
            }
        } else {
            file.createNewFile();
        }

        List<Question> selectedQuestions = new ArrayList<>();
        List<Question> availableQuestions = new ArrayList<>();
        for (Question question : allQuestions) {
            if (!usedQuestionHashCodes.contains(question.getHashCode())) {
                availableQuestions.add(question);
            }
        }

        if (availableQuestions.size() < numQuestions) {
            return selectedQuestions;
        }


        Collections.shuffle(availableQuestions);
        selectedQuestions = availableQuestions.subList(0, numQuestions);

        try (FileWriter fw = new FileWriter(file, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            for (Question q : selectedQuestions) {
                if (!usedQuestionHashCodes.contains(q.getHashCode())) { // Check to avoid duplicate entries
                    out.println(q.getHashCode());
                }
            }
        }

        return selectedQuestions;
    }

    static boolean areSelectedQuestionsPresent(List<Question> selectedQuestions) {
        return selectedQuestions != null && selectedQuestions.size() > 0;
    }
}
