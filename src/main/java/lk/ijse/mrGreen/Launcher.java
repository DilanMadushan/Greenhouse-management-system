package lk.ijse.mrGreen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Launcher extends Application {
    public static void main(String[] args) {
       launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent rootNode =FXMLLoader.load(getClass().getResource("/View/Login.fxml"));

        Scene scene=new Scene(rootNode);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.centerOnScreen();

        stage.show();

    }
}
