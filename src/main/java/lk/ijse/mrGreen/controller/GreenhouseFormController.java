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
import lk.ijse.mrGreen.dto.GreenHouseDto;
import lk.ijse.mrGreen.dto.LettuceDto;
import lk.ijse.mrGreen.dto.tm.GreenHouseTm;
import model.GreenHouseModel;
import model.LettuceModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class GreenhouseFormController {

    @FXML
    private AnchorPane Anchor;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colLettuce;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPh;

    @FXML
    private TableColumn<?, ?> colTemp;

    @FXML
    private TableView<GreenHouseTm> tblGreen;
    @FXML
    private JFXComboBox cmbLettuce;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPh;

    @FXML
    private JFXTextField txtTemp;

    GreenHouseModel greenModel = new GreenHouseModel();

    LettuceModel lettModel = new LettuceModel();
    public void initialize(){
        loadAllLettuceID();
        selCellValues();
        setAllGreenhouses();
    }

    private void setAllGreenhouses() {
        ObservableList<GreenHouseTm> obList = FXCollections.observableArrayList();

        try {
            List<GreenHouseDto> dtoList = greenModel.getAllGreenhouse();
            for (GreenHouseDto dto: dtoList) {
                obList.add(new GreenHouseTm(
                        dto.getId(),
                        dto.getName(),
                        dto.getL_id(),
                        dto.getTemp(),
                        dto.getPh()
                ));
            }
            tblGreen.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void selCellValues() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colLettuce.setCellValueFactory(new PropertyValueFactory<>("l_id"));
        colTemp.setCellValueFactory(new PropertyValueFactory<>("temp"));
        colPh.setCellValueFactory(new PropertyValueFactory<>("ph"));
    }

    private void loadAllLettuceID() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<LettuceDto> lettuceDto= lettModel.getAllLettuceDetails();

            for (LettuceDto dto: lettuceDto) {
                obList.add(dto.getId());
            }
            cmbLettuce.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void addOnAction(ActionEvent event) {

        boolean isValead = validateGreenhouse();

        if (isValead) {

            String id = txtId.getText();
            String name = txtName.getText();
            String letId = (String) cmbLettuce.getValue();
            int temp = Integer.parseInt(txtTemp.getText());
            double ph = Double.parseDouble(txtPh.getText());

            var dto = new GreenHouseDto(id, name, letId, 0, temp, ph);

            try {
                boolean isSaved = greenModel.saveGreenhouse(dto);

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Added Successfully").show();
                    initialize();
                    clearFeilds();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Added Failed").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean validateGreenhouse() {
        String id = txtId.getText();
        boolean idMatch = Pattern.matches("[G]\\d{3,}",id);
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

        //int temp = Integer.parseInt(txtTemp.getText());
        boolean tempMatch = Pattern.matches("[0-9]{1,}",txtTemp.getText());
        if (!tempMatch) {
            new Alert(Alert.AlertType.ERROR,"invalid Temperature").show();
            return false;
        }

        //double ph = Double.parseDouble(txtPh.getText());
        boolean phMatch = Pattern.matches("[0-9.]{1,}",txtPh.getText());
        if (!phMatch) {
            new Alert(Alert.AlertType.ERROR,"invalid ph").show();
            return false;
        }
        String letId = (String) cmbLettuce.getValue();
        if(letId==null){
            new Alert(Alert.AlertType.ERROR,"Lettuce id is empty").show();
            return false;
        }

        return true;

    }

    @FXML
    void removeOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            boolean isDelete= greenModel.deleteGreenhouse(id);

            if (isDelete) {
                new Alert(Alert.AlertType.CONFIRMATION,"Delete Successfully").show();
                initialize();
                clearFeilds();
            }else {
                new Alert(Alert.AlertType.WARNING,"Delete Failed").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void updateOnAction(ActionEvent event) {

        boolean isValead = validateGreenhouse();

        if (isValead) {

            String id = txtId.getText();
            String name = txtName.getText();
            String letId = (String) cmbLettuce.getValue();
            int temp = Integer.parseInt(txtTemp.getText());
            double ph = Double.parseDouble(txtPh.getText());

            var dto = new GreenHouseDto(id, name, letId, 0, temp, ph);

            try {
                boolean isUpdated = greenModel.updateGreenhouse(dto);

                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Update Successfully").show();
                    initialize();
                    clearFeilds();
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

    private void clearFeilds() {
        txtId.setText("");
        txtName.setText("");
        cmbLettuce.setValue("");
        txtTemp.setText("");
        txtPh.setText("");
    }


    public void onMouseClick(MouseEvent mouseEvent) {
        Integer index = tblGreen.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }

        txtId.setText(colId.getCellData(index).toString());
        txtName.setText(colName.getCellData(index).toString());
        cmbLettuce.setValue(colLettuce.getCellData(index).toString());
        txtTemp.setText(colTemp.getCellData(index).toString());
        txtPh.setText(colPh.getCellData(index).toString());
    }
}
