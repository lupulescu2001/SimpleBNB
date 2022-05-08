package org.loose.fis.sre.exceptions;

public class MarkIsIncorrectException extends Exception {
    public MarkIsIncorrectException() {
        super("The mark you introduced is incorrect");
    }
}
