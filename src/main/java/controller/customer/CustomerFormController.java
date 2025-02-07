package controller.customer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.Customer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import service.ServiceFactory;
import service.custom.CustomerService;
import util.ServiceType;

import java.net.URL;
import java.util.ResourceBundle;


public class CustomerFormController implements Initializable {

    @FXML
    public TableColumn colSalary;

    public TableColumn colAction;

    public JFXButton btnUpdate;
    public JFXButton btnDelete;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<Customer> tblCustomers;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtSalary;


    CustomerService service = ServiceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);
    @FXML
    void btnAddOnAction(ActionEvent event) {
        Customer customer= new Customer(
                txtId.getText(),
                txtName.getText(),
                txtAddress.getText(),
                Double.parseDouble(txtSalary.getText())
        );

        if(service.addCustomer(customer)){
            new Alert(Alert.AlertType.INFORMATION,"Customer Add Success").show();
            loadTable();
        }else{
            new Alert(Alert.AlertType.ERROR,"Customer Not Add").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (service.deleteCustomer(txtId.getText())){
            new Alert(Alert.AlertType.INFORMATION).show();
            loadTable();
        }else {
            new Alert(Alert.AlertType.ERROR).show();

        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        Customer customer = service.searchCustomer(txtId.getText());
        if (customer != null) {
            setTextToValues(customer);
        } else {
            new Alert(Alert.AlertType.WARNING, "Customer Not Found!").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        Customer customer = new Customer(
                txtId.getText(),
                txtName.getText(),
                txtAddress.getText(),
                Double.parseDouble(txtSalary.getText())
        );

        if (service.upadateCustomer(customer)){
            new Alert(Alert.AlertType.INFORMATION,"Customer Updated!!").show();
            loadTable();
        }else {
            new Alert(Alert.AlertType.ERROR,"Customer Not Updated!!").show();

        }
    }

    private void loadTable(){
        ObservableList<Customer> all = service.getAll();
        tblCustomers.setItems(all);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        // Add Update & Delete buttons in Action Column
        colAction.setCellFactory(param -> new TableCell<>() {
            private final JFXButton btnUpdate = new JFXButton("Update");
            private final JFXButton btnDelete = new JFXButton("Delete");
            private final HBox actionButtons = new HBox(10, btnUpdate, btnDelete); // HBox with spacing

            {
                // Styling (Optional)
                btnUpdate.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                btnDelete.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white;");

                // Update Button Action (Fill text fields with row data)
                btnUpdate.setOnAction(event -> {
                    Customer customer = new Customer(
                            txtId.getText(),
                            txtName.getText(),
                            txtAddress.getText(),
                            Double.parseDouble(txtSalary.getText())
                    );

                    if (service.upadateCustomer(customer)){
                        new Alert(Alert.AlertType.INFORMATION,"Customer Updated!!").show();
                        loadTable();
                    }else {
                        new Alert(Alert.AlertType.ERROR,"Customer Not Updated!!").show();

                    }
                    Customer customer1 = (Customer) getTableView().getItems().get(getIndex());
                    setTextToValues(customer);
                });

                // Delete Button Action (Remove customer from list)
                btnDelete.setOnAction(event -> {
                    Customer customer = (Customer) getTableView().getItems().get(getIndex());
                    boolean isDeleted = service.deleteCustomer(customer.getId()); // Call delete service

                    if (isDeleted) {
                        new Alert(Alert.AlertType.INFORMATION, "Customer Deleted!").show();
                        loadTable(); // Refresh table after deletion
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to Delete Customer!").show();
                    }
                });
            }

            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(actionButtons);
                }
            }
        });

        loadTable();

        // Handle row selection
        tblCustomers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setTextToValues(newValue);
            }
        });
    }


    //
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
//        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
//        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
//        colSalary .setCellValueFactory(new PropertyValueFactory<>("salary"));
//        colAction.setCellValueFactory(new PropertyValueFactory<>("btnUpdate"));
//
//        loadTable();
//
//
//        tblCustomers.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
//            if(newValue!=null){
//                setTextToValues(newValue);
//            }
//
//        }));
//    }
    private void setTextToValues(Customer newValue) {
        txtId.setText(newValue.getId());
        txtName.setText(newValue.getName());
        txtAddress.setText(newValue.getAddress());
        txtSalary.setText(newValue.getSalary().toString());
    }


    public void editOnMouseClick(MouseEvent mouseEvent) {
    }

    public void deleteOnMouseAction(MouseEvent mouseEvent) {
    }
}
