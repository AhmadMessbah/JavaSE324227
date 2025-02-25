package mft.library.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import mft.library.model.entity.enums.FormState;

import java.net.URL;
import java.util.ResourceBundle;

public class MainJobHistoryController implements Initializable {
    @FXML
    private MenuItem newJobMnu, editJobMnu, removeJobMnu, findJobMnu;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newJobMnu.setOnAction(e -> {
            FormViewer.jobHistoryFormState = FormState.New;
            showJobHistoryForm();
        });

        editJobMnu.setOnAction(e -> {
            FormViewer.jobHistoryFormState = FormState.Edit;
            showJobHistoryForm();
        });

        removeJobMnu.setOnAction(e -> {
            FormViewer.jobHistoryFormState = FormState.Remove;
            showJobHistoryForm();
        });
        findJobMnu.setOnAction(e -> {
            FormViewer.jobHistoryFormState = FormState.Find;
            showJobHistoryForm();
        });

    }

    public void showJobHistoryForm() {
        try {
            FormViewer formViewer = new FormViewer();
            formViewer.showJobHistoryForm();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    }
