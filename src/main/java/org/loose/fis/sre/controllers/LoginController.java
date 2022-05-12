package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.loose.fis.sre.exceptions.IncorrectCredentials;
import org.loose.fis.sre.services.UserService;
import java.io.IOException;
import java.util.Objects;

public class LoginController {

    @FXML
    private Text loginMessage;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;

    private String role=new String();


  //  @FXML
   // public void initialize() {
     //   role.getItems().addAll("Client", "Owner");
   // }

    @FXML
    public void handleLoginAction() {
        try {
            role=UserService.getRoleByUsername(usernameField.getText());
            UserService.CheckUserCredentials(usernameField.getText(), passwordField.getText(), (String) role);
            loginMessage.setText("Login successfully!");
            Parent root;

           // String roleValue =  role;
            String Owner = new String();
            Owner="Owner";

            try {
                if (Objects.equals(role,Owner)) {
                    //root = FXMLLoader.load(getClass().getClassLoader().getResource("openOwner.fxml"));
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/openOwner.fxml"));
                    String username = usernameField.getText();
                    root = (Parent) loader.load();
                    OpenOwnerController openOwnerController = loader.getController();
                    openOwnerController.setUsername(username);
                }
                else
                { FXMLLoader loader = new FXMLLoader(getClass().getResource("/openClient.fxml"));
                root = (Parent) loader.load();
                OpenClientController openClientController= loader.getController();
                openClientController.setClientUsername(usernameField.getText());

                }
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

