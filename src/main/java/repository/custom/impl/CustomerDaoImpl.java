package repository.custom.impl;

import entity.CustomerEntity;
import repository.custom.CustomerDao;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public List<String> getIds() {
        return List.of();
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
        return null;
    }

    @Override
    public boolean delete(String s) {
        return false;
    }

    @Override
    public boolean update(CustomerEntity entity) {
        return false;
    }

    @Override
    public List<CustomerEntity> getAll() {
        List<CustomerEntity> customerList = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM customer");
            while (resultSet.next()) {
                customerList.add(new CustomerEntity(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerList;
    }
}
