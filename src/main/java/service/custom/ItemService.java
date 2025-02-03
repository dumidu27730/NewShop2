package service.custom;

import javafx.collections.ObservableList;
import dto.Item;
import service.SuperService;

public interface ItemService extends SuperService {

    boolean addItem(Item item);

    boolean deleteItem(String id);

    ObservableList<Item> getAll();

    boolean updateItem(Item item);

    Item searchItem(String itemCode);
}
