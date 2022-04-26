package org.loose.fis.sre.exceptions;

public class PropertyAlreadyExistsException extends Exception {
    private String name;

    public PropertyAlreadyExistsException(String name) {
        super(String.format("A property with the name %s already exists!", name));
        this.name = name;
    }

    public String getUsername() {
        return name;
    }
}
