package com.quizapp.QuizApp.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Authenticator {
    private static final String USERS_FILE = System.getProperty("user.dir") + "/.users";

    public static boolean authenticate(String username, String password) {
        try {
            Path path = Paths.get(USERS_FILE);
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                String[] parts = line.split(" ");
                if (parts.length == 2 && parts[0].equals(username)) {
                    return parts[1].equals(password);
                }
            }

            Files.write(path, (username + " " + password + "\n").getBytes(), StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
