package mft.library.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j;
import mft.library.model.entity.Person;
import mft.library.model.service.PersonService;

import java.util.List;

@Log4j
public class PersonModalController {

    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> fNameCol, lNameCol, birthCol;
    @FXML
    private Button selectBtn, cancelBtn;

    private Person selectedPerson;
    PersonService personService = new PersonService();

    @FXML
    public void initialize() {
        loadPersonData();

        selectBtn.setOnAction(e -> {
            selectedPerson = personTable.getSelectionModel().getSelectedItem();
            if (selectedPerson != null) {
                Stage stage = (Stage) selectBtn.getScene().getWindow();
                stage.close();
                MilitaryLicenseController militaryLicenseController = new MilitaryLicenseController();
                militaryLicenseController.fillPersonField(selectedPerson);
            }

        });
        cancelBtn.setOnAction(e -> {
            Stage stage = (Stage) selectBtn.getScene().getWindow();
            stage.close();
        });
    }

    public void loadPersonData() {
        try {
            List<Person> personList = personService.findAll();
            ObservableList<Person> personObservableList = FXCollections.observableArrayList(personList);

            fNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            lNameCol.setCellValueFactory(new PropertyValueFactory<>("family"));
            birthCol.setCellValueFactory(new PropertyValueFactory<>("birthDate"));

            personTable.setItems(personObservableList);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
