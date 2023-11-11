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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.mrGreen.dto.EmployeeDto;
import lk.ijse.mrGreen.dto.tm.EmployeeTm;
import model.EmployeeModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EmployeeFormController {

    @FXML
    private AnchorPane Anchor;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colAge;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colJob;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colSalary;


    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtAge;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtJob;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtSalary;

    EmployeeModel empModel = new EmployeeModel();

    public void initialize(){
        setCellValues();
        loadAllEpmloyee();
    }

    public void clearFields(){
        colId.setText("");
        colName.setText("");
        colAge.setText("");
        colAddress.setText("");
        colJob.setText("");
        colSalary.setText("");
    }

    private void loadAllEpmloyee() {
        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDto> dtoList = empModel.getAllEmployee();

            for (EmployeeDto dto: dtoList) {
                obList.add(new EmployeeTm(
                        dto.getId(),
                        dto.getName(),
                        dto.getAge(),
                        dto.getAddress(),
                        dto.getJob(),
                        dto.getSalary()
                ));
            }
            tblEmployee.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValues() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colJob.setCellValueFactory(new PropertyValueFactory<>("job"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }

    @FXML
    void addOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name =txtName.getText();
        int age =Integer.parseInt(txtAge.getText());
        String address =txtAddress.getText();
        String job=txtJob.getText();
        double salary=Double.parseDouble(txtSalary.getText());

        var dto = new EmployeeDto(id,name,age,address,job,salary);

        try {
            boolean isSaved = empModel.saveEmployee(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION,"Added Succesfully").show();
                initialize();
                clearFields();
            }else{
                new Alert(Alert.AlertType.WARNING,"Added Failed").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void removeOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            boolean isDeleted = empModel.deleteEmployee(id);
            if (isDeleted) {
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
        String name =txtName.getText();
        int age =Integer.parseInt(txtAge.getText());
        String address =txtAddress.getText();
        String job=txtJob.getText();
        double salary=Double.parseDouble(txtSalary.getText());

        var dto = new EmployeeDto(id,name,age,address,job,salary);

        try {
            boolean isSaved = empModel.updateEmployee(dto);

            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION,"Updated Successfully").show();
                initialize();
                clearFields();
            }else {
                new Alert(Alert.AlertType.WARNING,"Updated failed").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


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
