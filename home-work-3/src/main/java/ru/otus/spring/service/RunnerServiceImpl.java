package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RunnerServiceImpl implements RunnerService {

    private final QuestionService questionService;

    @Override
    public void run() {
        questionService.startTesting();
    }
}
