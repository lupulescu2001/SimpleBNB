package org.loose.fis.sre.controllers;
import java.io.IOException;
import java.util.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.loose.fis.sre.model.BookingRequest;
import org.loose.fis.sre.services.BookingRequestService;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.loose.fis.sre.model.Property;
import org.loose.fis.sre.services.PropertyService;

public class AvailableAccommodationsController {

    @FXML
    private ListView<String> listview;

    @FXML
    private TextField propertynameField;

    @FXML
    private Text message;

    private String checkinday;
    private String checkinmonth;
    private String checkinyear;
    private String checkoutday;
    private String checkoutmonth;
    private String checkoutyear;
    private String clientUsername;

    private List<String> avail_names=new ArrayList<>();

    public void setAvailableNames(List<String> list){avail_names=list;}
    public void setAvailableList(List<String> list){
        listview.getItems().addAll(list);
    }
    public void setCheckin(String day,String month,String year){
        checkinday=day;
        checkinmonth=month;
        checkinyear=year;

    }
    public void setCheckout(String day,String month,String year){
        checkoutday=day;
        checkoutmonth=month;
        checkoutyear=year;

    }
    public void setClientUsername(String s){
        clientUsername=s;
    }

    public void handleBookAction(){
        int ok=0;
        for(String s:avail_names)
            if(Objects.equals(propertynameField.getText(),s))
                ok=1;
        if(ok==1) {
            Property p = PropertyService.getPropertyByName(propertynameField.getText());
            BookingRequest newbook = new BookingRequest(BookingRequestService.getLastId() + 1, clientUsername, propertynameField.getText(), checkinday, checkinmonth, checkinyear, checkoutday, checkoutmonth, checkoutyear, 0);
            BookingRequestService.addBookingRequest(newbook);
            message.setText(String.format("Booking request sent from %s", clientUsername));
        }
        else{
            message.setText(String.format("Name of the property incorrect"));
        }
    }
    public void handleHomeAction(){
        try {
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/openClient.fxml"));
            root = (Parent) loader.load();
            OpenClientController openClientController = loader.getController();
            openClientController.setClientUsername(clientUsername);
            Stage stage = new Stage();
            stage.setTitle("SimpleBNB");
            stage.setScene(new Scene(root, 600, 575));
            stage.show();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }


}
