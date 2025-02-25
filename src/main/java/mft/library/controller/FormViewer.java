package mft.library.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j;
import mft.library.model.entity.DriverLicense;
import mft.library.model.entity.Person;
import mft.library.model.entity.enums.FormState;

@Log4j
public class FormViewer {
    public static FormState personFormState;
    public static FormState DriverLicenseFormState;
    public static Person selectedPerson;
    public static DriverLicense selectedDriverLicense;

    public void showPersonForm() throws Exception {
        log.info("Starting Application");
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/javafx/personView.fxml")));
        stage.setScene(scene);
        stage.setTitle("Member Info");
        stage.show();
    }
    public void showDriverLicenseForm() throws Exception {
        log.info("Starting Application");
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/javafx/driverLicenseView.fxml")));
        stage.setScene(scene);
        stage.setTitle("showDriverLicenseForm");
        stage.show();
    }
}
