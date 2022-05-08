package org.loose.fis.sre.exceptions;

public class ClientDoesNotHavePastReservationsException extends Exception{
    public ClientDoesNotHavePastReservationsException(String username) {
        super(String.format("The client %s does not have any past reservations", username));
    }
}
