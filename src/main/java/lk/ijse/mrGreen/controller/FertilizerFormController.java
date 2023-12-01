package lk.ijse.mrGreen.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.mrGreen.dto.Fertilizerdto;
import lk.ijse.mrGreen.dto.LettuceDto;
import lk.ijse.mrGreen.dto.SupplierDto;
import lk.ijse.mrGreen.dto.tm.FertilizerTm;
import lk.ijse.mrGreen.dto.tm.LettuceTm;
import model.FertilizerModel;
import model.LettuceModel;
import model.SupplierModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class FertilizerFormController {

    @FXML
    private AnchorPane Anchor;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private JFXComboBox cmbLettId;

    @FXML
    private JFXComboBox cmbSupId;

    @FXML
    private TableColumn<?, ?> colCompany;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colLettId;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSupID;

    @FXML
    private TableColumn<?, ?> colUnit;

    @FXML
    private TableView<FertilizerTm> tblFertilizer;

    @FXML
    private JFXTextField txtCompany;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtUnit;

    SupplierModel suppModel = new SupplierModel();

    LettuceModel lettModel = new LettuceModel();

    FertilizerModel ferModel = new FertilizerModel();

    public void initialize(){
        loadAllSupplier();
        loadAllLettuceID();
        setCellValues();
        loadAllFertilizer();
    }

    private void loadAllFertilizer() {

        ObservableList<FertilizerTm> obList = FXCollections.observableArrayList();

        try {
            List<Fertilizerdto> dtoList = ferModel.getAllFertilizer();

            for (Fertilizerdto dto: dtoList) {
                obList.add(new FertilizerTm(
                        dto.getId(),
                        dto.getName(),
                        dto.getCompany(),
                        dto.getUnit(),
                        dto.getQty(),
                        dto.getSupId(),
                        dto.getLId()
                ));

            }
            tblFertilizer.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValues() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colSupID.setCellValueFactory(new PropertyValueFactory<>("supId"));
        colLettId.setCellValueFactory(new PropertyValueFactory<>("lId"));

    }

    private void loadAllLettuceID() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<LettuceDto> lettuceDto= lettModel.getAllLettuceDetails();

            for (LettuceDto dto: lettuceDto) {
                obList.add(dto.getId());
            }
            cmbLettId.setItems(obList);
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

        boolean isValead = validateFertilizer();

        if (isValead) {

            String id = txtId.getText();
            String name = txtName.getText();
            String company = txtCompany.getText();
            Double unit = Double.parseDouble(txtUnit.getText());
            int qty = Integer.parseInt(txtQty.getText());
            String supId = (String) cmbSupId.getValue();
            String l_id = (String) cmbLettId.getValue();


            var dto = new Fertilizerdto(id, name, company, unit, qty, supId, l_id);

            try {
                boolean isSaved = ferModel.SaveFertilizer(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Added Successfully").show();
                    initialize();
                    clerFelads();

                } else {
                    new Alert(Alert.AlertType.WARNING, "Added Failed").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private boolean validateFertilizer() {
        String id = txtId.getText();
        boolean idMatch = Pattern.matches("[F]\\d{3,}",id);
        if (!idMatch) {
            new Alert(Alert.AlertType.ERROR,"invalid id").show();
            return false;
        }

        String name = txtName.getText();
        boolean nameMAtch = Pattern.matches("[A-za-z\\s\\d]{1,}",name);
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

        //Double unit = Double.parseDouble(txtUnit.getText());
        boolean unitMatch=Pattern.matches("[0-9.]{1,}",txtUnit.getText());
        if (!unitMatch) {
            new Alert(Alert.AlertType.ERROR,"invalid unit price").show();
            return false;
        }

        //int qty = Integer.parseInt(txtQty.getText());
        boolean qtyMatch=Pattern.matches("[0-9.]{1,}",txtQty.getText());
        if (!qtyMatch) {
            new Alert(Alert.AlertType.ERROR,"invalid Qty").show();
            return false;
        }
        String supId = (String) cmbSupId.getValue();
        if(supId==null){
            new Alert(Alert.AlertType.ERROR,"supplier id is empty").show();
            return false;
        }

        String l_id = (String) cmbLettId.getValue();
        if (l_id==null) {
            new Alert(Alert.AlertType.ERROR,"lettuce id is empty").show();
            return false;
        }

        return true;


    }

    private void clerFelads() {
        txtId.setText("");
        txtName.setText("");
        txtCompany.setText("");
        txtUnit.setText("");
        txtQty.setText("");
        cmbSupId.setValue("");
        cmbLettId.setValue("");
    }

    @FXML
    void removeOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            boolean isDelete = ferModel.deleteFertilizer(id);
            if (isDelete) {
                new Alert(Alert.AlertType.CONFIRMATION,"Delete Successfully").show();
                initialize();
                clerFelads();
            }else{
                new Alert(Alert.AlertType.WARNING,"Delete Failed").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void updateOnAction(ActionEvent event) {

        boolean isValead = validateFertilizer();

        if (isValead) {

            String id = txtId.getText();
            String name = txtName.getText();
            String company = txtCompany.getText();
            Double unit = Double.parseDouble(txtUnit.getText());
            int qty = Integer.parseInt(txtQty.getText());
            String supId = (String) cmbSupId.getValue();
            String l_id = (String) cmbLettId.getValue();

            var dto = new Fertilizerdto(id, name, company, unit, qty, supId, l_id);

            try {
                boolean isUpdated = ferModel.updateFertilizer(dto);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated Successfully").show();
                    initialize();
                    clerFelads();
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

    public void mouseClickOnAction(MouseEvent mouseEvent) {
        Integer index = tblFertilizer.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtId.setText(colId.getCellData(index).toString());
        txtName.setText(colName.getCellData(index).toString());
        txtCompany.setText(colCompany.getCellData(index).toString());
        txtUnit.setText(colUnit.getCellData(index).toString());
        txtQty.setText(colQty.getCellData(index).toString());
        cmbSupId.setValue(colSupID.getCellData(index).toString());
        cmbLettId.setValue(colLettId.getCellData(index).toString());
    }

    public void nameOnAction(ActionEvent event) {
        txtCompany.requestFocus();
    }

    public void companyOnAction(ActionEvent event) {
        txtUnit.requestFocus();
    }

    public void fidOnAction(ActionEvent event) {
        txtName.requestFocus();
    }

    public void suppIdOnAction(ActionEvent event) {
        cmbLettId.requestFocus();
    }

    public void priceOnAction(ActionEvent event) {
        txtQty.requestFocus();
    }

    public void qtyOnAction(ActionEvent event) {
        cmbSupId.requestFocus();
    }
}