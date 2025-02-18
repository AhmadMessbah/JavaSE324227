package mft.library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j;

@Log4j
public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        log.info("Starting Application");
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/javafx/memberView.fxml")));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Member Info");
        primaryStage.show();

        System.out.println("Dastoore Jadid");
    }
}
