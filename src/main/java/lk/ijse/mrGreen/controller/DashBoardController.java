package lk.ijse.mrGreen.controller;

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
import javafx.util.Duration;
import model.CustomerModel;
import model.EmployeeModel;
import model.GreenHouseModel;
import model.LettuceModel;

import java.io.IOException;
import java.sql.SQLException;
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

    @FXML
    private Text txtLettuceCount;

    @FXML
    private Text txtEmployee;

    @FXML
    private Text txtCustomer;

    @FXML
    private Text txtGreenhouse;
    private int year;
    private int month;
    private int datee;

    LettuceModel lettModel =new LettuceModel();
    EmployeeModel empModel = new EmployeeModel();
    CustomerModel cusModel = new CustomerModel();
    GreenHouseModel greenModel = new GreenHouseModel();

    @FXML
    private AnchorPane root1;

    public void initialize(){
        setDateandTime();
        setLettuceCount();
        setEmployeeCount();
        setCustomerCount();
        setGreenhouseCount();
    }

    private void setGreenhouseCount() {

        try {
            String Count = Integer.toString(greenModel.getGreenhouseCount());
            txtGreenhouse.setText(Count);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCustomerCount() {
        try {
            String count = Integer.toString(cusModel.getCustomerCount());
            txtCustomer.setText(count);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setEmployeeCount() {
        try {
            String count = Integer.toString(empModel.getEmployeCount());
            txtEmployee.setText(count);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setLettuceCount() {

        try {
            String count = Integer.toString(lettModel.getCount());
            txtLettuceCount.setText(count);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateTime() {
        LocalTime now = LocalTime.now();
        String formattedTime = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        txtTime.setText(formattedTime);
    }

    private void setDateandTime() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> updateTime()));
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
    void LettuceOnAction(ActionEvent event) throws IOException {
        Parent rootNode =FXMLLoader.load(getClass().getResource("/view/Lettuce_Form.fxml"));
        Stage stage = (Stage) root1.getScene().getWindow();

        Scene scene=new Scene(rootNode);
        stage.setScene(scene);
        stage.centerOnScreen();

    }

    @FXML
    void aboutOnAction(ActionEvent event) {

    }

    @FXML
    void customerOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/Customer_Form.fxml"));
        Stage stage = (Stage) root1.getScene().getWindow();

        Scene scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void employeeOnAction(ActionEvent event) throws IOException {
        Parent rootNode =FXMLLoader.load(getClass().getResource("/view/Employee_Form.fxml"));
        Stage stage = (Stage) root1.getScene().getWindow();

        Scene scene=new Scene(rootNode);
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void fertilizerOnAction(ActionEvent event) throws IOException {
        Parent rootNode =FXMLLoader.load(getClass().getResource("/view/Fertilizer_Form.fxml"));
        Stage stage = (Stage) root1.getScene().getWindow();

        Scene scene=new Scene(rootNode);
        stage.setScene(scene);
        stage.centerOnScreen();

    }

    @FXML
    void greenhouseOnAction(ActionEvent event) throws IOException {
        Parent rootNode =FXMLLoader.load(getClass().getResource("/view/Greenhouse_Form.fxml"));
        Stage stage = (Stage) root1.getScene().getWindow();

        Scene scene=new Scene(rootNode);
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void notifyOnAction(ActionEvent event) {

    }

    @FXML
    void orderOnAction(ActionEvent event) {

    }

    @FXML
    void supplairOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/Supplier_Form.fxml"));
        Stage stage = (Stage) root1.getScene().getWindow();

        Scene scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.centerOnScreen();
    }
    @FXML
    public void backOnAction(MouseEvent mouseEvent) throws IOException {
        Parent rootNode =FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        Stage stage = (Stage) root1.getScene().getWindow();

        Scene scene=new Scene(rootNode);
        stage.setScene(scene);
        stage.centerOnScreen();

    }
}
