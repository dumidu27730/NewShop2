package service.custom.impl;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import dto.Item;
import dto.OrderDetail;
import service.custom.ItemService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemServiceImpl implements ItemService {

    private static ItemServiceImpl instance;

    private ItemServiceImpl() {
    }

    public static ItemServiceImpl getInstance() {
        return instance == null ? instance = new ItemServiceImpl() : instance;
    }
    @Override
    public boolean addItem(Item item) {
        String SQl = "INSERT INTO item VALUES(?,?,?,?)";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQl);
            psTm.setObject(1,item.getCode());
            psTm.setObject(2,item.getDescription());
            psTm.setObject(3,item.getPrice());
            psTm.setObject(4,item.getQty());

            return psTm.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean deleteItem(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            return connection.createStatement().executeUpdate("DELETE FROM item WHERE code='"+id+"'")>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Item> getAll() {
        String SQL = "SELECT * FROM item";
        ObservableList<Item> itemObservableList = FXCollections.observableArrayList();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();
            while (resultSet.next()){
                itemObservableList.add(new Item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getInt(4)
                ));
            }
            return itemObservableList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateItem(Item item) {

        String  SQL ="UPDATE item SET description=?,unitPrice=?,qtyOnHand=? WHERE Code=?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,item.getDescription());
            psTm.setObject(2,item.getPrice());
            psTm.setObject(3,item.getQty());
            psTm.setObject(4,item.getCode());

            return psTm.executeUpdate()>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Item searchItem(String itemCode) {
        String SQL = "SELECT * FROM item WHERE code='" + itemCode + "'";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();
            while (resultSet.next()) {
                return new Item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getInt(4)
                );

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public ObservableList<String> getItemIds(){
        ObservableList<String> itemIdsList = FXCollections.observableArrayList();
        getAll().forEach(Item->
                itemIdsList.add(Item.getCode()) );

        return itemIdsList;
    }

    public boolean updateStock(List<OrderDetail> orderDetails){
        for (OrderDetail orderDetail : orderDetails){
            boolean isUpdateStock = updateStock(orderDetail);
            if(!isUpdateStock){
                return false;
            }
        }
        return true;

    }
    public  boolean updateStock(OrderDetail orderDetail){
        String SQL = "UPDATE item SET qtyOnHand = qtyOnHand-?  WHERE code=?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,orderDetail.getQty());
            psTm.setString(2,orderDetail.getItemCode());
            return psTm.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
