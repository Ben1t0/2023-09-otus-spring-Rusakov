package ru.otus.spring.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.config.QuestionFileNameProvider;
import ru.otus.spring.exception.QuestionReadException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CsvQuestionDaoUnitTest {

    @Mock
    QuestionFileNameProvider provider;

    @InjectMocks
    private CsvQuestionDao dao;


    @Test
    void shouldThrowExceptionWhenFileNotFound() {
        when(provider.getQuestionFileName()).thenReturn("missing_file.csv");

        assertThatThrownBy(dao::getAll)
                .isInstanceOf(QuestionReadException.class)
                .hasMessage("File missing_file.csv not found!");
    }
}
