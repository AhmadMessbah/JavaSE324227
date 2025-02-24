package mft.library.controller;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j;

import java.net.URL;
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

    // نگهداری آیتم انتخاب‌شده از جدول برای عملیات ویرایش/حذف
    private Bimeh selectedBimeh;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        insuranceTypeCombo.setItems(FXCollections.observableArrayList("CAR", "LIFE", "HEALTH", "PROPERTY"));
        statusCombo.setItems(FXCollections.observableArrayList("ACTIVE", "EXPIRED", "CANCELLED"));

        resetForm();

        saveBtn.setOnAction(event -> {
            try {
                Bimeh bimeh = Bimeh.builder()
                        .policyNumber(policyNumberTxt.getText())
                        .insuranceType(InsuranceType.valueOf(insuranceTypeCombo.getSelectionModel().getSelectedItem()))
                        .startDate(startDatePicker.getValue())
                        .endDate(endDatePicker.getValue())
                        .person(personTxt.getText())
                        .status(InsuranceStatus.valueOf(statusCombo.getSelectionModel().getSelectedItem()))
                        .build();
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
                    Bimeh bimeh = Bimeh.builder()
                            .id(selectedBimeh.getId())
                            .policyNumber(policyNumberTxt.getText())
                            .insuranceType(InsuranceType.valueOf(insuranceTypeCombo.getSelectionModel().getSelectedItem()))
                            .startDate(startDatePicker.getValue())
                            .endDate(endDatePicker.getValue())
                            .person(personTxt.getText())
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

        // هنگام انتخاب یک ردیف از جدول، اطلاعات آن در فرم نمایش داده شود
        bimehTable.setOnMouseReleased(event -> {
            selectedBimeh = bimehTable.getSelectionModel().getSelectedItem();
            if (selectedBimeh != null) {
                policyNumberTxt.setText(selectedBimeh.getPolicyNumber());
                insuranceTypeCombo.getSelectionModel().select(String.valueOf(selectedBimeh.getInsuranceType()));
                startDatePicker.setValue(selectedBimeh.getStartDate());
                endDatePicker.setValue(selectedBimeh.getEndDate());
                personTxt.setText(selectedBimeh.getPerson());
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
        personCol.setCellValueFactory(new PropertyValueFactory<>("person"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        bimehTable.setItems(bimehObservableList);
    }
}

