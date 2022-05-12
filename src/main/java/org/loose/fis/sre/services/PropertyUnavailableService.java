package org.loose.fis.sre.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.sre.exceptions.*;
import org.loose.fis.sre.model.Property;
import org.loose.fis.sre.model.PropertyUnavailable;
import org.loose.fis.sre.services.FileSystemService;
import org.loose.fis.sre.services.PropertyService;

import java.util.*;

public class PropertyUnavailableService {
    private static ObjectRepository<PropertyUnavailable> propertyUnavailableRepository;
    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(FileSystemService.getPathToFile("SimpleBNBunavailable6.db").toFile())
                .openOrCreate("SimpleBNBunavailable6", "SimpleBNBunavailable6");

        propertyUnavailableRepository = database.getRepository(PropertyUnavailable.class);
    }
    public static void addUnavailableDate(PropertyUnavailable x) throws IncorrectDateException, PropertyDoesNotExistException, PropertyAlreadyUnavailableException {
        checkDateIsCorrect(x);
        propertyDoesNotExist(x);
        propertyAlreadyUnavailable(x);
        propertyUnavailableRepository.insert(x);

    }
    public static void bookingToUnavailable(PropertyUnavailable x) throws PropertyAlreadyUnavailableException {
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

    public static List<String> searchByCity(String city) throws NoPropertyInThisCity {
        List<String> list = new ArrayList<>();
        int ok=0;
        for(Property prop : PropertyService.getAll()){
            if(Objects.equals(city,prop.getCityName())){
                list.add(prop.getName());
                ok=1;
            }
        }
        if(ok==0)
            throw new NoPropertyInThisCity(city);
        return list;
    }
    public static List<String> searchbByDate(List<String> x,String inday,String inmonth,String inyear,String outday,String outmonth,String outyear) throws IncorrectDateException,NoPropertyAvailable{
        List<String> available= new ArrayList<>();
        PropertyUnavailable p=new PropertyUnavailable(0,"nu","prop",inday,inmonth,inyear,outday,outmonth,outyear);
        checkDateIsCorrect(p);
        for(String prop : x) {
            int ok=1;
            for (PropertyUnavailable prop_un : propertyUnavailableRepository.find()) {
                if (Objects.equals(prop, prop_un.getName()) && p.compare(prop_un)==false) {
                    ok=0;
                }
            }
            if(ok==1)
                available.add(prop);
        }
        if(available.size()==0){
            throw new NoPropertyAvailable();
        }

        return available;
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
                id = (id > propertyUnavailable.getId() + 1) ? id : (propertyUnavailable.getId() + 1);
        return id;
    }
}
