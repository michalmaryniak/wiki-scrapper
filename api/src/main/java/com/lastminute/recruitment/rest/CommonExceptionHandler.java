package com.lastminute.recruitment.rest;

import com.lastminute.recruitment.domain.error.WikiPageCannotParse;
import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(WikiPageNotFound.class)
    public ResponseEntity<Void> handleWikiPageNotFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WikiPageCannotParse.class)
    public ResponseEntity<Void> handleWikiPageCannotParse() {
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Void> handleBadRequest() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleUnexpected() {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
