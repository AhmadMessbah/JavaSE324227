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
    private Button saveBtn, updateBtn, deleteBtn;

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
        resetForm();
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
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "New License Saved : "
                        + militaryLicenseEntity, ButtonType.OK);
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
                provinceComboBox.setValue(militaryLicenseEntity.getProvince());
                militaryTypeComboBox.setValue(militaryLicenseEntity.getType());
            }
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

//        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
//        militaryIdCol.setCellValueFactory(new PropertyValueFactory<>("militaryId"));
        idCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        militaryIdCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMilitaryId()));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        militaryLicenseTable.setItems(militaryLicenseObservableList);
    }
}