package org.loose.fis.sre.exceptions;

public class PropertyDoesNotExistException extends Exception{
    private String username;

    public PropertyDoesNotExistException(String username) {
        super(String.format("A property with the name %s does not exist!", username));
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
