package repository.custom;

import dto.Item;
import repository.CrudDao;

import java.util.List;

public interface ItemDao extends CrudDao<Item,String> {
    List<String> getIds();
}
