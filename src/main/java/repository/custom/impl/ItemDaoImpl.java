package repository.custom.impl;

import dto.Item;
import repository.custom.ItemDao;

import java.util.List;

public class ItemDaoImpl implements ItemDao {
    @Override
    public List<String> getIds() {
        return List.of();
    }

    @Override
    public boolean save(Item entity) {
        return false;
    }

    @Override
    public Item search(String s) {
        return null;
    }

    @Override
    public boolean delete(String s) {
        return false;
    }

    @Override
    public boolean update(Item entity) {
        return false;
    }

    @Override
    public List<Item> getAll() {
        return List.of();
    }
}
