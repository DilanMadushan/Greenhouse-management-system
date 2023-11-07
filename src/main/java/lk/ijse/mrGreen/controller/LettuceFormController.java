package lk.ijse.mrGreen.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import lk.ijse.mrGreen.dto.SupplierDto;
import model.LettuceModel;
import model.SupplierModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class LettuceFormController {


    @FXML
    public AnchorPane Anchor;
    public JFXComboBox cmbSupName;

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


    public void initialize(){
        loadAllSupplier();
    }

    private void loadAllSupplier() {

        ObservableList<String> obList = FXCollections.observableArrayList();


        try {
            List<SupplierDto> supDto = SupplierModel.loadAllSupplier();
            for (SupplierDto dto: supDto) {
                obList.add(dto.getSup_id());
            }
            for (String x: obList) {
                System.out.println(x);
            }
            cmbSupName.setItems(obList);
            System.out.println(cmbSupName.getValue());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void addOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name=txtName.getText();
        String temp=txtTemp.getText();
        String humid=txtHumid.getText();
        String qty=txtQty.getText();
        String suppId= (String) cmbSupName.getValue();

        var dto = new LettuceDto(id,name,temp,humid,qty,suppId);

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

        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/DashBoard.fxml"));
        Stage stage = (Stage) Anchor.getScene().getWindow();

        Scene scene=new Scene(rootNode);
        stage.setScene(scene);
        stage.centerOnScreen();

    }
}
