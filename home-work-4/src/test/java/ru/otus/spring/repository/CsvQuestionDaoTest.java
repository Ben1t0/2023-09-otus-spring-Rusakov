package ru.otus.spring.repository;

import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CsvQuestionDaoTest {
    @Autowired
    private QuestionDao dao;


    @Test
    void shouldCorrectReadQuestions() {
        Question q1 = new Question(1, "Что такое Spring?");
        q1.addAnswer(1, new Answer("язык", false));
        q1.addAnswer(2, new Answer("фреймворк", true));
        q1.addAnswer(3, new Answer("велосипед", false));
        Question q2 = new Question(2, "Что такое IoC ?");
        q2.addAnswer(1, new Answer("механизм фреймворка для контроля зависимостей", true));
        q2.addAnswer(2, new Answer("мнимый контроль", false));
        Question q3 = new Question(3, "Можно ли хранить данные приложения в контексте?");
        q3.addAnswer(1, new Answer("Да", false));
        q3.addAnswer(2, new Answer("Нет", true));
        Question q4 = new Question(4, "Какая аннотация служит для классов слоя хранения данных?");
        q4.addAnswer(1, new Answer("@Service", false));
        q4.addAnswer(2, new Answer("@Configuration", false));
        q4.addAnswer(3, new Answer("@Bean", false));
        q4.addAnswer(4, new Answer("@Repository", true));
        Question q5 = new Question(5, "Какая аннотация служит для выбора файла с настройками приложения?");
        q5.addAnswer(1, new Answer("@PropertySource", true));
        q5.addAnswer(2, new Answer("@PropertySauce", false));
        q5.addAnswer(3, new Answer("@PropertyMouse", false));

        Set<Question> questions = Set.of(q1, q2, q3, q4, q5);

        RecursiveComparisonConfiguration configuration = RecursiveComparisonConfiguration.builder()
                .withIgnoreCollectionOrder(true)
                .withIgnoredCollectionOrderInFields("answers")
                .build();

        var readQuestions = dao.getAll();
        assertThat(readQuestions)
                .hasSize(5)
                .usingRecursiveFieldByFieldElementComparator(configuration)
                .containsAll(questions);
    }
}