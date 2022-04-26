package org.loose.fis.sre.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.loose.fis.sre.model.Property;
import javafx.scene.control.ListView;
import java.net.URL;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.loose.fis.sre.services.PropertyService;

import java.io.IOException;
import java.util.ResourceBundle;

public class OpenPropertiesController {
    private String current;
    @FXML
    private ListView<String> listView;
    private String username;
    public void setUsername(String username) {
        this.username = username;
        listView.getItems().addAll(PropertyService.getAllProperties(username));
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
    public void handleChangeDescriptionAction() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/changeDescription.fxml"));
            String username = this.username;
            root = loader.load();
            ChangeDescriptionController changeDescriptionController = loader.getController();
            changeDescriptionController.setUsername(username);
            //root = FXMLLoader.load(getClass().getClassLoader().getResource("openAddProperty.fxml"));
            Stage stage = new Stage();
            stage.setTitle("SimpleBNB Property Page");
            stage.setScene(new Scene(root, 600, 575));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleDeletePropertyAction() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/deleteProperty.fxml"));
            String username = this.username;
            root = loader.load();
            DeletePropertyController deletePropertyController = loader.getController();
            deletePropertyController.setUsername(username);
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

