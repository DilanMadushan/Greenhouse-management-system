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
import lk.ijse.mrGreen.dto.SupplierDto;
import lk.ijse.mrGreen.dto.UserDto;
import lk.ijse.mrGreen.dto.tm.SupplierTm;
import model.SupplierModel;
import model.UserModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class SupplierFormController {

    @FXML
    private AnchorPane Anchor;

    @FXML
    private JFXComboBox cmbUserId;

    @FXML
    private TableColumn<?, ?> colCompany;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPhone;

    @FXML
    private TableColumn<?, ?> colUser;

    @FXML
    private TableView<SupplierTm> tblSupplier;

    @FXML
    private JFXTextField txtCompany;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPhone;

    UserModel userModel = new UserModel();

    SupplierModel supModel = new SupplierModel();

    public void initialize(){
        loadAllUseres();
        setCellValues();
        loadAllSuppliers();
    }

    private void loadAllSuppliers() {
        ObservableList<SupplierTm> obList= FXCollections.observableArrayList();

        try {
            List<SupplierDto> dtoList = supModel.loadAllSupplier();
            for (SupplierDto dto: dtoList) {
                obList.add(new SupplierTm(
                        dto.getSup_id(),
                        dto.getName(),
                        dto.getCompany(),
                        dto.getTel(),
                        dto.getUser_id()
                ));
            }
            tblSupplier.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValues() {
        colId.setCellValueFactory(new PropertyValueFactory<>("sup_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colUser.setCellValueFactory(new PropertyValueFactory<>("user_id"));
    }

    private void loadAllUseres() {
        ObservableList<String> supList = FXCollections.observableArrayList();

        try {
            List<UserDto> dtoList = userModel.loadAllUseres();
            for (UserDto dto : dtoList) {
                supList.add(dto.getId());
            }
            cmbUserId.setItems(supList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addOnAction(ActionEvent event) {

        boolean isValead = validateSupplier();

        if (isValead) {

            String id = txtId.getText();
            String name = txtName.getText();
            String company = txtCompany.getText();
            int tel = Integer.parseInt(txtPhone.getText());
            String userId = (String) cmbUserId.getValue();

            var dto = new SupplierDto(id, name, company, tel, userId);

            try {
                boolean isSaved = supModel.saveSupplier(dto);

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Added Successfully").show();
                    initialize();
                    clearFealds();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Added Failed").show();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private boolean validateSupplier() {
        String id = txtId.getText();
        boolean idMatch = Pattern.matches("[S]\\d{3,}",id);
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

        String company = txtCompany.getText();
        boolean companyMatch = Pattern.matches("[A-za-z\\s\\d]{3,}",company);
        if (!companyMatch) {
            new Alert(Alert.AlertType.ERROR,"invalid company").show();
            return false;
        }

        //int tel = Integer.parseInt(txtPhone.getText());
        boolean telMatch = Pattern.matches("[0-9]{10}",txtPhone.getText());
        if (!telMatch) {
            new Alert(Alert.AlertType.ERROR,"invalid tel").show();
            return false;
        }
        String userId = (String) cmbUserId.getValue();
        if(userId==null){
            new Alert(Alert.AlertType.ERROR,"user id is empty").show();
            return false;
        }

        return true;
    }

    @FXML
    void removeOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            boolean isDelete = supModel.deleteSupplier(id);
            if (isDelete) {
                new Alert(Alert.AlertType.CONFIRMATION,"Delete Successfully").show();
                initialize();
                clearFealds();
            }else {
                new Alert(Alert.AlertType.WARNING,"Delete Failed").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void updateOnAction(ActionEvent event) {

        boolean isValead = validateSupplier();

        if (isValead) {

            String id = txtId.getText();
            String name = txtName.getText();
            String company = txtCompany.getText();
            int tel = Integer.parseInt(txtPhone.getText());
            String userId = (String) cmbUserId.getValue();

            System.out.println(tel);

            var dto = new SupplierDto(id, name, company, tel, userId);

            try {
                boolean isUpdated = supModel.updateSupplier(dto);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Update Successfully").show();
                    initialize();
                    clearFealds();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Update Failed").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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

    public void clearFealds(){
        txtId.setText("");
        txtName.setText("");
        txtCompany.setText("");
        txtPhone.setText("");
        cmbUserId.setValue("");
    }

    public void mouseClickOnAction(MouseEvent mouseEvent) {
        Integer index = tblSupplier.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtId.setText(colId.getCellData(index).toString());
        txtName.setText(colName.getCellData(index).toString());
        txtCompany.setText(colCompany.getCellData(index).toString());
        txtPhone.setText(colPhone.getCellData(index).toString());
        cmbUserId.setValue(colUser.getCellData(index).toString());
    }

    public void suppIdOnAction(ActionEvent event) {
        txtName.requestFocus();
    }

    public void nameOnAction(ActionEvent event) {
        txtCompany.requestFocus();
    }

    public void companyOnAction(ActionEvent event) {
        txtPhone.requestFocus();
    }

    public void phoneOnAction(ActionEvent event) {
        cmbUserId.requestFocus();
    }
}
