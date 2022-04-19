package org.loose.fis.sre.exceptions;

public class IncorrectCredentials extends Exception {

    private String username;

    public IncorrectCredentials(String username) {
        super(String.format("Incorrect credentials for username %s", username));
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

