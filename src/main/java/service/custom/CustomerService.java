package service.custom;

import javafx.collections.ObservableList;
import dto.Customer;
import service.SuperService;

public interface CustomerService extends SuperService {

    ObservableList<Customer> getAll();

    boolean addCustomer(Customer customer);

    boolean upadateCustomer(Customer customer);

    boolean deleteCustomer(String customerId);

    Customer searchCustomer(String customerId);
}
