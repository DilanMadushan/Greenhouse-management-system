package lk.ijse.mrGreen.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import lk.ijse.mrGreen.dto.LettuceDto;
import lk.ijse.mrGreen.dto.SupplierDto;
import lk.ijse.mrGreen.dto.tm.LettuceTm;
import model.LettuceModel;
import model.SupplierModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class LettuceFormController {


    @FXML
    public AnchorPane Anchor;
    @FXML
    private TableView<LettuceTm> tblLettuce;

    @FXML
    private JFXComboBox cmbSupId;

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
    private JFXTextField txtunit;

    @FXML
    private TableColumn<?, ?> colHumid;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSupId;

    @FXML
    private TableColumn<?, ?> colTemp;

    @FXML
    private TableColumn<?, ?> colUnit;

    SupplierModel suppModel = new SupplierModel();

    LettuceModel lettModel = new LettuceModel();


    public void initialize(){
        loadAllSupplier();
        setCellValuesFactory();
        loadAllLettuce();
    }

    private void setCellValuesFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTemp.setCellValueFactory(new PropertyValueFactory<>("temp"));
        colHumid.setCellValueFactory(new PropertyValueFactory<>("humid"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("Unit"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colSupId.setCellValueFactory(new PropertyValueFactory<>("suppId"));

    }
    private void loadAllLettuce() {

    ObservableList<LettuceTm> obList= FXCollections.observableArrayList();

        try {
            List<LettuceDto> dto = lettModel.getAllLettuceDetails();

            for (LettuceDto list: dto) {
                obList.add(new LettuceTm(
                        list.getId(),
                        list.getName(),
                        list.getTemp(),
                        list.getHumid(),
                        list.getUnit(),
                        list.getQty(),
                        list.getSuppId()
                ));
            }
            tblLettuce.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadAllSupplier() {
        ObservableList<String> obList = FXCollections.observableArrayList();


        try {
            List<SupplierDto> supDto = suppModel.loadAllSupplier();
            for (SupplierDto dto: supDto) {
                obList.add(dto.getSup_id());
            }

            cmbSupId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void addOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name= txtName.getText();
        int temp = Integer.parseInt(txtTemp.getText());
        int humid = Integer.parseInt(txtHumid.getText());
        double qty = Double.parseDouble(txtQty.getText());
        double unit = Double.parseDouble(txtunit.getText());
        String suppId= (String) cmbSupId.getValue();


        var dto = new LettuceDto(id,name,temp,humid,qty,unit,suppId);

        try {
            boolean isSaved=lettModel.saveLettuce(dto);
            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved Successfully").show();
                initialize();
                clearFields();
            }else {
                new Alert(Alert.AlertType.WARNING,"Save Failed").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void removeOnAction(ActionEvent event) {
        String id=txtId.getText();

        try {
            boolean isDelete =lettModel.deleteLettuce(id);

            if (isDelete) {
                new Alert(Alert.AlertType.CONFIRMATION,"Delete Successfully").show();
                initialize();
                clearFields();
            }else{
                new Alert(Alert.AlertType.WARNING,"Delete Failed").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void updateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name= txtName.getText();
        int temp = Integer.parseInt(txtTemp.getText());
        int humid = Integer.parseInt(txtHumid.getText());
        double qty = Double.parseDouble(txtQty.getText());
        double unit = Double.parseDouble(txtunit.getText());
        String suppId= (String) cmbSupId.getValue();

        var dto = new LettuceDto(id,name,temp,humid,qty,unit,suppId);

        try {
            boolean isUpdated =lettModel.updateLettuce(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION,"Updated Successfully").show();
                initialize();
                clearFields();
            } else{
                new Alert(Alert.AlertType.WARNING,"Update Failed").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtTemp.setText("");
        txtHumid.setText("");
        txtQty.setText("");
        txtunit.setText("");
        cmbSupId.setValue("");
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