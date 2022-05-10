package org.loose.fis.sre.exceptions;

public class NoRequestSelectedException extends Exception{
    public NoRequestSelectedException() {
        super("You have not selected a booking request!");
    }
}
