package mft.library.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j;
import mft.library.model.entity.DriverLicense;
import mft.library.model.entity.MilitaryLicense;
import mft.library.model.entity.Person;
import mft.library.model.entity.enums.FormState;
import mft.library.model.entity.enums.MilitaryType;

import java.io.IOException;

@Log4j
public class FormViewer {
    public static FormState personFormState = FormState.New;
    public static FormState militaryFormState = FormState.New;
    public static FormState driverLicenseFormState = FormState.New;
    public static Person loggedInPerson, selectedPerson;
    public static FormState jobHistoryFormState;
    public static Person selectedJobHistory;
    public static MilitaryLicense selectedMilitaryLicense;
    public static DriverLicense selectedDriverLicense;

    public void showForm(String formName, String title) throws IOException {
        log.info(formName +"Form Starting");
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/javafx/" + formName + "View.fxml")));
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }
}
