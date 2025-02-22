package mft.library.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j;
import mft.library.model.entity.MilitaryLicenseEntity;
import mft.library.model.entity.enums.MilitaryType;
import mft.library.model.entity.enums.Province;
import mft.library.model.service.MilitaryLicenseService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Log4j
public class MilitaryLicenseController implements Initializable {
    @FXML
    private TextField id, militaryId, firstName, lastName;

    @FXML
    private Button saveBtn, updateBtn, deleteBtn, refreshBtn;

    @FXML
    private DatePicker startDate, endDate;

    @FXML
    private ComboBox<Province> provinceComboBox; //Enum class

    @FXML
    private ComboBox<MilitaryType> militaryTypeComboBox; //Enum class

    @FXML
    private TableView<MilitaryLicenseEntity> militaryLicenseTable;

    @FXML
    private TableColumn<MilitaryLicenseEntity, Integer> idCol, militaryIdCol;

    @FXML
    private TableColumn<MilitaryLicenseEntity, String> firstNameCol, lastNameCol;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("View initialized");

        provinceComboBox.setItems(FXCollections.observableArrayList(Province.values()));
        provinceComboBox.setPromptText("Select Province");
        militaryTypeComboBox.setItems(FXCollections.observableArrayList(MilitaryType.values()));
        militaryTypeComboBox.setPromptText("Select Military Type");
        resetForm();

        saveBtn.setOnMouseEntered(event -> saveBtn.setStyle("-fx-background-color: #00ff00;"));
        saveBtn.setOnMouseExited(event -> saveBtn.setStyle("-fx-background-color: #00aa00;"));
        saveBtn.setOnMousePressed(event -> {saveBtn.setStyle("-fx-background-color: #008800;");});

        updateBtn.setOnMouseEntered(event -> updateBtn.setStyle("-fx-background-color: #0066ff;"));
        updateBtn.setOnMouseExited(event -> updateBtn.setStyle("-fx-background-color: #0044ff;"));
        updateBtn.setOnMousePressed(event -> {updateBtn.setStyle("-fx-background-color: #0044aa;");});

        deleteBtn.setOnMouseEntered(event -> deleteBtn.setStyle("-fx-background-color: #ff0000;"));
        deleteBtn.setOnMouseExited(event -> deleteBtn.setStyle("-fx-background-color: #dd0000;"));
        deleteBtn.setOnMousePressed(event -> {deleteBtn.setStyle("-fx-background-color: #aa0000;");});

        saveBtn.setOnAction(event -> {
            try {
                MilitaryLicenseEntity militaryLicenseEntity = MilitaryLicenseEntity
                        .builder()
                        .militaryId(Integer.parseInt(militaryId.getText()))
                        .firstName(firstName.getText())
                        .lastName(lastName.getText())
                        .startMilitaryDate(startDate.getValue())
                        .endMilitaryDate(endDate.getValue())
                        .type(militaryTypeComboBox.getValue())
                        .province(provinceComboBox.getValue())
                        .build();
                MilitaryLicenseService.save(militaryLicenseEntity);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "New License Saved ", ButtonType.OK);
                alert.show();
                resetForm();
                log.info("License Saved : " + militaryLicenseEntity);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
                log.error(e);
            }
        });
        updateBtn.setOnAction(event -> {
            try {
                MilitaryLicenseEntity militaryLicenseEntity = MilitaryLicenseEntity
                        .builder()
                        .id(Integer.parseInt(id.getText()))
                        .firstName(firstName.getText())
                        .lastName(lastName.getText())
                        .startMilitaryDate(startDate.getValue())
                        .endMilitaryDate(endDate.getValue())
                        .province(provinceComboBox.getValue())
                        .type(militaryTypeComboBox.getValue())
                        .build();
                MilitaryLicenseService.edit(militaryLicenseEntity);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "License Updated", ButtonType.OK);
                alert.show();
                resetForm();
                log.info("License Updated : " + militaryLicenseEntity);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
                log.error(e);
            }
        });
        deleteBtn.setOnAction(event -> {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wanna delete?", ButtonType.YES, ButtonType.NO);
            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    try {
                        MilitaryLicenseService.remove(Integer.parseInt(id.getText()));
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "License Deleted", ButtonType.OK);
                        alert.show();
                        resetForm();
                        log.info("License Deleted");
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
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

        militaryLicenseTable.setOnMouseReleased(event -> {
            MilitaryLicenseEntity militaryLicenseEntity = militaryLicenseTable.getSelectionModel().getSelectedItem();
            if (militaryLicenseEntity != null) {
                id.setText(String.valueOf(militaryLicenseEntity.getId()));
                militaryId.setText(String.valueOf(militaryLicenseEntity.getMilitaryId()));
                firstName.setText(militaryLicenseEntity.getFirstName());
                lastName.setText(militaryLicenseEntity.getLastName());
                startDate.setValue(militaryLicenseEntity.getStartMilitaryDate());
                endDate.setValue(militaryLicenseEntity.getEndMilitaryDate());
                provinceComboBox.getSelectionModel().select(militaryLicenseEntity.getProvince());
                militaryTypeComboBox.getSelectionModel().select(militaryLicenseEntity.getType());
            }
        });

        refreshBtn.setOnAction(event -> {
            resetForm();
        });
    }

    private void resetForm() {
        id.clear();
        militaryId.clear();
        firstName.clear();
        lastName.clear();
        startDate.setValue(null);
        endDate.setValue(null);
        provinceComboBox.setValue(null);
        militaryTypeComboBox.setValue(null);
        try {
            refreshTable(MilitaryLicenseService.findAll());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
            log.error(e);
        }
    }

    private void refreshTable(List<MilitaryLicenseEntity> militaryLicenseList) {
        ObservableList<MilitaryLicenseEntity> militaryLicenseObservableList = FXCollections.observableArrayList(militaryLicenseList);
//        militaryLicenseTable.getItems().clear();

        idCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        militaryIdCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMilitaryId()));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        militaryLicenseTable.setItems(militaryLicenseObservableList);
    }
}