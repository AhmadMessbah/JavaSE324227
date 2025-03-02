package mft.library.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j;
import mft.library.model.entity.enums.FormStateEmployment;

@Log4j
public class FormViewerEmployment {
    public static FormStateEmployment employmentFormState;
    public static FormStateEmployment personFormStateEmployment;

    public void showPersonForm() throws Exception {
        log.info("Starting Application");
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/javafx/mainEmploymentView.fxml")));
        stage.setScene(scene);
        stage.setTitle("Member Info");
        stage.show();
    }
}
