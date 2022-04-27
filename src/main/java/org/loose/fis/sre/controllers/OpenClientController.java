package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;

public class OpenClientController {
    @FXML
    public void handleClickSearchAccommodationAction(){

        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("searchAccommodation.fxml"));
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

