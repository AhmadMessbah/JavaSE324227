package mft.library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j;
import mft.library.controller.PersonModalController;
import mft.library.model.entity.Person;

import java.io.IOException;
import java.util.List;

@Log4j
public class MilitaryLicenseApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        log.info("Starting Application");
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/javafx/militaryLicenseView.fxml")));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Military License");
        primaryStage.show();
    }


    public static void showPersonModal() throws IOException {
//            Scene scene = new Scene(FXMLLoader.load(MilitaryLicenseApp.class.getResource("/javafx/personModal.fxml")));

        FXMLLoader fxmlLoader = new FXMLLoader(MilitaryLicenseApp.class.getResource("/javafx/personModal.fxml"));
            Parent root = fxmlLoader.load();
            log.info("Showing modal");
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle("Person Modal");
            modalStage.setScene(new Scene(root));
//            modalStage.setScene(scene);
            modalStage.showAndWait();
    }
}
