package lk.ijse.mrGreen.Controller;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;


public class DashBoardController {

    @FXML
    private Text txtDate;

    @FXML
    private Text txtTime;
    private int year;
    private int month;
    private int datee;

    @FXML
    private AnchorPane root1;

    public void initialize(){
        setDateandTime();
    }
    private void updateTime() {
        LocalTime now = LocalTime.now();
        String formattedTime = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        txtTime.setText(formattedTime);
    }

    private void setDateandTime() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateTime()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        DateFormat date = new SimpleDateFormat("yyy:MM:dd");
        Calendar cal = Calendar.getInstance();

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        datee = cal.get(Calendar.DATE);
        txtDate.setText(year + " : " + month + " : " + datee);
    }

    @FXML
    void LettuceOnAction(ActionEvent event) {

    }

    @FXML
    void aboutOnAction(ActionEvent event) {

    }

    @FXML
    void customerOnAction(ActionEvent event) {

    }

    @FXML
    void employeeOnAction(ActionEvent event) {

    }

    @FXML
    void fertilizerOnAction(ActionEvent event) {

    }

    @FXML
    void greenhouseOnAction(ActionEvent event) {

    }

    @FXML
    void notifyOnAction(ActionEvent event) {

    }

    @FXML
    void orderOnAction(ActionEvent event) {

    }

    @FXML
    void supplairOnAction(ActionEvent event) {

    }
    @FXML
    public void backOnAction(MouseEvent mouseEvent) throws IOException {
        Parent rootNode =FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
        Stage stage = (Stage) root1.getScene().getWindow();

        Scene scene=new Scene(rootNode);
        stage.setScene(scene);
        stage.centerOnScreen();

    }
}