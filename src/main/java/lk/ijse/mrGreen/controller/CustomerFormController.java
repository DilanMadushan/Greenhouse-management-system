package lk.ijse.mrGreen.controller;

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
import lk.ijse.mrGreen.dto.CustomerDto;
import lk.ijse.mrGreen.dto.tm.CustomerTm;
import model.CustomerModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class CustomerFormController {

    @FXML
    private AnchorPane Anchor;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPhone;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPhone;

    CustomerModel cusmodel = new CustomerModel();

    public void initialize(){
        setCellValues();
        loadAllCustomer();
    }

    private void loadAllCustomer() {
        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        try {
            List<CustomerDto> dtoList =cusmodel.loadAllCustomer();

            for (CustomerDto dto: dtoList) {
                obList.add(new CustomerTm(
                        dto.getId(),
                        dto.getName(),
                        dto.getAddress(),
                        dto.getTel()
                ));
            }
            tblCustomer.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValues() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }

    @FXML
    void addOnAction(ActionEvent event) {

        boolean isValead = validateCustomer();

        if (isValead) {

            String id = txtId.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String tel = txtPhone.getText();

            CustomerDto dto = new CustomerDto(id, name, address, tel);

            try {
                boolean isSaved = cusmodel.saveCustomer(dto);

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Added Successfilly").show();
                    initialize();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Addes Failed").show();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean validateCustomer() {
        String id = txtId.getText();
        boolean idMatch = Pattern.matches("[C]\\d{3,}",id);
        if (!idMatch) {
            new Alert(Alert.AlertType.ERROR,"invalid id").show();
            return false;
        }

        String name = txtName.getText();
        boolean nameMAtch = Pattern.matches("[A-za-z\\s]{4,}",name);
        if (!nameMAtch) {
            new Alert(Alert.AlertType.ERROR,"invalid name").show();
            return false;
        }

        String address = txtAddress.getText();
        boolean addressMatch= Pattern.matches("[A-za-z]{3,}",address);
        if (!addressMatch) {
            new Alert(Alert.AlertType.ERROR,"invalid address").show();
            return false;
        }

       // int tel =Integer.parseInt(txtPhone.getText());
        boolean telMatch = Pattern.matches("[0-9]{10}",txtPhone.getText());
        if (!telMatch) {
            new Alert(Alert.AlertType.ERROR,"invalid tel").show();
            return false;
        }
        return true;
    }

    @FXML
    void removeOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            boolean isDelete = cusmodel.deleteCustomer(id);
            if (isDelete) {
                new Alert(Alert.AlertType.CONFIRMATION,"Delete Successfully").show();
                initialize();
                clearFields();
            }else{
                new Alert(Alert.AlertType.WARNING,"Delete failed").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        boolean isValead = validateCustomer();

        if (isValead) {

            String id = txtId.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String tel = txtPhone.getText();

            CustomerDto dto = new CustomerDto(id, name, address, tel);

            try {
                boolean isUpdated = cusmodel.updateCustomer(dto);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated Successfully").show();
                    initialize();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Update Failed").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtPhone.setText("");
    }
    @FXML
    void backOnAction(MouseEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/DashBoard.fxml"));
        Stage stage = (Stage) Anchor.getScene().getWindow();

        Scene scene=new Scene(rootNode);
        stage.setScene(scene);
        stage.centerOnScreen();
    }


    public void mouseClickOnAction(MouseEvent mouseEvent) {
        Integer index = tblCustomer.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtId.setText(colId.getCellData(index).toString());
        txtName.setText(colName.getCellData(index).toString());
        txtAddress.setText(colAddress.getCellData(index).toString());
        txtPhone.setText(colPhone.getCellData(index).toString());
    }

    public void nameOnAction(ActionEvent event) {
        txtAddress.requestFocus();
    }

    public void addressOnAction(ActionEvent event) {
        txtPhone.requestFocus();
    }

    public void idOnAction(ActionEvent event) {
       txtName.requestFocus();
    }
}
