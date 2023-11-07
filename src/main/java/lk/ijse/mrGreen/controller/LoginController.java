package lk.ijse.mrGreen.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.mrGreen.dto.UserDto;
import model.UserModel;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private JFXTextField txtNmae;

    @FXML
    private JFXTextField txtPassword;
    @FXML
    private AnchorPane root;

    private UserModel userModel = new UserModel();

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
                    new Alert(Alert.AlertType.INFORMATION, "Incorrect username or password").show();
                }

            }
            else{
                new Alert(Alert.AlertType.INFORMATION, "Incorrect username or password").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }



    }

    @FXML
    public void closeOnAction(javafx.scene.input.MouseEvent mouseEvent) {
        System.exit(0);
    }
}
