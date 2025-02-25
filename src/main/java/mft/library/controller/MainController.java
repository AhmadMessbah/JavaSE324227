package mft.library.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import mft.library.model.entity.enums.FormState;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private MenuItem newPersonMnu, editPersonMnu, removePersonMnu, findPersonMnu;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newPersonMnu.setOnAction(e -> {
            FormViewer.personFormState = FormState.New;
            showPersonForm();
        });

        editPersonMnu.setOnAction(e -> {
            FormViewer.personFormState = FormState.Edit;
            showPersonForm();
        });

        removePersonMnu.setOnAction(e -> {
            FormViewer.personFormState = FormState.Remove;
            showPersonForm();
        });
        findPersonMnu.setOnAction(e -> {
            FormViewer.personFormState = FormState.Find;
            showPersonForm();
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
}
