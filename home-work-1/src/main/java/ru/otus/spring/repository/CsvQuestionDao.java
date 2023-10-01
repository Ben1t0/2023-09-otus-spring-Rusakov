package ru.otus.spring.repository;

import ru.otus.spring.exception.QuestionReadException;
import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class CsvQuestionDao implements QuestionDao {

    private final String fileName;

    public CsvQuestionDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Set<Question> getAllQuestions() {
        InputStream is = getFileFromResourceAsStream(fileName);
        Set<Question> questions = new HashSet<>();
        int id = 1;

        try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] s = line.split(",");
                Question q = new Question(id, s[0].trim());

                for (int i = 1; i < s.length - 1; i++) {
                    q.addAnswer(new Answer(s[i].trim(), Integer.parseInt(s[s.length - 1]) == i));
                }
                questions.add(q);
                id++;
            }
        } catch (IOException e) {
            throw new QuestionReadException("Question repository read error", e);
        }
        return questions;
    }

    private InputStream getFileFromResourceAsStream(String fileName) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(fileName);

            if (inputStream == null) {
                throw new QuestionReadException(String.format("File %s not found!", fileName));
            } else {
                return inputStream;
            }
        } catch (Exception e) {
            throw new QuestionReadException("Question repository read error", e);
        }
    }
}
