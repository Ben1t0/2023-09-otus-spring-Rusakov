package ru.otus.spring.repository;

import ru.otus.spring.exception.AnswerReadException;
import ru.otus.spring.model.Answer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class AnswerDaoFromFile implements AnswerDao {

    private final String fileName;

    public AnswerDaoFromFile(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Answer> getAllAnswers() {
        List<Answer> answers = new ArrayList<>();
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new AnswerReadException(String.format("File %s not found!", fileName));
        }

        try (InputStreamReader streamReader =
                     new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] s = line.split(",");
                answers.add(new Answer(Integer.valueOf(s[0]), s[1].trim()));
            }

        } catch (IOException e) {
            throw new AnswerReadException("Answer repository read error", e);
        }
        return answers;
    }
}
