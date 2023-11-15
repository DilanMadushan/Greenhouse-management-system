package lk.ijse.mrGreen.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.UserModel;

import java.sql.SQLException;

public class ForgetFormController {

    @FXML
    private JFXTextField txtPassword;

    @FXML
    private JFXTextField txtUserName;

    UserModel userModel = new UserModel();

    @FXML
    void checkOnAction(ActionEvent event) {
        String name = txtUserName.getText();

        try {
            String pw = userModel.getPassword(name);
            if(pw!=null){
                txtPassword.setDisable(false);
                txtPassword.setText(pw);
            }else {
                new Alert(Alert.AlertType.WARNING,"User not found").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void closeOnAction(MouseEvent event) {
        Stage stage = (Stage) txtUserName.getScene().getWindow();
        stage.close();
    }


}
