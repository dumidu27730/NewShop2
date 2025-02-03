package controller.order;


import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import dto.*;
import service.ServiceFactory;
import service.custom.OrderService;
import service.custom.impl.CustomerServiceImpl;
import service.custom.impl.ItemServiceImpl;
import service.custom.impl.OrderServiceImpl;
import util.ServiceType;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {

    public ComboBox cmbCustomerId;
    public ComboBox cmbItemCode;
    public JFXTextField txtAddress;
    public JFXTextField txtSalary;
    public JFXTextField txtName;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtDescription;
    public JFXTextField txtQty;
    public TableView tblCart;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public JFXTextField txtNetTotal;
    public Label lblNetTotal;
    public TextField txtOrderId;
    @FXML
    private Label lblDate;


    OrderService service = ServiceFactory.getInstance().getServiceType(ServiceType.ORDER);



    @FXML
    private Label lblTime;

    private void loadDateAndTime() {
       Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime cTime = LocalTime.now();
            lblTime.setText(
                    cTime.getHour() + ":" + cTime.getMinute() + ":" + cTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();

    }
    private void loadCustomerIds(){

       CustomerServiceImpl customer= CustomerServiceImpl.getInstance();

        cmbCustomerId.setItems(customer.getCustomerIds());
    }

    private void loadItemIds(){

        ItemServiceImpl item  =ItemServiceImpl.getInstance();

        cmbItemCode.setItems(item.getItemIds());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        loadCustomerIds();
        loadDateAndTime();
        loadItemIds();


        cmbCustomerId.getSelectionModel()
                .selectedItemProperty()
                .addListener((observableValue, oldValue, newValue) -> {

                    if (newValue!=null){
                        setCustomerDataToText((String) newValue);
                        System.out.println(newValue);
                    }

                });

        cmbItemCode.getSelectionModel()
                .selectedItemProperty()
                .addListener((observableValue, oldValue, newValue) -> {

                    if (newValue!=null){
                        setCustomerDataToText1((String) newValue);
                        System.out.println(newValue);
                    }

                });
    }

    private void setCustomerDataToText(String id) {
        CustomerServiceImpl customerController = CustomerServiceImpl.getInstance();

        Customer customer = customerController.searchCustomer(id);

        txtName.setText(customer.getName());
        txtAddress.setText(customer.getAddress());
        txtSalary.setText(customer.getSalary().toString());

    }

    private void setCustomerDataToText1(String id) {
        ItemServiceImpl itemController = ItemServiceImpl.getInstance();
        Item item = itemController.searchItem(id);
        txtDescription.setText(item.getDescription());
        txtUnitPrice.setText(item.getPrice().toString());
        txtQtyOnHand.setText(item.getQty().toString());

    }

    ObservableList<CartTM> cartTMObservableList = FXCollections.observableArrayList();


    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        Double total = Double.parseDouble(txtUnitPrice.getText()) * Integer.parseInt(txtQty.getText());
        cartTMObservableList.add(
                new CartTM(
                        cmbItemCode.getValue().toString(),
                        txtDescription.getText(),
                        Integer.parseInt(txtQty.getText()),
                        Double.parseDouble(txtUnitPrice.getText()),
                        total
                )
        );


        tblCart.setItems(cartTMObservableList);

        calcNetTotal();
    }

    private void calcNetTotal(){
        Double netTotal=0.0;
        for (CartTM cartTM: cartTMObservableList){
            netTotal+=cartTM.getTotal();
        }
        lblNetTotal.setText(netTotal.toString());
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {

        String orderId = txtOrderId.getText();
        String date = lblDate.getText();
        String customerId = cmbCustomerId.getValue().toString();

        List<OrderDetail> orderDetailList = new ArrayList<>();

        cartTMObservableList.forEach(cartTM -> {
            orderDetailList.add(new OrderDetail(
                    txtOrderId.getText(),
                    cartTM.getItemCode(),
                    cartTM.getQty(),
                    cartTM.getUnitPrice()
            ));
        });

        try {
            boolean isPlaceOrder = new OrderServiceImpl().placeOrder(new Order(orderId, date, customerId, orderDetailList));
            if (isPlaceOrder){
                new Alert(Alert.AlertType.INFORMATION,"Order Placed!!").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Order Not Placed!!").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void btnCommitOnAction(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
