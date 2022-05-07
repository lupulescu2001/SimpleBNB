package org.loose.fis.sre.controllers;
import java.util.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.loose.fis.sre.exceptions.IncorrectDateException;
import org.loose.fis.sre.exceptions.NoPropertyAvailable;
import org.loose.fis.sre.exceptions.NoPropertyInThisCity;
import org.loose.fis.sre.services.PropertyService;
import org.loose.fis.sre.services.PropertyUnavailableService;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.loose.fis.sre.exceptions.IncorrectCredentials;
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.services.UserService;
import java.io.IOException;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class SearchAccommodationController{

    @FXML
    private TextField citynameField;

    @FXML
    private TextField checkindayField;

    @FXML
    private TextField checkinmonthField;

    @FXML
    private TextField checkinyearField;

    @FXML
    private TextField checkoutdayField;

    @FXML
    private TextField checkoutmonthField;

    @FXML
    private TextField checkoutyearField;

    @FXML
    private Text message;
    private String clientUsername;

    public void setClientUsername(String s)
    {
        clientUsername=s;
    }




    @FXML
    public void handleClickSearch(){
        try{
            List<String> possible_prop = new ArrayList<>();
            possible_prop=PropertyUnavailableService.searchByCity(citynameField.getText());
            possible_prop=PropertyUnavailableService.searchbByDate(possible_prop,checkindayField.getText(),checkinmonthField.getText(),checkinyearField.getText(),checkoutdayField.getText(),checkoutmonthField.getText(),checkoutyearField.getText());
          //  message.setText(String.format("%d accommodations found",possible_prop.size()));

            try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/availableAccommodations.fxml"));
            Parent root;
            root = (Parent) loader.load();
            AvailableAccommodationsController availableAccommodationsController = loader.getController();
            availableAccommodationsController.setAvailableList(PropertyService.getAllPropertiesByName(possible_prop));
            availableAccommodationsController.setClientUsername(clientUsername);
            availableAccommodationsController.setAvailableNames(possible_prop);
            availableAccommodationsController.setCheckin(checkindayField.getText(),checkinmonthField.getText(),checkinyearField.getText());
            availableAccommodationsController.setCheckout(checkoutdayField.getText(),checkoutmonthField.getText(),checkoutyearField.getText());
            Stage stage=new Stage();
            stage.setTitle("SimpleBNB");
            stage.setScene(new Scene(root,600,575));
            stage.show();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }

        }
        catch(NoPropertyInThisCity e){
            message.setText(e.getMessage());
        }
        catch(IncorrectDateException e){
            message.setText(e.getMessage());
        }
        catch(NoPropertyAvailable e){
            message.setText(e.getMessage());
        }



    }

}