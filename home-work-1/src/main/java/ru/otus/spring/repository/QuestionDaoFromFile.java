package ru.otus.spring.repository;

import ru.otus.spring.exception.QuestionReadException;
import ru.otus.spring.model.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class QuestionDaoFromFile implements QuestionDao {
    private final Map<Integer, Question> questions = new HashMap<>();

    public QuestionDaoFromFile(String fileName) {
        readQuestionFile(fileName);
    }

    private void readQuestionFile(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new QuestionReadException(String.format("File %s not found!", fileName));
        }

        try (InputStreamReader streamReader =
                     new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] s = line.split(",");
                questions.put(Integer.valueOf(s[0]), new Question(Integer.valueOf(s[0]), s[1].trim()));
            }

        } catch (IOException e) {
            throw new QuestionReadException("Question repository read error", e);
        }
    }


    @Override
    public Optional<Question> getQuestionById(Integer id) {
        if (questions.get(id) != null) {
            Question q = questions.get(id).clone();
            return Optional.of(q);
        }
        return Optional.empty();
    }

    @Override
    public void save(Question question) {
        questions.put(question.getId(), question);
    }

    @Override
    public Set<Question> getAllQuestions() {
        return questions.values().stream()
                .map(Question::clone)
                .collect(Collectors.toSet());
    }

}
