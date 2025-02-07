package service.custom.impl;

import entity.CustomerEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import dto.Customer;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.CustomerDao;
import service.custom.CustomerService;
import util.DaoType;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private static CustomerServiceImpl instance;

    private CustomerServiceImpl() {
    }

    public static CustomerServiceImpl getInstance() {
        return instance == null ? instance = new CustomerServiceImpl() : instance;
    }
    CustomerDao customerDao = DaoFactory.getInstance().getDaoType(DaoType.CUSTOMER);
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public ObservableList<Customer> getAll() {
        List<CustomerEntity> customerEntityDetails = customerDao.getAll();

        ObservableList<Customer> customers = FXCollections.observableArrayList();
        for (CustomerEntity entity : customerEntityDetails) {
            Customer customer = new ModelMapper().map(entity, Customer.class);
            customers.add(customer);
        }
        return customers;
    }

    @Override
    public boolean addCustomer(Customer customer) {
        CustomerEntity entity = new ModelMapper().map(customer, CustomerEntity.class);
        return customerDao.save(entity);
    }

    @Override
    public boolean upadateCustomer(Customer customer) {
        CustomerEntity entity = new ModelMapper().map(customer, CustomerEntity.class);
        return customerDao.update(entity);
    }

    @Override
    public boolean deleteCustomer(String customerId) {
        return customerDao.delete(customerId);
    }

    @Override
    public Customer searchCustomer(String customerId) {
        CustomerEntity customerEntity = customerDao.search(customerId);

        if (customerEntity != null) {
            return  modelMapper.map(customerEntity, Customer.class);
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
