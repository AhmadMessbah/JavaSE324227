package mft.library.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mft.library.model.entity.Person;
import mft.library.model.service.PersonService;

import java.util.List;

public class PersonModalController {

    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> fNameCol, lNameCol, birthCol;
    @FXML
    private Button selectBtn, cancelBtn;

    private Person selectedPerson;

    @FXML
    public void initialize() {
        loadPersonData();

        selectBtn.setOnAction(e -> {
        });
        cancelBtn.setOnAction(e -> {
        });
    }

    public void loadPersonData() {
        try {
            List<Person> personList = PersonService.findAll();
            ObservableList<Person> personObservableList = FXCollections.observableArrayList(personList);

            fNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            lNameCol.setCellValueFactory(new PropertyValueFactory<>("family"));
            birthCol.setCellValueFactory(new PropertyValueFactory<>("birthDate"));

            personTable.setItems(personObservableList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void selectPerson() {
        selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            Stage stage = (Stage) personTable.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void closeModal() {
        selectedPerson = null;
        Stage stage = (Stage) personTable.getScene().getWindow();
        stage.close();
    }
}
