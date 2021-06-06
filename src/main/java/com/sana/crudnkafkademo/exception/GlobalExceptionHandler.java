package com.sana.crudnkafkademo.exception;

import com.sana.crudnkafkademo.model.ErrorMsg;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                                         HttpHeaders headers, HttpStatus status, WebRequest request) {
        //ErrorMessage error = new ErrorMessage(status.value(), e.getMessage(), status.getReasonPhrase(), LocalDateTime.now());
        ErrorMsg error = new ErrorMsg(LocalDateTime.now(), status.value(), status.getReasonPhrase(), e.getMessage());
        return ResponseEntity.status(status).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException e,
                                                                     HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpHeaders header = new HttpHeaders();
        header.add("error", "Media type not supported");
        //ErrorMessage error = new ErrorMessage(status.value(), e.getMessage(), status.getReasonPhrase(), LocalDateTime.now());
        ErrorMsg error = new ErrorMsg(LocalDateTime.now(), status.value(), status.getReasonPhrase(), e.getMessage());
        return ResponseEntity.status(status).headers(headers).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException e, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {
        HttpHeaders header = new HttpHeaders();
        header.add("error", "Missing Path Variable");
        //ErrorMessage error = new ErrorMessage(status.value(), e.getMessage(), status.getReasonPhrase(), LocalDateTime.now());
        ErrorMsg error = new ErrorMsg(LocalDateTime.now(), status.value(), status.getReasonPhrase(), e.getMessage());
        return ResponseEntity.status(status).headers(headers).body(error);

    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpHeaders header = new HttpHeaders();
        header.add("error", "Missing RequestParam");
        //ErrorMessage error = new ErrorMessage(status.value(), e.getMessage(), status.getReasonPhrase(), LocalDateTime.now());
        ErrorMsg error = new ErrorMsg(LocalDateTime.now(), status.value(), status.getReasonPhrase(), e.getMessage());
        return ResponseEntity.status(status).headers(headers).body(error);

    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException e, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {
        HttpHeaders header = new HttpHeaders();
        header.add("error", "Input mismatch");
        //ErrorMessage error = new ErrorMessage(status.value(), e.getMessage(), status.getReasonPhrase(), LocalDateTime.now());
        ErrorMsg error = new ErrorMsg(LocalDateTime.now(), status.value(), status.getReasonPhrase(), e.getMessage());
        return ResponseEntity.status(status).headers(headers).body(error);

    }

    @ExceptionHandler(AuthorNotFoundException.class)
    protected ResponseEntity<Object> handleStudentNotFoundException(AuthorNotFoundException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String reason = HttpStatus.BAD_REQUEST.getReasonPhrase();
        HttpHeaders header = new HttpHeaders();
        header.add("error", "Author not found");
        //ErrorMessage error = new ErrorMessage(status.value(), e.getMessage(), status.getReasonPhrase(), LocalDateTime.now());
        ErrorMsg error = new ErrorMsg(LocalDateTime.now(), status.value(), reason, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(header).body(error);
    }

    @ExceptionHandler(BookIsbnNotFoundException.class)
    protected ResponseEntity<Object> handleISbnNotFoundException(BookIsbnNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String reason = HttpStatus.NOT_FOUND.getReasonPhrase();
        HttpHeaders header = new HttpHeaders();
        header.add("error", "Isbn not found");
        //ErrorMessage error = new ErrorMessage(status.value(), e.getMessage(), reason, LocalDateTime.now());
        ErrorMsg error = new ErrorMsg(LocalDateTime.now(), status.value(), reason, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(header).body(error);

    }

    @ExceptionHandler(BookNotFoundException.class)
    protected ResponseEntity<Object> handleBookNotFoundException(BookNotFoundException e) {
        HttpStatus status = HttpStatus.FAILED_DEPENDENCY;
        String reason = HttpStatus.FAILED_DEPENDENCY.getReasonPhrase();
        HttpHeaders header = new HttpHeaders();
        header.add("error", "Book not found");
        //ErrorMessage error = new ErrorMessage(status.value(), e.getMessage(), status.getReasonPhrase(), LocalDateTime.now());
        ErrorMsg error = new ErrorMsg(LocalDateTime.now(), status.value(), reason, e.getMessage());
        return ResponseEntity.status(status).headers(header).body(error);

    }
}
