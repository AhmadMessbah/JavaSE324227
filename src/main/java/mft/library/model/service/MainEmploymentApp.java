package mft.library.model.service;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j;

@Log4j
public class MainEmploymentApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        log.info("Starting Application");
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/javafx/mainEmploymentView.fxml")));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Person Info");
        primaryStage.show();
    }

}
