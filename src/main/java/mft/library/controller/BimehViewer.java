package mft.library.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mft.library.model.entity.enums.FormState;

public class BimehViewer {
    public static FormState bimehFormState;
    public static FormState selectedBimeh;

    public void showBimehForm() throws Exception {

        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/javafx/bimeh.fxml")));
        stage.setScene(scene);
        stage.setTitle("Bimeh Info");
        stage.show();
    }
}
