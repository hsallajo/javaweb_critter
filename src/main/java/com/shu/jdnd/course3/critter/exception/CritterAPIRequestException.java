package com.shu.jdnd.course3.critter.exception;

public class CritterAPIRequestException extends RuntimeException {
    public CritterAPIRequestException(String message) {
        super(message);
    }

    public CritterAPIRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
