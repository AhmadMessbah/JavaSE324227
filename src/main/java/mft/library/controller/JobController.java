package mft.library.controller;

import com.github.javafaker.Job;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import mft.library.model.entity.JobHistory;
import mft.library.model.service.JobService;
import mft.library.model.service.MemberService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class JobController implements Initializable {

    @FXML
    private TextField idTxt,personTxt,jobTxt,companyTxt,descriptionTxt,personSearchTxt,jobSearchTxt;

    @FXML
    private Button saveBtn, editBtn, removeBtn;

    @FXML
    private Deprecated startDate,endDate;

    @FXML
    private ToggleGroup activeToggleGroup;

    @FXML
    private TableView<Job> jobTable;

    @FXML
    private TableColumn<Job, Integer> idCol,personCol,jobCol,companyCol,startDateCol,endDateCol;

    @FXML
    private TableColumn<Job, String> personCol,jobCole;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resetForm();

        saveBtn.setOnAction(event -> {
            try {
//                Member member = Member
                JobHistory jobHistory =
                        JobHistory
                        .builder()
                        .person(personTxt.getText())
                        .job(jobTxt.getText())
                                .startDate(startDate.getValue())
                                .endDate(endDate.getValue())
                        .company(companyTxt.getText())
                        .description(descriptionTxt.getText())
                        .build();
                JobService.save(jobHistory);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "jobHistory Saved : " + jobHistory, ButtonType.OK);
                alert.show();
                resetForm();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
        });
        editBtn.setOnAction(event -> {
            try {
                JobHistory jobHistory =
                        JobHistory
                        .builder()
                        .id(Integer.parseInt(idTxt.getText()))
                        .person(personTxt.getText())
                        .job(jobTxt.getText())
                        .startDate(startDate.getValue())
                        .endDate(endDate.getValue())
                        .company(companyTxt.getText())
                        .description(descriptionTxt.getText())
                        .build();
                JobService.edit(jobHistory);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "jobHistory Edited", ButtonType.OK);
                alert.show();
                resetForm();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
        });
        removeBtn.setOnAction(event -> {
            try {
                MemberService.remove(Integer.parseInt(idTxt.getText()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Member Removed", ButtonType.OK);
                alert.show();
                resetForm();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
        });

    }
    private void resetForm() {
        idTxt.clear();
        personTxt.clear();
        jobTxt.clear();
        startDate.setValue(null);
        endDate.setValue(null);
        companyTxt.clear();
        descriptionTxt.clear();
        personSearchTxt.clear();
        jobSearchTxt.clear();
        try {
            refreshTable(JobService.findAll());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
    }

    private void refreshTable(List<JobHistory> jobList) {
        ObservableList<JobHistory> jobObservableList = FXCollections.observableArrayList(jobList);
        jobTable.getItems().clear();

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        personCol.setCellValueFactory(new PropertyValueFactory<>("person"));
        jobCol.setCellValueFactory(new PropertyValueFactory<>("job"));
        companyCol.setCellValueFactory(new PropertyValueFactory<>("company"));

        jobTable.setItems(jobObservableList);
    }
}
