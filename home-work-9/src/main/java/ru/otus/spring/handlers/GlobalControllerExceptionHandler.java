package ru.otus.spring.handlers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.otus.spring.exceptions.NotFoundException;
import ru.otus.spring.utils.ApiError;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setErrors(ex.getBindingResult().getFieldErrors().stream()
                .map(error -> "Field: " + error.getField() + ". Error: " + error.getDefaultMessage() +
                        ". Value: " + error.getRejectedValue())
                .collect(Collectors.toList()));
        apiError.setMessage("During [" + ex.getBindingResult().getObjectName() + "] validation " +
                ex.getBindingResult().getFieldErrors().size() + " errors were found");
        apiError.setReason("Incorrectly made request.");
        return createResponse(apiError);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setReason("The required object was not found.");
        apiError.setMessage(ex.getMessage());
        return createResponse(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> globalExceptionHandler(Exception ex) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
        apiError.setReason("Internal server error.");
        apiError.setMessage(ex.getMessage());
        return createResponse(apiError);
    }

    private ResponseEntity<Object> createResponse(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
