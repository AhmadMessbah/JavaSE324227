package mft.library.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import mft.library.model.entity.enums.FormStateEmployment;

import java.net.URL;
import java.util.ResourceBundle;

public class MainEmploymentController implements Initializable{
    @FXML
    private MenuItem newEmploymentMnu, editEmploymentMnu, removeEmploymentMnu, findEmploymentMnu;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newEmploymentMnu.setOnAction(e -> {
            FormViewerEmployment.personFormStateEmployment = FormStateEmployment.New;
            showPersonForm();
        });

        editEmploymentMnu.setOnAction(e -> {
            FormViewerEmployment.personFormStateEmployment = FormStateEmployment.Edit;
            showPersonForm();
        });

        removeEmploymentMnu.setOnAction(e -> {
            FormViewerEmployment.personFormStateEmployment = FormStateEmployment.New.Remove;
            showPersonForm();
        });
        findEmploymentMnu.setOnAction(e -> {
            FormViewerEmployment.personFormStateEmployment = FormStateEmployment.New.Find;
            showPersonForm();
        });

    }

    public void showPersonForm() {
        try {
            FormViewerEmployment formViewerEmployment = new FormViewerEmployment();
            formViewerEmployment.showPersonForm();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
