package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.List;

import org.loose.fis.sre.exceptions.IncorrectScoreException;
import org.loose.fis.sre.exceptions.PropertyDoesNotExistException;
import org.loose.fis.sre.model.Property;
import org.loose.fis.sre.services.PropertyService;
import java.util.*;

public class PastReservationsController {

    String clientUsername;

    @FXML
    private ListView<String> listView;
    @FXML
    private TextField ReviewedPropName;

    @FXML private Text Message;

    @FXML
    private TextField Review;

    public void setclientusername(String s)
    {
        clientUsername=s;
    }

    public void setPastReservations (List<String> list){
        listView.getItems().addAll(list);
    }

    @FXML
    public void handleClickLeaveReviewAction(){
        try{
            if(Objects.equals(clientUsername,null))
                clientUsername="USERNAME";
            PropertyService.addReview(ReviewedPropName.getText(),Float.parseFloat(Review.getText()),clientUsername);
            Property prop= PropertyService.getPropertyByName(ReviewedPropName.getText());
            Message.setText("New review : " + prop.getReview());
        }
        catch(NumberFormatException e) {
            Message.setText("Please introduce a number");
        }
        catch (IncorrectScoreException e){
            Message.setText(e.getMessage());}
        catch (PropertyDoesNotExistException e){
            Message.setText(e.getMessage());
        }


    }






}
