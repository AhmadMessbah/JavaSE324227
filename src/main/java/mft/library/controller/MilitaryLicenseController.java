package mft.library.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j;
import mft.library.MilitaryLicenseApp;
import mft.library.model.entity.MilitaryLicense;
import mft.library.model.entity.Person;
import mft.library.model.entity.enums.FormState;
import mft.library.model.entity.enums.MilitaryType;
import mft.library.model.entity.enums.Province;
import mft.library.model.service.MilitaryLicenseService;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Log4j
public class MilitaryLicenseController implements Initializable {
    @FXML
    private TextField id, militaryId, person;

    @FXML
    private Button saveBtn, updateBtn, deleteBtn, refreshBtn, addPersonBtn;

    @FXML
    private DatePicker startDate, endDate;

    @FXML
    private ComboBox<Province> provinceComboBox; //Enum class

    @FXML
    private ComboBox<MilitaryType> militaryTypeComboBox; //Enum class

    @FXML
    private TableView<MilitaryLicense> militaryLicenseTable;

    @FXML
    private TableColumn<MilitaryLicense, Integer> idCol, militaryIdCol;

    @FXML
    private TableColumn<MilitaryLicense, String> firstNameCol, lastNameCol;

    MilitaryLicenseService militaryLicenseService = new MilitaryLicenseService();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (FormViewer.militaryFormState.equals(FormState.New)) {
            updateBtn.setDisable(true);
            deleteBtn.setDisable(true);
            refreshBtn.setVisible(false);
            log.info("Save Mode");
        } else if (FormViewer.militaryFormState.equals(FormState.Edit)) {
            saveBtn.setDisable(true);
            deleteBtn.setDisable(true);
            addPersonBtn.setDisable(true);
            fillFieldsByMouseClick();
            log.info("Edit Mode");
        } else if (FormViewer.militaryFormState.equals(FormState.Remove)) {
            saveBtn.setDisable(true);
            updateBtn.setDisable(true);
            addPersonBtn.setDisable(true);
            fillFieldsByMouseClick();
            log.info("Remove Mode");
        } else if (FormViewer.militaryFormState.equals(FormState.Find)) {
            saveBtn.setVisible(false);
            updateBtn.setVisible(false);
            deleteBtn.setVisible(false);
            refreshBtn.setVisible(false);
            militaryLicenseTable.setLayoutX(13);
            militaryLicenseTable.setLayoutY(23);
            militaryLicenseTable.setPrefWidth(860);
            militaryLicenseTable.setPrefHeight(515);
            log.info("Find Mode");
        }

        log.info("View initialized");

        provinceComboBox.setItems(FXCollections.observableArrayList(Province.values()));
        provinceComboBox.setPromptText("Select Province");
        militaryTypeComboBox.setItems(FXCollections.observableArrayList(MilitaryType.values()));
        militaryTypeComboBox.setPromptText("Select Military Type");
        resetForm();

        saveBtn.setOnMouseEntered(event -> saveBtn.setStyle("-fx-background-color: #00ff00;"));
        saveBtn.setOnMouseExited(event -> saveBtn.setStyle("-fx-background-color: #00aa00;"));
        saveBtn.setOnMousePressed(event -> {
            saveBtn.setStyle("-fx-background-color: #008800;");
        });

        updateBtn.setOnMouseEntered(event -> updateBtn.setStyle("-fx-background-color: #0066ff;"));
        updateBtn.setOnMouseExited(event -> updateBtn.setStyle("-fx-background-color: #0044ff;"));
        updateBtn.setOnMousePressed(event -> {
            updateBtn.setStyle("-fx-background-color: #0044aa;");
        });

        deleteBtn.setOnMouseEntered(event -> deleteBtn.setStyle("-fx-background-color: #ff0000;"));
        deleteBtn.setOnMouseExited(event -> deleteBtn.setStyle("-fx-background-color: #dd0000;"));
        deleteBtn.setOnMousePressed(event -> {
            deleteBtn.setStyle("-fx-background-color: #aa0000;");
        });

        saveBtn.setOnAction(event -> {
            try {
                MilitaryLicense militaryLicense = MilitaryLicense
                        .builder()
                        .militaryId(Integer.parseInt(militaryId.getText()))
                        .startMilitaryDate(startDate.getValue())
                        .endMilitaryDate(endDate.getValue())
                        .type(militaryTypeComboBox.getValue())
                        .province(provinceComboBox.getValue())
                        .person(Person.builder().id(Integer.parseInt(person.getText())).build())
                        .build();
                militaryLicenseService.save(militaryLicense);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "New License Saved ", ButtonType.OK);
                alert.show();
                resetForm();
                log.info("License Saved : " + militaryLicense);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Saving Process Failed", ButtonType.OK);
                alert.show();
                log.error(e);
            }
        });
        updateBtn.setOnAction(event -> {
            try {
                MilitaryLicense militaryLicense = MilitaryLicense
                        .builder()
                        .id(Integer.parseInt(id.getText()))
                        .startMilitaryDate(startDate.getValue())
                        .endMilitaryDate(endDate.getValue())
                        .province(provinceComboBox.getValue())
                        .type(militaryTypeComboBox.getValue())
                        .person(Person.builder().id(Integer.parseInt(person.getText())).build())
                        .build();
                militaryLicenseService.edit(militaryLicense);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "License Updated", ButtonType.OK);
                alert.show();
                resetForm();
                log.info("License Updated : " + militaryLicense);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Updating Process Failed");
                alert.show();
                log.error(e);
            }
        });
        deleteBtn.setOnAction(event -> {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wanna delete?", ButtonType.YES, ButtonType.NO);
            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    try {
                        militaryLicenseService.remove(Integer.parseInt(id.getText()));
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "License Deleted", ButtonType.OK);
                        alert.show();
                        resetForm();
                        log.info("License Deleted");
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Deleting Process Failed");
                        alert.show();
                        log.error(e);
                    }
                }
            });
        });

        /*nameSearchTxt.setOnKeyReleased(event -> {
            try {
                refreshTable(MemberService.findByNameAndFamily(nameSearchTxt.getText(), familySearchTxt.getText()));
            } catch (Exception e) {
                log.error(e);
            }
        });
        familySearchTxt.setOnKeyReleased(event -> {
            try {
                refreshTable(MemberService.findByNameAndFamily(nameSearchTxt.getText(), familySearchTxt.getText()));
            } catch (Exception e) {
                log.error(e);
            }
        });*/

        refreshBtn.setOnAction(event -> {
            resetForm();
        });

        addPersonBtn.setOnAction(event -> {
            try {
                MilitaryLicenseApp.showPersonModal();
                PersonModalController personModalController = new PersonModalController();
                personModalController.loadPersonData();
            } catch (Exception e) {
                log.error(e);
            }
        });
    }

    private void resetForm() {
        id.clear();
        militaryId.clear();
        person.clear();
        startDate.setValue(null);
        endDate.setValue(null);
        provinceComboBox.setValue(null);
        militaryTypeComboBox.setValue(null);
        try {
            refreshTable(militaryLicenseService.findAll());
        } catch (Exception e) {
            log.error(e);
        }
    }

    private void refreshTable(List<MilitaryLicense> militaryLicenseList) {
        ObservableList<MilitaryLicense> militaryLicenseObservableList = FXCollections.observableArrayList(militaryLicenseList);
        idCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        militaryIdCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMilitaryId()));
        firstNameCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPerson().getName()));
        lastNameCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPerson().getFamily()));

        militaryLicenseTable.setItems(militaryLicenseObservableList);
    }

    public void fillPersonField(Person selectedPerson) {
        System.out.println(selectedPerson);
        System.out.println(selectedPerson.getName().concat(selectedPerson.getFamily()));
        System.out.println("Trying to set text...");
        Platform.runLater(() -> {
            if (person != null) {
                person.setText("Test Value");
                System.out.println("Text Set Successfully!");
            } else {
                System.out.println("person is null!");
            }
        });
//        person.setText(((selectedPerson.getName() + " "))
//                .concat(String.valueOf(selectedPerson.getFamily())));
    }

    private void fillFieldsByMouseClick() {
        militaryLicenseTable.setOnMouseReleased(event -> {
            MilitaryLicense militaryLicense = militaryLicenseTable.getSelectionModel().getSelectedItem();
            if (militaryLicense != null) {
                id.setText(String.valueOf(militaryLicense.getId()));
                militaryId.setText(String.valueOf(militaryLicense.getMilitaryId()));
                startDate.setValue(militaryLicense.getStartMilitaryDate());
                endDate.setValue(militaryLicense.getEndMilitaryDate());
                provinceComboBox.getSelectionModel().select(militaryLicense.getProvince());
                militaryTypeComboBox.getSelectionModel().select(militaryLicense.getType());
                person.setText(((militaryLicense.getPerson().getName() + " "))
                        .concat(String.valueOf(militaryLicense.getPerson().getFamily())));
                FormViewer.selectedMilitaryLicense = militaryLicense;
            }
        });
    }
}