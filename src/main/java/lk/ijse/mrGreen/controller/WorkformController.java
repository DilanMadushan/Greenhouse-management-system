package lk.ijse.mrGreen.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.mrGreen.dto.EmployeeDto;
import lk.ijse.mrGreen.dto.GreenHouseDto;
import javafx.scene.control.DatePicker;
import lk.ijse.mrGreen.dto.WorkDto;
import lk.ijse.mrGreen.dto.tm.EmployeeTm;
import model.EmployeeModel;
import model.GreenHouseModel;
import model.WorkModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class WorkformController {

    @FXML
    private AnchorPane Anchor;

    @FXML
    private JFXComboBox cmbSupId;

    @FXML
    private JFXComboBox cmbGreen;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDesc;

    @FXML
    private TableColumn<?, ?> colG_id;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<?> tblWork;

    @FXML
    private JFXTextArea txtDesc;

    @FXML
    private JFXTextField txtG_id;

    @FXML
    private JFXTextField txtName;

    @FXML
    private DatePicker datePikker;

    EmployeeModel empModel = new EmployeeModel();

    GreenHouseModel greenModel = new GreenHouseModel();

    WorkModel workModel = new WorkModel();

    public void initialize(){
        loadAllEmployeeNames();
        loadAllGreenhouses();
    }

    private void loadAllGreenhouses() {
        ObservableList<String> oblist =FXCollections.observableArrayList();

        try {
            List<GreenHouseDto> dtoList = greenModel.getAllGreenhouse();

            for (GreenHouseDto dto: dtoList) {
                oblist.add(dto.getId());
            }
            cmbGreen.setItems(oblist);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadAllEmployeeNames() {
        ObservableList<String> oblist =FXCollections.observableArrayList();

        try {
            List<EmployeeDto> dtoList = empModel.getAllEmployee();

            for (EmployeeDto dto: dtoList) {
                oblist.add(dto.getId());
            }
            cmbSupId.setItems(oblist);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addOnAction(ActionEvent event) {
        String empId = (String) cmbSupId.getValue();
        String greenId =(String) cmbGreen.getValue();
        String date = String.valueOf(datePikker.getValue());
        String desc = txtDesc.getText();

        WorkDto workDto = new WorkDto(empId,greenId,date,desc);

        try {
            boolean isSaved = workDto.saveWork(workDto);

            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION,"Added Successfully").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Added Failed").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void removeOnAction(ActionEvent event) {

    }

    @FXML
    void supplierOnAction(ActionEvent event) {
        String id = (String) cmbSupId.getValue();
        try {
            txtName.setText(empModel.getEmployeeName(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void updateOnAction(ActionEvent event) {

    }

    @FXML
    void backOnAction(MouseEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/DashBoard.fxml"));
        Stage stage = (Stage) Anchor.getScene().getWindow();

        Scene scene=new Scene(rootNode);
        stage.setScene(scene);
        stage.centerOnScreen();
    }

}
