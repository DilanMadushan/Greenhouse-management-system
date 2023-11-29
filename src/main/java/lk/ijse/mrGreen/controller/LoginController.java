package lk.ijse.mrGreen.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.mrGreen.dto.UserDto;
import lombok.SneakyThrows;
import model.UserModel;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    public JFXTextField txtPAssWord;
    @FXML
    private JFXTextField txtNmae;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private AnchorPane root;

    private UserModel userModel = new UserModel();

    public void initialize(){
        txtPAssWord.setVisible(false);
    }
    @FXML
    void FogetOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/forget_Form.fxml"));
        Scene scene = new Scene(rootNode);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.centerOnScreen();
        stage.show();
    }
    @FXML
    void loginOnAction(ActionEvent event) throws IOException {

        String name = txtNmae.getText();
        String password= txtPassword.getText();

        try {
            UserDto userDto = userModel.checkUser(name);
            if(userDto!=null){
                if( name.equals(userDto.getName()) && password.equals(userDto.getPassword())){
                    AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/DashBoard.fxml"));
                    Scene scene = new Scene(anchorPane);

                    Stage stage = (Stage) root.getScene().getWindow();
                    stage.setScene(scene);
                    stage.centerOnScreen();
                }else {
                    new Alert(Alert.AlertType.WARNING, "Incorrect username or password").show();
                }

            }
            else{
                new Alert(Alert.AlertType.WARNING, "Incorrect username or password").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }



    }


    @FXML
    public void closeOnAction(javafx.scene.input.MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void showOnActionRellese(MouseEvent mouseEvent) {
            txtPAssWord.setVisible(false);
            txtPassword.setVisible(true);
    }

    public void showOnActionPressed(MouseEvent mouseEvent) {
        txtPassword.setVisible(false);
        txtPAssWord.setText(txtPassword.getText());
        txtPAssWord.setVisible(true);
    }
    @FXML
    void userNameOnAction(ActionEvent event) {
        txtPassword.requestFocus();
    }

    public void passwordOnAction(ActionEvent event) throws IOException {
        loginOnAction(event);
    }
}
