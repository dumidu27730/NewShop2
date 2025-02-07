package repository.custom.impl;

import db.DBConnection;
import dto.Customer;
import entity.CustomerEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repository.custom.CustomerDao;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public ObservableList<String> getIds() {
        return null;
    }

    @Override
    public boolean save(CustomerEntity entity) {
        try {
            String SQL = "INSERT INTO customer VALUES(?,?,?,?)";
            return CrudUtil.execute(SQL,
                    entity.getId(),
                    entity.getName(),
                    entity.getAddress(),
                    entity.getSalary()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CustomerEntity search(String s) {
        String SQL = "SELECT * FROM customer WHERE id=? || name=?";

        try {
            ResultSet resultSet = CrudUtil.execute(SQL, s,s);

            if(resultSet.next()) {
                return new CustomerEntity(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        Double.parseDouble(resultSet.getString(4))


                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public boolean delete(String s) {
        String SQL = "DELETE FROM customer WHERE id = ?";
        try {
            return CrudUtil.execute(SQL,s);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(CustomerEntity entity) {
        String SQL = "UPDATE customer SET  name=?, address=?, salary=? WHERE id=? ";
        try {
            return CrudUtil.execute(SQL,
                    entity.getName(),
                    entity.getAddress(),
                    entity.getSalary(),
                    entity.getId());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<CustomerEntity> getAll() {

        ObservableList<CustomerEntity> employeeEntityObservableList = FXCollections.observableArrayList();
        try {
            String SQL = "SELECT * FROM customer";
            ResultSet resultSet = CrudUtil.execute(SQL);
            while (resultSet.next()) {
                CustomerEntity CustomerEntity = new CustomerEntity(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4)
                );
                employeeEntityObservableList.add(CustomerEntity);
            }
            return employeeEntityObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
