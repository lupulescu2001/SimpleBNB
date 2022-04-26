package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class OpenOwnerController {
    private String username;
    public void setUsername(String username) {
        this.username = username;
    }
    public void handleClickPropertiesAction() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/openProperties.fxml"));
            root = loader.load();
            OpenPropertiesController openPropertiesController = loader.getController();
            openPropertiesController.setUsername(username);
            //root = FXMLLoader.load(getClass().getClassLoader().getResource("openProperties.fxml"));
            Stage stage=new Stage();
            stage.setTitle("SimpleBNB Owner Page");
            stage.setScene(new Scene(root,600,575));
            stage.show();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
