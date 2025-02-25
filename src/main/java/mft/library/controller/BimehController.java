package com.bimeh.controller;

import com.bimeh.model.entity.InsuranceStatus;
import com.bimeh.model.entity.InsuranceType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import com.bimeh.model.entity.Bimeh;
import com.bimeh.model.entity.Person;
import com.bimeh.model.service.BimehService;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class BimehController implements Initializable {

    @FXML
    private TextField policyNumberTxt;

    @FXML
    private ComboBox<String> insuranceTypeCombo;

    @FXML
    private DatePicker startDatePicker, endDatePicker;

    @FXML
    private TextField personTxt;

    @FXML
    private ComboBox<String> statusCombo;

    @FXML
    private Button saveBtn, editBtn, removeBtn;

    @FXML
    private TableView<Bimeh> bimehTable;

    @FXML
    private TableColumn<Bimeh, String> policyNumberCol;
    @FXML
    private TableColumn<Bimeh, String> personCol;
    @FXML
    private TableColumn<Bimeh, String> statusCol;

    private Bimeh selectedBimeh;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        insuranceTypeCombo.setItems(FXCollections.observableArrayList("CAR", "LIFE", "HEALTH", "PROPERTY"));
        statusCombo.setItems(FXCollections.observableArrayList("ACTIVE", "EXPIRED", "CANCELLED"));
        resetForm();

        saveBtn.setOnAction(event -> {
            try {
                String personName = personTxt.getText();
                Person person = new Person();
                person.setId(1);
                person.setName(personName);
                person.setFamily("Default Family");
                person.setBirthDate(LocalDate.now());
                person.setUsername("default_username");
                person.setPassword("default_password");
                person.setActive(true);

                Bimeh bimeh = Bimeh.builder()
                        .policyNumber(policyNumberTxt.getText())
                        .insuranceType(InsuranceType.valueOf(insuranceTypeCombo.getSelectionModel().getSelectedItem()))
                        .startDate(startDatePicker.getValue())
                        .endDate(endDatePicker.getValue())
                        .person(person)
                        .status(InsuranceStatus.valueOf(statusCombo.getSelectionModel().getSelectedItem()))
                        .build();
                System.out.println(bimeh);

                BimehService.save(bimeh);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "بیمه ذخیره شد: " + bimeh, ButtonType.OK);
                alert.show();
                resetForm();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
        });

        editBtn.setOnAction(event -> {
            try {
                if (selectedBimeh != null) {
                    String personName = "ftmjbr";
                    Person person = new Person();
                    person.setName(personName);
                    person.setFamily("Default Family");
                    person.setBirthDate(LocalDate.now());
                    person.setUsername("default_username");
                    person.setPassword("default_password");
                    person.setActive(true);

                    Bimeh bimeh = Bimeh.builder()
                            .id(selectedBimeh.getId())
                            .policyNumber(policyNumberTxt.getText())
                            .insuranceType(InsuranceType.valueOf(insuranceTypeCombo.getSelectionModel().getSelectedItem()))
                            .startDate(startDatePicker.getValue())
                            .endDate(endDatePicker.getValue())
                            .person(person)
                            .status(InsuranceStatus.valueOf(statusCombo.getSelectionModel().getSelectedItem()))
                            .build();

                    BimehService.edit(bimeh);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "بیمه ویرایش شد", ButtonType.OK);
                    alert.show();
                    resetForm();
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
        });

        removeBtn.setOnAction(event -> {
            try {
                if (selectedBimeh != null) {
                    BimehService.remove(selectedBimeh.getId());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "بیمه حذف شد", ButtonType.OK);
                    alert.show();
                    resetForm();
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
        });

        bimehTable.setOnMouseReleased(event -> {
            selectedBimeh = bimehTable.getSelectionModel().getSelectedItem();
            if (selectedBimeh != null) {
                policyNumberTxt.setText(selectedBimeh.getPolicyNumber());
                insuranceTypeCombo.getSelectionModel().select(String.valueOf(selectedBimeh.getInsuranceType()));
                startDatePicker.setValue(selectedBimeh.getStartDate());
                endDatePicker.setValue(selectedBimeh.getEndDate());
                personTxt.setText(selectedBimeh.getPerson() != null ? selectedBimeh.getPerson().getName() : "No person");
                statusCombo.getSelectionModel().select(String.valueOf(selectedBimeh.getStatus()));
            }
        });
    }

    private void resetForm() {
        policyNumberTxt.clear();
        insuranceTypeCombo.getSelectionModel().clearSelection();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        personTxt.clear();
        statusCombo.getSelectionModel().clearSelection();
        selectedBimeh = null;
        try {
            System.out.println(BimehService.findAll());
            refreshTable(BimehService.findAll());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
    }

    private void refreshTable(List<Bimeh> bimehList) {
        ObservableList<Bimeh> bimehObservableList = FXCollections.observableArrayList(bimehList);
        bimehTable.getItems().clear();

        policyNumberCol.setCellValueFactory(new PropertyValueFactory<>("policyNumber"));
        personCol.setCellValueFactory(cellData -> {
            Person person = cellData.getValue().getPerson();
            return person != null ? new SimpleStringProperty(person.getName()) : new SimpleStringProperty("No person");
        });
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        bimehTable.setItems(bimehObservableList);
    }
}
