package mft.library.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import mft.library.model.entity.enums.FormState;

import java.net.URL;
import java.util.ResourceBundle;

public class MainBimehController implements Initializable {

    @FXML
    private MenuItem newBimehMnu, editBimehMnu, removeBimehMnu, findBimehMnu;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newBimehMnu.setOnAction(e -> {
            BimehViewer.selectedBimeh= FormState.New;
            showBimehForm();
        });

        editBimehMnu.setOnAction(e -> {
            BimehViewer.bimehFormState = FormState.Edit;
            showBimehForm();
        });

        removeBimehMnu.setOnAction(e -> {
            BimehViewer.bimehFormState = FormState.Remove;
            showBimehForm();
        });
        findBimehMnu.setOnAction(e -> {
            BimehViewer.bimehFormState = FormState.Find;
            showBimehForm();
        });

    }

    public void showBimehForm() {
        try {
            BimehViewer bimehViewer = new BimehViewer();
            bimehViewer.showBimehForm();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
