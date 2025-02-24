package mft.library;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class BimehApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/javafx/bimeh.fxml")));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Bimeh Info");
        primaryStage.show();
    }
}

