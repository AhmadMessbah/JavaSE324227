package mft.library.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import mft.library.model.entity.enums.FormState;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private MenuItem newPersonMnu, editPersonMnu, removePersonMnu, findPersonMnu,
            newDriverLicenseMnu, editDriverLicenseMnu, removeDriverLicenseMnu, findDriverLicenseMnu;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newPersonMnu.setOnAction(e -> {
            FormViewer.personFormState = FormState.New;
            showPersonForm();
        });
        newDriverLicenseMnu.setOnAction(e -> {
            FormViewer.DriverLicenseFormState = FormState.New;
            showDriverLicenseForm();
        });

        editPersonMnu.setOnAction(e -> {
            FormViewer.personFormState = FormState.Edit;
            showPersonForm();
        });
        editDriverLicenseMnu.setOnAction(e -> {
            FormViewer.DriverLicenseFormState = FormState.Edit;
            showDriverLicenseForm();
        });

        removePersonMnu.setOnAction(e -> {
            FormViewer.personFormState = FormState.Remove;
            showPersonForm();
        });
        removeDriverLicenseMnu.setOnAction(e -> {
            FormViewer.DriverLicenseFormState = FormState.Remove;
            showDriverLicenseForm();
        });


        findPersonMnu.setOnAction(e -> {
            FormViewer.personFormState = FormState.Find;
            showPersonForm();
        });
        findDriverLicenseMnu.setOnAction(e -> {
            FormViewer.DriverLicenseFormState = FormState.Find;
            showDriverLicenseForm();
        });

    }

    public void showPersonForm() {
        try {
            FormViewer formViewer = new FormViewer();
            formViewer.showPersonForm();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void showDriverLicenseForm() {
        try {
            FormViewer formViewer = new FormViewer();
            formViewer.showDriverLicenseForm();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
