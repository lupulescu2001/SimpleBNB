package org.loose.fis.sre.exceptions;

public class ThisClientDidNotTryToRentYourPropertyException extends Exception {
    private String clientUsername;
    public ThisClientDidNotTryToRentYourPropertyException(String clientUsername) {
        super(String.format("The client %s did not try to rent any of your properties during the selected period!", clientUsername));
        this.clientUsername = clientUsername;
    }
}
