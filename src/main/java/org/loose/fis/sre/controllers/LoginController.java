package org.loose.fis.sre.controllers;

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

public class LoginController {

    @FXML
    private Text loginMessage;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private ChoiceBox role;


    @FXML
    public void initialize() {
        role.getItems().addAll("Client", "Owner");
    }

    @FXML
    public void handleLoginAction() {
        try {
            UserService.CheckUserCredentials(usernameField.getText(), passwordField.getText(), (String) role.getValue());
            loginMessage.setText("Login successfully!");
            Parent root;
            String roleValue = (String) role.getValue();
            try {
                if (roleValue == "Owner") {
                    //root = FXMLLoader.load(getClass().getClassLoader().getResource("openOwner.fxml"));
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/openOwner.fxml"));
                    String username = usernameField.getText();
                    root = (Parent) loader.load();
                    OpenOwnerController openOwnerController = loader.getController();
                    openOwnerController.setUsername(username);
                }
                else
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
        } catch (IncorrectCredentials e) {
            loginMessage.setText(e.getMessage());
        }
    }
}

