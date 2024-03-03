package ru.otus.spring.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.otus.spring.exceptions.NotFoundException;
import ru.otus.spring.utils.ApiError;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ApiError apiError = new ApiError("Incorrectly made request.");
        apiError.setErrors(ex.getBindingResult().getFieldErrors().stream()
                .map(error -> "Field: " + error.getField() + ". Error: " + error.getDefaultMessage() +
                        ". Value: " + error.getRejectedValue())
                .collect(Collectors.toList()));
        apiError.setMessage("During [" + ex.getBindingResult().getObjectName() + "] validation " +
                ex.getBindingResult().getFieldErrors().size() + " errors were found");
        return apiError;
    }


    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(NotFoundException ex) {
        ApiError apiError = new ApiError("The required object was not found.");
        apiError.setMessage(ex.getMessage());
        return apiError;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError globalExceptionHandler(Exception ex) {
        ApiError apiError = new ApiError("Internal server error.");
        apiError.setErrors(new ArrayList<>());
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            ex.printStackTrace(pw);
            apiError.getErrors().add(sw.toString());
        }
        log.error("Error", ex);
        apiError.setMessage(ex.getMessage());
        return apiError;
    }
}
