package org.loose.fis.sre.exceptions;

public class IncorrectScoreException extends Exception{
    public IncorrectScoreException(){
        super(String.format("Incorect type of score ,introduce a number from 1 to 10"));
    }
}
