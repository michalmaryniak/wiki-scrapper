package com.lastminute.recruitment.domain.error;

public class WikiPageCannotParse extends RuntimeException {
    public WikiPageCannotParse(String message) {
        super(message);
    }

    public WikiPageCannotParse(String message, Throwable cause) {
        super(message, cause);
    }
}
