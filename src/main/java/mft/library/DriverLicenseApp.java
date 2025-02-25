package mft.library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DriverLicenseApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/javafx/driverLicenseView.fxml")));
        primaryStage.setScene(scene);
        primaryStage.setTitle("DriverLicense Info");
        primaryStage.show();

    }
}
