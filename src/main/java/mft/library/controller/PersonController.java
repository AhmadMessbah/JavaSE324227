package mft.library.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j;
import mft.library.model.entity.Person;
import mft.library.model.entity.enums.FormState;
import mft.library.model.service.PersonService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Log4j
public class PersonController implements Initializable {
    @FXML
    private TextField idTxt, nameTxt, familyTxt, userTxt, nameSearchTxt, familySearchTxt;

    @FXML
    private PasswordField passTxt;

    @FXML
    private Button saveBtn, editBtn, removeBtn;

    @FXML
    private DatePicker birthDate;

    @FXML
    private RadioButton activeRdo, disableRdo;

    @FXML
    private ToggleGroup activeToggleGroup;

    @FXML
    private TableView<Person> personTable;

    @FXML
    private TableColumn<Person, Integer> idCol;

    @FXML
    private TableColumn<Person, String> nameCol, familyCol, userCol;

    @FXML
    private Label welcomeLbl;

    private PersonService personService = new PersonService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcomeLbl.setText("Welcome " + FormViewer.loggedInPerson.getUsername());

        if (FormViewer.personFormState.equals(FormState.New)) {
            editBtn.setDisable(true);
            removeBtn.setDisable(true);
        } else if (FormViewer.personFormState.equals(FormState.Edit)) {
            saveBtn.setDisable(true);
            removeBtn.setDisable(true);
        } else if (FormViewer.personFormState.equals(FormState.Remove)) {
            saveBtn.setDisable(true);
            editBtn.setDisable(true);
        } else if (FormViewer.personFormState.equals(FormState.Find)) {
            saveBtn.setDisable(true);
            editBtn.setDisable(true);
            removeBtn.setDisable(true);
            personTable.setLayoutX(14);
            personTable.setLayoutY(14);
            personTable.setPrefWidth(733);
            personTable.setPrefHeight(314);
        }

        log.info("MemberView initialized");
        resetForm();

        saveBtn.setOnAction(event -> {
            try {
                Person person = Person
                        .builder()
                        .name(nameTxt.getText())
                        .family(familyTxt.getText())
                        .birthDate(birthDate.getValue())
                        .username(userTxt.getText())
                        .password(passTxt.getText())
                        .active(activeRdo.isSelected())
                        .build();
                personService.save(person);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Member Saved : " + person, ButtonType.OK);
                alert.show();
                resetForm();
                log.info("Member Saved : " + person);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
                log.error(e);
            }
        });
        editBtn.setOnAction(event -> {
            try {
                Person person = Person
                        .builder()
                        .id(Integer.parseInt(idTxt.getText()))
                        .name(nameTxt.getText())
                        .family(familyTxt.getText())
                        .birthDate(birthDate.getValue())
                        .username(userTxt.getText())
                        .password(passTxt.getText())
                        .active(activeRdo.isSelected())
                        .build();
                personService.edit(person);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Member Edited", ButtonType.OK);
                alert.show();
                resetForm();
                log.info("Member Edited : " + person);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
                log.error(e);
            }
        });
        removeBtn.setOnAction(event -> {
            try {
                personService.remove(Integer.parseInt(idTxt.getText()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Member Removed", ButtonType.OK);
                alert.show();
                resetForm();
                log.info("Member Removed");
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
                log.error(e);
            }
        });

        nameSearchTxt.setOnKeyReleased(event -> {
            try {
                refreshTable(personService.findByNameAndFamily(nameSearchTxt.getText(), familySearchTxt.getText()));
            } catch (Exception e) {
                log.error(e);
            }
        });
        familySearchTxt.setOnKeyReleased(event -> {
            try {
                refreshTable(personService.findByNameAndFamily(nameSearchTxt.getText(), familySearchTxt.getText()));
            } catch (Exception e) {
                log.error(e);
            }
        });

        personTable.setOnMouseReleased(event -> {
            Person person = personTable.getSelectionModel().getSelectedItem();
            System.out.println(person);
            idTxt.setText(String.valueOf(person.getId()));
            nameTxt.setText(person.getName());
            familyTxt.setText(person.getFamily());
            birthDate.setValue(person.getBirthDate());
            userTxt.setText(person.getUsername());
            passTxt.setText(person.getPassword());
            if (person.isActive()) {
                activeRdo.setSelected(true);
                disableRdo.setSelected(false);
            } else {
                activeRdo.setSelected(false);
                disableRdo.setSelected(true);
            }
            FormViewer.selectedPerson = person;
        });
    }

    private void resetForm() {
        idTxt.clear();
        nameTxt.clear();
        familyTxt.clear();
        birthDate.setValue(null);
        userTxt.clear();
        passTxt.clear();
        activeRdo.setSelected(true);
        nameSearchTxt.clear();
        familySearchTxt.clear();
        try {
            refreshTable(personService.findAll());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
    }

    private void refreshTable(List<Person> personList) {
        ObservableList<Person> personObservableList = FXCollections.observableArrayList(personList);
        personTable.getItems().clear();

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        familyCol.setCellValueFactory(new PropertyValueFactory<>("family"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        personTable.setItems(personObservableList);
    }
}
