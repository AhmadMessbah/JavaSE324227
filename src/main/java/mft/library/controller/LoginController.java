package mft.library.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import mft.library.model.entity.Person;
import mft.library.model.service.PersonService;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField userTxt;
    @FXML
    private TextField passTxt;
    @FXML
    private Button loginBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginBtn.setOnAction(event -> {
            try {
                FormViewer.loggedInPerson = PersonService.findByUsernameAndPassword(userTxt.getText(), passTxt.getText());;
                FormViewer formViewer = new FormViewer();
                formViewer.showMainPanel();

                loginBtn.getScene().getWindow().hide();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Login Error\n" + e.getMessage());
                alert.show();
            }
        });
    }
}
