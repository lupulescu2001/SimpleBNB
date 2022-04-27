package org.loose.fis.sre.exceptions;

import org.loose.fis.sre.model.PropertyUnavailable;

public class PropertyAlreadyUnavailableException extends Exception {
    private PropertyUnavailable x;
    public PropertyAlreadyUnavailableException(PropertyUnavailable y) {
        super(String.format("The property with the name %s is already unavailable some time between %s/%s/%s and %s/%s/%s", y.getName(), y.getFirstDay(), y.getFirstMonth(), y.getFirstYear(), y.getLastDay(), y.getLastMonth(), y.getLastYear()));
        x = y;
    }
    public PropertyUnavailable getX() {
        return x;
    }
}
