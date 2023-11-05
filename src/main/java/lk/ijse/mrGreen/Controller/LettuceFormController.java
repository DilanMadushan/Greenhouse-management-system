package lk.ijse.mrGreen.Controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.mrGreen.dto.LettuceDto;
import model.LettuceModel;

import java.io.IOException;
import java.sql.SQLException;


public class LettuceFormController {

    @FXML
    public AnchorPane Anchor;

    @FXML
    private TableColumn<?, ?> colHumid;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTemp;

    @FXML
    private TableView<?> tblLettuce;

    @FXML
    private JFXTextField txtHumid;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtTemp;

    @FXML
    public JFXTextField txtSupName;

    private LettuceModel letModel = new LettuceModel();

    @FXML
    void addOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name=txtName.getText();
        String temp=txtTemp.getText();
        String humid=txtHumid.getText();
        String qty=txtQty.getText();

        var dto = new LettuceDto(id,name,temp,humid,qty);

        try {
            boolean isSaved = LettuceModel.saveLettuce(dto);

            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Saved").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }


    }

    @FXML
    void removeOnAction(ActionEvent event) {

    }

    @FXML
    void updateOnAction(ActionEvent event) {

    }

    @FXML
    public void backOnAction(MouseEvent mouseEvent) throws IOException {

        Parent rootNode = FXMLLoader.load(getClass().getResource("/View/DashBoard.fxml"));
        Stage stage = (Stage) Anchor.getScene().getWindow();

        Scene scene=new Scene(rootNode);
        stage.setScene(scene);
        stage.centerOnScreen();

    }
}
