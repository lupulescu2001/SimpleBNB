package org.loose.fis.sre.exceptions;

import org.loose.fis.sre.model.PropertyUnavailable;

public class IncorrectDateException extends Exception {
    private PropertyUnavailable x;
    public IncorrectDateException(PropertyUnavailable y) {
        super(String.format("The following dates are incorrect: %s/%s/%s and %s/%s/%s", y.getFirstDay(), y.getFirstMonth(), y.getFirstYear(), y.getLastDay(), y.getLastMonth(), y.getLastYear()));
        x = y;
    }

    public void setName(PropertyUnavailable x) {
        this.x = x;
    }
}
