package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;

public class OpenClientController {

    private String clientUsername;

    public void setClientUsername(String s){
        clientUsername=s;
    }

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

}

