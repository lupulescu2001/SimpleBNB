package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;

import javafx.stage.Stage;
import org.loose.fis.sre.services.BookingRequestService;

import java.io.IOException;

public class OpenClientController {

    private String clientUsername;

    public void setClientUsername(String s){
        clientUsername=s;
    }

    @FXML
    private Text errormessage;

    @FXML
    public void handleClickSearchAccommodationAction(){


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/searchAccommodation.fxml"));
            Parent root;
            root = (Parent) loader.load();
            SearchAccommodationController searchAccommodationController=loader.getController();
            searchAccommodationController.setClientUsername(clientUsername);

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
    @FXML
    public void handleseeupcomingreservations(){

        try{
        List<String> up = new ArrayList<>();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/upcomingReservations.fxml"));
        Parent root;
        root = (Parent) loader.load();
        up=BookingRequestService.getAllUpcomingRequestsForUser(clientUsername);
        //errormessage.setText(String.format(clientUsername));
        UpcomingReservationsController upcomingReservationsController =loader.getController();
        upcomingReservationsController.setUpcomingReservations(up);





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
    @FXML
    public void handleseepastreservations(){
        Parent root;
        try{
            List<String> up = new ArrayList<>();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pastReservations.fxml"));

           // errormessage.setText(String.format("crapa"));
            root = (Parent)loader.load();
            //  errormessage.setText(String.format("crapa"));
            up=BookingRequestService.getAllPastRequestsForUser(clientUsername);
           //errormessage.setText(String.format(clientUsername));
            PastReservationsController pastReservationsController =loader.getController();
            pastReservationsController.setPastReservations(up);
            pastReservationsController.setclientusername(clientUsername);


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



}

