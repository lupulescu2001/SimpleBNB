package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
public class OpenPropertiesController {
    private String username;
    public void setUsername(String username) {
        this.username = username;
    }
    public void handleAddPropertyAction() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/openAddProperty.fxml"));
            String username = this.username;
            root = loader.load();
            AddPropertyController addPropertyController = loader.getController();
            addPropertyController.setUsername(username);
            //root = FXMLLoader.load(getClass().getClassLoader().getResource("openAddProperty.fxml"));
            Stage stage = new Stage();
            stage.setTitle("SimpleBNB Property Page");
            stage.setScene(new Scene(root, 600, 575));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

