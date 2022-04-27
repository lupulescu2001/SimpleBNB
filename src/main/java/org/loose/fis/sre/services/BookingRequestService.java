package org.loose.fis.sre.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.Id;
import org.dizitart.no2.objects.ObjectRepository;
import org.jetbrains.annotations.NotNull;
import org.loose.fis.sre.exceptions.*;
import org.loose.fis.sre.model.BookingRequest;
import org.loose.fis.sre.model.Property;
import org.loose.fis.sre.model.PropertyUnavailable;
import org.loose.fis.sre.model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import org.dizitart.no2.objects.filters.ObjectFilters;
import org.loose.fis.sre.services.PropertyService;
import static org.loose.fis.sre.services.FileSystemService.getPathToFile;

public class BookingRequestService {

    private static ObjectRepository<BookingRequest> BookingRequestRepository;
    public static void initDatabase(){
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("SimpleBNBbookingrequest.db").toFile())
                .openOrCreate("SimpleBNBbookingrequest", "SimpleBNBbookingrequest");
        BookingRequestRepository= database.getRepository(BookingRequest.class);
    }
    public static void addBookingRequest(BookingRequest b){
        BookingRequestRepository.insert(b);
    }
    public static int getLastId(){
        int id=0;
        for(BookingRequest bookingRequest : BookingRequestRepository.find())
            id= bookingRequest.getId();
        return id;
    }


}
