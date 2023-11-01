package ru.otus.spring.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.otus.spring.config.QuestionFileNameProvider;
import ru.otus.spring.exception.QuestionReadException;
import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class CsvQuestionDao implements QuestionDao {
    private final QuestionFileNameProvider provider;

    public CsvQuestionDao(QuestionFileNameProvider provider) {
        this.provider = provider;
    }

    @Override
    public List<Question> getAll() throws QuestionReadException {
        List<Question> questions = new ArrayList<>();
        int id = 1;

        try (InputStream is = getFileFromResourceAsStream(provider.getQuestionFileName());
             InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                questions.add(parseQuestionFromString(id, line));
                id++;
            }
        } catch (IOException e) {
            throw new QuestionReadException("Question repository read error", e);
        }
        return questions;
    }

    private Question parseQuestionFromString(int id, String line) {
        String[] s = line.split(",");
        if (s.length < 3) {
            throw new QuestionReadException("Question " + s[0] + "has no answers.");
        }
        Question q = new Question(id, s[0].trim());

        for (int i = 1; i < s.length - 1; i++) {
            q.addAnswer(i, new Answer(s[i].trim(), Integer.parseInt(s[s.length - 1]) == i));
        }
        return q;
    }


    private InputStream getFileFromResourceAsStream(String fileName) throws QuestionReadException {
        InputStream inputStream = getClass().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new QuestionReadException(String.format("File %s not found!", fileName));
        } else {
            return inputStream;
        }
    }
}