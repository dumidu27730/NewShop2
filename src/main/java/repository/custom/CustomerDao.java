package repository.custom;

import entity.CustomerEntity;
import javafx.collections.ObservableList;
import repository.CrudDao;

import java.util.List;

public interface CustomerDao extends CrudDao<CustomerEntity,String> {
    ObservableList<String> getIds();
}
