package lk.ijse.mrGreen.controller;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.mrGreen.dto.CustomerDto;
import lk.ijse.mrGreen.dto.LettuceDto;
import lk.ijse.mrGreen.dto.PlaceOrderDto;
import model.PlaceOrderModel;
import lk.ijse.mrGreen.dto.tm.CartTm;
import model.CustomerModel;
import model.LettuceModel;
import model.OrderModel;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderFormController {

    public Text oId;
    public JFXTextField txtUnit;
    @FXML
    private AnchorPane Anchor;

    @FXML
    private JFXButton btnAddTocart;

    @FXML
    private JFXComboBox cmbLettId;

    @FXML
    private JFXComboBox cmdCustomerId;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnit;

    @FXML
    private DatePicker datePikker;

    @FXML
    private TableView<CartTm> tblOrder;

    @FXML
    private JFXTextField txtCusName;

    @FXML
    private JFXTextField txtLettName;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private Text txtTotal;

    Integer index;


    private OrderModel orderModel = new OrderModel();

    private CustomerModel cusModel = new CustomerModel();

    private LettuceModel lettuceModel = new LettuceModel();
    private ObservableList<CartTm> obList =FXCollections.observableArrayList();

    private PlaceOrderModel placeOrderModel = new PlaceOrderModel();

    public void initialize(){
        genarateOrderId();
        setCustomerId();
        setLettuceId();
        datePikker.setValue(LocalDate.now());
        setCellValues();
    }

    private void setCellValues() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

    }


    private void setLettuceId() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<LettuceDto> dtoList = lettuceModel.getAllLettuceDetails();
            for (LettuceDto dto: dtoList) {
                obList.add(dto.getId());
            }
            cmbLettId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCustomerId() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<CustomerDto> dtoList = cusModel.loadAllCustomer();
            for (CustomerDto dto: dtoList) {
                obList.add(dto.getId());
            }
            cmdCustomerId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void genarateOrderId() {
        try {
            String id = orderModel.genarateOrderId();
            oId.setText(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addToCartOnAction(ActionEvent event) {
        String id = (String) cmbLettId.getValue();
        String name = txtLettName.getText();

        if(Double.parseDouble(txtQtyOnHand.getText())<Double.parseDouble(txtQty.getText())){
            new Alert(Alert.AlertType.ERROR,"out of Stock").show();
            return;
        }
        double qty =Double.parseDouble(txtQty.getText());
        double unit = Double.parseDouble(txtUnit.getText());
        double total = unit * qty;

        var orderList = new CartTm(id,name,qty,unit,total);

        if(!obList.isEmpty()){

            for (int i = 0; i < tblOrder.getItems().size(); i++) {
                if(colId.getCellData(i).equals(id)){
                    double col_qty = (double) colQty.getCellData(i);
                    qty += col_qty;
                    total = unit * qty;

                    obList.get(i).setQty(qty);
                    obList.get(i).setTotal(total);

                    calculateTotal();
                    tblOrder.refresh();
                    return;
                }
            }

        }

        obList.add(orderList);


        tblOrder.setItems(obList);
        calculateTotal();
        txtQty.clear();

    }
    private void calculateTotal() {
        double total = 0;
        for(int i = 0; i < tblOrder.getItems().size(); i++){
            total+=(double)colTotal.getCellData(i);
        }
        txtTotal.setText(Double.toString(total));
    }

    @FXML
    void backOnAction(MouseEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/DashBoard.fxml"));
        Stage stage = (Stage) Anchor.getScene().getWindow();

        Scene scene=new Scene(rootNode);
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void placeOnAction(ActionEvent event) {
        String orderId = oId.getText();
        LocalDate date = datePikker.getValue();
        String cus_id = (String) cmdCustomerId.getValue();

        List<CartTm> cartTmList = new ArrayList<>();
        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            CartTm cartTm = obList.get(i);

            cartTmList.add(cartTm);
        }
        var placeOrderDto = new PlaceOrderDto(orderId,date,cus_id,cartTmList);

        try {
            boolean isSuccess = placeOrderModel.placeOrder(placeOrderDto);
            if (isSuccess) {
                new Alert(Alert.AlertType.CONFIRMATION,"Order Success").show();
            }else{
                new Alert(Alert.AlertType.ERROR,"Order Failed").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        clearAll();


    }

    @FXML
    void removeOnAction(ActionEvent event) {
        int focusedIndex = tblOrder.getSelectionModel().getSelectedIndex();

        obList.remove(focusedIndex);
        tblOrder.refresh();
        calculateTotal();

    }

    public void lettIdOnAction(ActionEvent actionEvent) {
        String id = (String) cmbLettId.getValue();

        try {
            LettuceDto dtoList = lettuceModel.getLettueDetails(id);

            txtLettName.setText(dtoList.getName());
            txtQtyOnHand.setText(Double.toString(dtoList.getQty()));
            txtUnit.setText(Double.toString(dtoList.getUnit()));

        } catch (Exception e) {

        }

    }

    public void cusIdOnAction(ActionEvent actionEvent) {
        String id = (String) cmdCustomerId.getValue();

        try {
            String Name = cusModel.getName(id);
            txtCusName.setText(Name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void mouseClickOnAction(MouseEvent mouseEvent) {


    }

    public void qtyOnAction(ActionEvent actionEvent) {
        addToCartOnAction(actionEvent);
    }

    public void clearAll(){
        cmdCustomerId.setValue("");
        cmbLettId.setValue("");
        txtCusName.clear();
        txtLettName.clear();
        txtQtyOnHand.clear();
        txtUnit.clear();
        obList.clear();
    }
}
