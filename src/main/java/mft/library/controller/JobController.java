package mft.library.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import mft.library.model.entity.JobHistory;
import mft.library.model.entity.enums.FormState;
import mft.library.model.service.JobService;
import mft.library.model.service.PersonService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class JobController implements Initializable {

    @FXML
    private TextField idTxt,personTxt,jobTxt,companyTxt,descriptionTxt,personSearchTxt,jobSearchTxt ;

    @FXML
    private Button saveBtn, editBtn, removeBtn;

    @FXML
    private DatePicker startDate,endDate;

    @FXML
    private TableView<JobHistory> jobTable;

    @FXML
    private TableColumn<JobHistory, Integer> idCol;

    @FXML
    private TableColumn<JobHistory, String> personCol,jobCol,companyCol;

    @FXML
    private Pane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pane.setPrefWidth(268);
        if (FormViewer.jobHistoryFormState.equals(FormState.New)) {
            saveBtn.setVisible(true);
        } else if (FormViewer.jobHistoryFormState.equals(FormState.Edit)) {
            editBtn.setVisible(true);
        } else if (FormViewer.jobHistoryFormState.equals(FormState.Remove)) {
            removeBtn.setVisible(true);
        } else if (FormViewer.jobHistoryFormState.equals(FormState.Find)) {
//            saveBtn.setDisable(true);
//            editBtn.setDisable(true);
//            removeBtn.setDisable(true);
            pane.setPrefWidth(802);
            jobTable.setLayoutX(14);
            jobTable.setLayoutY(14);
            jobTable.setPrefWidth(733);
            jobTable.setPrefHeight(314);
        }

        resetForm();

        saveBtn.setOnAction(event -> {
            try {
                JobHistory jobHistory =
                        JobHistory
                        .builder()
//                        .person(personTxt.getText())
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
//                        .person(personTxt.getText())
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
                PersonService.remove(Integer.parseInt(idTxt.getText()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Member Removed", ButtonType.OK);
                alert.show();
                resetForm();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
        });

        personSearchTxt.setOnKeyReleased(event -> {
            try{
//                refreshTable(JobService.findByPersonAndJob(personSearchTxt.getText() , jobSearchTxt.getText()));
            }catch (Exception e){

            }
        });

        jobSearchTxt.setOnKeyReleased(event -> {
            try{
//                refreshTable(JobService.findByPersonAndJob(personSearchTxt.getText() , jobSearchTxt.getText()));
            }catch (Exception e){

            }
        });

        jobTable.setOnMouseReleased(event -> {
            JobHistory jobHistory = jobTable.getSelectionModel().getSelectedItem();
            idTxt.setText(String.valueOf(jobHistory.getId()));
//            personTxt.setText(jobHistory.getPerson());
            jobTxt.setText(jobHistory.getJob());
            startDate.setValue(jobHistory.getStartDate());
            endDate.setValue(jobHistory.getEndDate());
            descriptionTxt.setText(jobHistory.getDescription());
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
