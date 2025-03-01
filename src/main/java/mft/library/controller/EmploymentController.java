package mft.library.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import mft.library.model.entity.Employment;
import mft.library.model.entity.Person;
import mft.library.model.service.EmploymentService;
import mft.library.model.service.PersonService;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class EmploymentController implements Initializable{
    @FXML
    private TextField idTxt,personTxt,departmentTxt,jobTxt,salaryTxt ;

    @FXML
    private Button saveBtn, editBtn, removeBtn;

    @FXML
    private DatePicker startDate,endDate;

    @FXML
    private TableView<Employment> employmentTable;

    @FXML
    private TableColumn<Employment, Integer> idCol;

    @FXML
    private TableColumn<Employment, String> personCol,jobCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resetForm();

        saveBtn.setOnAction(event -> {
            try {

                Person person = Person
                        .builder()
                        .id(1)
                        .name("behnaz")
                        .family("mohammadii")
                        .birthDate(LocalDate.now())
                        .username("b-m")
                        .password("b123m")
                        .active(true)
                        .build();
                Employment employment =
                        Employment
                                .builder()
                                .person(person)
                                .startDate(startDate.getValue())
                                .endDate(endDate.getValue())
                                .department(departmentTxt.getText())
                                .job(jobTxt.getText())
                                .salary(Long.valueOf(salaryTxt.getText()))
                                .build();
                EmploymentService.save(employment);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "employment Saved : " + employment, ButtonType.OK);
                alert.show();
                resetForm();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
        });

        editBtn.setOnAction(event -> {
            try {
                Person person = Person
                        .builder()
                        .id(1)
                        .name("behnaz")
                        .family("mohammadii")
                        .birthDate(LocalDate.now())
                        .username("b-m")
                        .password("b123m")
                        .active(true)
                        .build();
                Employment employment =
                        Employment
                                .builder()
                                .id(Integer.parseInt(idTxt.getText()))
                                .person(person)
                                .startDate(startDate.getValue())
                                .endDate(endDate.getValue())
                                .department(departmentTxt.getText())
                                .job(jobTxt.getText())
                                .salary(Long.valueOf(salaryTxt.getText()))
                                .build();
                EmploymentService.edit(employment);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "employment Edited", ButtonType.OK);
                alert.show();
                resetForm();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
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


        employmentTable.setOnMouseReleased(event -> {
            Employment employment = employmentTable.getSelectionModel().getSelectedItem();
            idTxt.setText(String.valueOf(employment.getId()));
//            personTxt.setText(employment.getPerson());
            personTxt.setText(employment.getPerson().toString());
            startDate.setValue(employment.getStartDate());
            endDate.setValue(employment.getEndDate());
            jobTxt.setText(employment.getJob().toString());
        });
    }


    private void resetForm() {
        idTxt.clear();
        personTxt.clear();
        departmentTxt.clear();
        startDate.setValue(null);
        endDate.setValue(null);
        jobTxt.clear();
        salaryTxt.clear();
        departmentTxt.clear();
        try {
            refreshTable(EmploymentService.findAll());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
    }

    private void refreshTable(List<Employment> employmentList) {
        ObservableList<Employment> employmentObservableList = FXCollections.observableArrayList(employmentList);
        employmentTable.getItems().clear();
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        personCol.setCellValueFactory(new PropertyValueFactory<>("person"));
        jobCol.setCellValueFactory(new PropertyValueFactory<>("job"));
        employmentTable.setItems(employmentObservableList);
    }
}
