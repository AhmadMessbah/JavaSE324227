package mft.library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JobHistoryApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/javafx/jobView.fxml")));
        primaryStage.setScene(scene);
        primaryStage.setTitle("JobHistory Info");
        primaryStage.show();

    }
}
