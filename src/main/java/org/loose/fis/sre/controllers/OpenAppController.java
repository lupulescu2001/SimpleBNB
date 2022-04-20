package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;

public class OpenAppController{


    @FXML
    public void handleClickRegisterAction() {


            Parent root;
            try {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
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
    public void handleClickLoginAction() {


        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
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


