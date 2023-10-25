package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.model.Student;

@RequiredArgsConstructor
@Service
public class StudentServiceSimple implements StudentService {
    private final IOService ioService;

    @Override
    public Student getStudent() {
        return new Student(ioService.getInput("Please, enter your first name"),
                ioService.getInput("Please, enter your last name"));
    }
}
