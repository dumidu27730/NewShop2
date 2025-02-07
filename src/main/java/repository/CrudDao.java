package repository;

import javafx.collections.ObservableList;

import java.util.List;

public interface CrudDao <T,ID> extends SuperDao{
    boolean save(T entity);
    T search(ID id);
    boolean delete(ID id);
    boolean update(T entity);
    ObservableList<T> getAll();
}
