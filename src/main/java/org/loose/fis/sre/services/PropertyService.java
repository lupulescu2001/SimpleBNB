package org.loose.fis.sre.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.Id;
import org.dizitart.no2.objects.ObjectRepository;
import org.jetbrains.annotations.NotNull;
import org.loose.fis.sre.exceptions.PropertyAlreadyExistsException;
import org.loose.fis.sre.model.Property;
import org.loose.fis.sre.model.User;
import org.loose.fis.sre.exceptions.PropertyDoesNotExistException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import org.dizitart.no2.objects.filters.ObjectFilters;

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
    public static List<String> getAllProperties(String username){
        List<String> list = new ArrayList<String>();
        for (Property property : propertyRepository.find()) {
            if (Objects.equals(username, property.getUsername()))
                list.add(property.getName() + '/' + property.getCityName() + '/' + property.getDescription());
        }
        return list;
    }
    public static List<String> getAllPropertiesByName(List<String> list){
        List<String> sol = new ArrayList<String>();
        for(String x : list)
        for (Property property : propertyRepository.find()) {
            if (Objects.equals(x, property.getName()))
                sol.add(property.getName() + '/' + property.getCityName() + '/' + property.getDescription());
        }
        return sol;
    }

    public static void changeDescription(String name, String username, String description) throws PropertyDoesNotExistException {
        int ok = 0;
        for (Property property : propertyRepository.find()) {
            if (Objects.equals(username, property.getUsername()) && Objects.equals(name, property.getName())) {
                property.setDescription(description);
                propertyRepository.update(property);
                ok = 1;
            }
        }
        if (ok == 0)
            throw new PropertyDoesNotExistException(name);
    }
    public static void deleteProperty(String name, String username) throws PropertyDoesNotExistException {
        int ok = 0;
        for (Property property : propertyRepository.find()) {
            if (Objects.equals(username, property.getUsername()) && Objects.equals(name, property.getName())) {
                propertyRepository.remove(property);
                ok = 1;
            }
        }
        if (ok == 0)
            throw new PropertyDoesNotExistException(name);
    }
    public static List<Property> getAll(){
        return propertyRepository.find().toList();
    }
}
