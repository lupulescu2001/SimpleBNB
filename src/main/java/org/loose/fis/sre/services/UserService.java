package org.loose.fis.sre.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.model.User;
import org.loose.fis.sre.exceptions.IncorrectCredentials;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.List;
import org.loose.fis.sre.exceptions.MarkIsIncorrectException;
import org.loose.fis.sre.exceptions.ClientDoesNotHavePastReservationsException;
import org.loose.fis.sre.services.BookingRequestService;
import org.loose.fis.sre.services.FileSystemService;

public class UserService {

    private static ObjectRepository<User> userRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(FileSystemService.getPathToFile("SimpleBNB1.db").toFile())
                .openOrCreate("SimpleBNB1", "SimpleBNB1");

        userRepository = database.getRepository(User.class);
    }
    public static String getRoleByUsername(String username) throws IncorrectCredentials{
        String role= new String();
        int ok=0;
        for(User user:userRepository.find()){
            if(Objects.equals(user.getUsername(),username)){
                role=user.getRole();
                ok=1;}
        }
        if(ok==0)
            throw new IncorrectCredentials(username);

        return role;
    }

    public static void addUser(String fullName,String phoneNumber,String username, String password, String role) throws UsernameAlreadyExistsException {
        checkUserDoesNotAlreadyExist(username);
        userRepository.insert(new User(fullName,phoneNumber,username, encodePassword(username, password), role));
    }
    public static List<User> getAllUsers(){
        return userRepository.find().toList();
    }

    private static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername()))
                throw new UsernameAlreadyExistsException(username);
        }
    }

    public static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }

    public static int CheckUserCredentials(String username, String password, String role) throws IncorrectCredentials
    {
        String pass=encodePassword(username,password);
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername()) && Objects.equals(role,user.getRole()) && pass.equals(user.getPassword()))
            {
                if(role.equals("Client"))
                {
                    return 1;
                }
                else
                {
                    return 2;
                }
            }
        }
        throw new IncorrectCredentials(username);
    }
    public static void addReview(String username, String clientUsername, String mark) throws MarkIsIncorrectException, ClientDoesNotHavePastReservationsException {
        int markInt;
        try {
            markInt = Integer.parseInt(mark);
        } catch (NumberFormatException e) {
            throw new MarkIsIncorrectException();
        }
        int ok = 0;
        if (markInt < 1 || markInt > 10)
            throw new MarkIsIncorrectException();
        for (String bookingUsername : BookingRequestService.getAllPastClients(username))
            if (Objects.equals(bookingUsername, clientUsername))
                ok = 1;
        if (ok == 0)
            throw new ClientDoesNotHavePastReservationsException(clientUsername);
        for (User user : userRepository.find())
            if (Objects.equals(user.getUsername(), clientUsername)) {
                user.setMark(user.getMark() + markInt);
                user.setReviews(user.getReviews() + 1);
                userRepository.update(user);
            }
    }
}

