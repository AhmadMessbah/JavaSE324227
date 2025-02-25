package mft.library.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j;
import mft.library.model.entity.DriverLicense;
import mft.library.model.entity.enums.FormState;
import mft.library.model.service.PersonService;
import mft.library.model.service.DriverLicenseService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
@Log4j
public class DriverLicenseController implements Initializable {

    @FXML
    private TextField idTxt,nameTxt,licenseidTxt,nameSerachTxt,licenseIdSearchTxt ;

    @FXML
    private Button saveBtn, editBtn, removeBtn;

    @FXML
    private DatePicker datetime,expire;

    @FXML
    private TableView<DriverLicense> driverLicenseTable;

    @FXML
    private TableColumn<DriverLicense, Integer> idCol;

    @FXML
    private TableColumn<DriverLicense, String> nameCol,licenseIdCol,dateTimeCol,expireCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (FormViewer.driverLicenseFormState.equals(FormState.New)) {
            editBtn.setDisable(true);
            removeBtn.setDisable(true);
        } else if (FormViewer.driverLicenseFormState.equals(FormState.Edit)) {
            saveBtn.setDisable(true);
            removeBtn.setDisable(true);
        } else if (FormViewer.driverLicenseFormState.equals(FormState.Remove)) {
            saveBtn.setDisable(true);
            editBtn.setDisable(true);
        } else if (FormViewer.driverLicenseFormState.equals(FormState.Find)) {
            saveBtn.setDisable(true);
            editBtn.setDisable(true);
            removeBtn.setDisable(true);
            driverLicenseTable.setLayoutX(14);
            driverLicenseTable.setLayoutY(14);
            driverLicenseTable.setPrefWidth(758);
            driverLicenseTable.setPrefHeight(325);
        }

        resetForm();

        saveBtn.setOnAction(event -> {
            try {
//                FormViewer formViewer = new FormViewer();
//                FormViewer.personFormState = FormState.Find;
//                formViewer.showPersonForm();
//
//                System.out.println(FormViewer.selectedPerson);

                DriverLicense driverLicense =
                        DriverLicense
                        .builder()

//                        .person(personTxt.getText())
                        .licenseId(Integer.parseInt(licenseidTxt.getText()))
                                .dateTime(datetime.getValue())
                                .expire(expire.getValue())
                        .build();
                DriverLicenseService.save(driverLicense);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "driverLicense Saved : " + driverLicense, ButtonType.OK);
                alert.show();
                resetForm();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
        });

        editBtn.setOnAction(event -> {
            try {
                DriverLicense driverLicense =
                        DriverLicense
                        .builder()
                        .id(Integer.parseInt(idTxt.getText()))
//                        .person(personTxt.getText())
                        .licenseId(Integer.parseInt(licenseidTxt.getText()))
                        .dateTime(datetime.getValue())
                        .expire(expire.getValue())
                        .build();
                DriverLicenseService.edit(driverLicense);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "driverLicense Edited", ButtonType.OK);
                alert.show();
                resetForm();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "controller 80" + e.getMessage());
                alert.show();
            }
        });

        removeBtn.setOnAction(event -> {
            try {
                PersonService.remove(Integer.parseInt(idTxt.getText()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Member Removed", ButtonType.OK);
                alert.show();
                resetForm();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
        });

        nameSerachTxt.setOnKeyReleased(event -> {
            try{
//                refreshTable(DriverLicenseService.findByPersonAndJob(nameSerachTxt.getText() , licenseIdSearchTxt.getText()));
            }catch (Exception e){

            }
        });

        licenseIdSearchTxt.setOnKeyReleased(event -> {
            try{
//                refreshTable(DriverLicenseService.findByPersonAndJob(nameSerachTxt.getText() , licenseIdSearchTxt.getText()));
            }catch (Exception e){

            }
        });

        driverLicenseTable.setOnMouseReleased(event -> {
            DriverLicense driverLicense = driverLicenseTable.getSelectionModel().getSelectedItem();
            idTxt.setText(String.valueOf(driverLicense.getId()));
//            personTxt.setText(driverLicense.getPerson());
            licenseidTxt.setText(String.valueOf(driverLicense.getLicenseId()));
            datetime.setValue(driverLicense.getDateTime());
            expire.setValue(driverLicense.getExpire());

            FormViewer.selectedDriverLicense = driverLicense;
        });

    }


private void resetForm() {
        idTxt.clear();
        nameTxt.clear();
        licenseidTxt.clear();
        datetime.setValue(null);
        expire.setValue(null);
        nameSerachTxt.clear();
        licenseIdSearchTxt.clear();
        try {
            refreshTable(DriverLicenseService.findAll());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
    }

    private void refreshTable(List<DriverLicense> jobList) {
        ObservableList<DriverLicense> jobObservableList = FXCollections.observableArrayList(jobList);
        driverLicenseTable.getItems().clear();

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        licenseIdCol.setCellValueFactory(new PropertyValueFactory<>("licenseId"));
        dateTimeCol.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        expireCol.setCellValueFactory(new PropertyValueFactory<>("expire"));

        driverLicenseTable.setItems(jobObservableList);
    }
}
