package org.loose.fis.sre.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.sre.exceptions.PropertyAlreadyExistsException;
import org.loose.fis.sre.model.Property;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.List;

import static org.loose.fis.sre.services.FileSystemService.getPathToFile;

public class PropertyService {
    private static ObjectRepository<Property> propertyRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("SimpleBNBproperty.db").toFile())
                .openOrCreate("SimpleBNBproperty", "SimpleBNBproperty");

        propertyRepository = database.getRepository(Property.class);
    }

    public static void addProperty(String name, String cityName, String description, String username) throws PropertyAlreadyExistsException {
        checkPropertyDoesNotAlreadyExist(name);
        propertyRepository.insert(new Property(cityName, description, username, name));
    }
    private static void checkPropertyDoesNotAlreadyExist(String name) throws PropertyAlreadyExistsException {
        for (Property property : propertyRepository.find()) {
            if (Objects.equals(name, property.getName()))
                throw new PropertyAlreadyExistsException(name);
        }
    }
}
