package org.loose.fis.sre.exceptions;

public class NoPropertyAvailable extends Exception{
    public NoPropertyAvailable(){
        super(String.format("No properties available, please introduce different dates"));
    }
}
