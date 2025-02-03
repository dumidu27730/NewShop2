package service.custom.impl;

import db.DBConnection;
import entity.CustomerEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import dto.Customer;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.CustomerDao;
import service.custom.CustomerService;
import util.DaoType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private static CustomerServiceImpl instance;

    private CustomerServiceImpl() {
    }

    public static CustomerServiceImpl getInstance() {
        return instance == null ? instance = new CustomerServiceImpl() : instance;
    }
    CustomerDao customerDao = DaoFactory.getInstance().getDaoType(DaoType.CUSTOMER);


    @Override
    public ObservableList<Customer> getAll() {
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
        List<CustomerEntity> customerEntities = customerDao.getAll();
       // ArrayList<Customer> customerArrayList = new ArrayList<>();

        customerEntities.forEach(customerEntity -> {
            customerObservableList.add(new ModelMapper().map(customerEntity, Customer.class));
        });

        return customerObservableList;
    }

    @Override
    public boolean addCustomer(Customer customer) {
        CustomerEntity entity = new ModelMapper().map(customer, CustomerEntity.class);
        return customerDao.save(entity);
    }

    @Override
    public boolean upadateCustomer(Customer customer) {
        String  SQL ="UPDATE customer SET name=?,address=?,salary=? WHERE id=?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,customer.getName());
            psTm.setObject(2,customer.getAddress());
            psTm.setObject(3,customer.getSalary());
            psTm.setObject(4,customer .getId());

            return psTm.executeUpdate()>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteCustomer(String customerId) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            return connection.createStatement().executeUpdate("DELETE FROM customer WHERE code='"+customerId+"'")>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer searchCustomer(String customerId) {
        String SQL = "SELECT * FROM customer WHERE id='" + customerId + "'";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();
            while (resultSet.next()) {
                return new Customer(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4)
                );

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public ObservableList<String> getCustomerIds(){
        ObservableList<String> customerIdsList = FXCollections.observableArrayList();
        getAll().forEach(Customer ->
                customerIdsList.add(Customer.getId()) );

        return customerIdsList;
    }
}
