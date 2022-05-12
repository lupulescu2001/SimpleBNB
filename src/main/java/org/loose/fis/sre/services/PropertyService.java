package org.loose.fis.sre.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.sre.exceptions.IncorrectScoreException;
import org.loose.fis.sre.exceptions.PropertyAlreadyExistsException;
import org.loose.fis.sre.model.Property;
import org.loose.fis.sre.exceptions.PropertyDoesNotExistException;
import org.loose.fis.sre.services.BookingRequestService;
import org.loose.fis.sre.services.FileSystemService;

import java.util.*;

public class PropertyService {
    private static ObjectRepository<Property> propertyRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(FileSystemService.getPathToFile("SimpleBNBproperty2.db").toFile())
                .openOrCreate("SimpleBNBproperty2", "SimpleBNBproperty2");

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
        float rev;
        String review;
        for(String x : list)
        for (Property property : propertyRepository.find()) {
            if (Objects.equals(x, property.getName())) {
                rev = property.getReview();
                if(rev==0)
                    review="No reviews yet";
                else
                    review=Float.toString(rev);
                sol.add(property.getName() + '/' + property.getCityName() + '/' + property.getDescription() + '/' + review);
            }
        }
        return sol;
    }
    public static Property getPropertyByName(String name){
        Property sol=new Property();
        for(Property property:propertyRepository.find())
            if(Objects.equals(name,property.getName()))
                sol=property;
        return sol;

    }
    public static void addReview(String prop_name,int rev,String client_username) throws IncorrectScoreException,PropertyDoesNotExistException {

        if(rev<1 || rev>10)
            throw new IncorrectScoreException();
        int ok=0;
        for (String s : BookingRequestService.getAllPastRequestsNameForUser(client_username)){
            if(Objects.equals(s,prop_name))
                ok=1;
        }
        if(ok==0)
            throw new PropertyDoesNotExistException(prop_name);

        for(Property property:propertyRepository.find())
            if(Objects.equals(prop_name,property.getName()))
            {
                int nr=property.getNr_of_reviews();
                int sum=property.getSum_of_reviews();

                nr+=1;
                sum+=rev;
                property.setNr_of_reviews(nr);
                property.setSum_of_reviews(sum);
                propertyRepository.update(property);
              //  System.out.println(property.getNr_of_reviews());
            }
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
