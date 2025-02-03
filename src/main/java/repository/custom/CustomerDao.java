package repository.custom;

import entity.CustomerEntity;
import repository.CrudDao;

import java.util.List;

public interface CustomerDao extends CrudDao<CustomerEntity,String> {
    List<String> getIds();
}
