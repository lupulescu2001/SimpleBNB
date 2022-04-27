package org.loose.fis.sre.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.Id;
import org.dizitart.no2.objects.ObjectRepository;
import org.jetbrains.annotations.NotNull;
import org.loose.fis.sre.exceptions.PropertyAlreadyExistsException;
import org.loose.fis.sre.model.Property;
import org.loose.fis.sre.model.PropertyUnavailable;
import org.loose.fis.sre.model.User;
import org.loose.fis.sre.exceptions.PropertyDoesNotExistException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import org.dizitart.no2.objects.filters.ObjectFilters;
import org.loose.fis.sre.services.PropertyService;
import org.loose.fis.sre.exceptions.PropertyAlreadyUnavailableException;
import org.loose.fis.sre.exceptions.IncorrectDateException;

import static org.loose.fis.sre.services.FileSystemService.getPathToFile;

public class PropertyUnavailableService {
    private static ObjectRepository<PropertyUnavailable> propertyUnavailableRepository;
    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("SimpleBNBunavailable4.db").toFile())
                .openOrCreate("SimpleBNBunavailable4", "SimpleBNBunavailable4");

        propertyUnavailableRepository = database.getRepository(PropertyUnavailable.class);
    }
    public static void addUnavailableDate(PropertyUnavailable x) throws IncorrectDateException, PropertyDoesNotExistException, PropertyAlreadyUnavailableException {
        checkDateIsCorrect(x);
        propertyDoesNotExist(x);
        propertyAlreadyUnavailable(x);
        propertyUnavailableRepository.insert(x);

    }
    private static void checkDateIsCorrect(PropertyUnavailable x) throws IncorrectDateException {
        if (x.date() == false)
            throw new IncorrectDateException(x);
        if (x.validDate() == false)
            throw new IncorrectDateException(x);
        if (x.firstGreaterThanLast() == false)
            throw new IncorrectDateException(x);
    }
    private static void propertyAlreadyUnavailable(PropertyUnavailable x) throws PropertyAlreadyUnavailableException {
        for (PropertyUnavailable propertyUnavailable : propertyUnavailableRepository.find()) {
            if (x.compare(propertyUnavailable) == false && Objects.equals(x.getName(), propertyUnavailable.getName()) && Objects.equals(x.getUsername(), propertyUnavailable.getUsername()))
                throw new PropertyAlreadyUnavailableException(x);
        }
    }
    private static void propertyDoesNotExist(PropertyUnavailable x) throws PropertyDoesNotExistException {
        int ok = 0;
        for (Property property : PropertyService.getAll()) {
            if (Objects.equals(x.getName(), property.getName()) && Objects.equals(x.getUsername(), property.getUsername()))
                ok = 1;
        }
        if (ok == 0)
            throw new PropertyDoesNotExistException(x.getName());
    }
    public static List<String> getAllUnavailable(String username) {
        List<String> list = new ArrayList<String>();
        for (PropertyUnavailable propertyUnavailable : propertyUnavailableRepository.find()) {
            if (Objects.equals(username, propertyUnavailable.getUsername()))
                list.add(propertyUnavailable.getName() + " unavailable from " + propertyUnavailable.getFirstDay() + '/' +
                        propertyUnavailable.getFirstMonth() + '/' + propertyUnavailable.getFirstYear() + " to " + propertyUnavailable.getLastDay() + '/' +
                        propertyUnavailable.getLastMonth() + '/' + propertyUnavailable.getLastYear());
        }
        return list;
    }
    public static int getTheId() {
        int id = 0;
        for (PropertyUnavailable propertyUnavailable : propertyUnavailableRepository.find())
                id = propertyUnavailable.getId() + 1;
        return id;
    }
}
