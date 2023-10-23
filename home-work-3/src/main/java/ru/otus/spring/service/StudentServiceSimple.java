package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.model.Student;

import java.util.Locale;

@RequiredArgsConstructor
@Service
public class StudentServiceSimple implements StudentService {
    private final IOService ioService;

    private final MessageSource messageSource;

    @Value("${app.questions.locale}")
    private Locale locale;

    @Override
    public Student getStudent() {
        var firstName = ioService.getInput(
                messageSource.getMessage("user.input.firstName", new Object[]{}, locale));
        var lastName = ioService.getInput(
                messageSource.getMessage("user.input.lastName", new Object[]{}, locale));
        ioService.printMessage(messageSource.getMessage("user.hello",
                new String[]{firstName + " " + lastName}, locale));
        return new Student(firstName, lastName);
    }
}
