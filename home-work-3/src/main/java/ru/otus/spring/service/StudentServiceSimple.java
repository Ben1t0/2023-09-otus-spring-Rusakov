package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.model.Student;

@RequiredArgsConstructor
@Service
public class StudentServiceSimple implements StudentService {
    private final LocalizedIOService localizedIOService;

    @Override
    public Student getStudent() {
        var firstName = localizedIOService.readStringWithPromptLocalized("user.input.firstName");
        var lastName = localizedIOService.readStringWithPromptLocalized("user.input.lastName");
        localizedIOService.printFormattedMessageLocalized("user.hello", firstName + " " + lastName);
        return new Student(firstName, lastName);
    }
}
