package service.custom.impl;

import db.DBConnection;
import dto.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailServiceImpl {

    public boolean addOrderDetail(List<OrderDetail> orderDetails) {
        for (OrderDetail orderDetail : orderDetails) {
            boolean isOrderDetailAdd = addOrderDetail(orderDetail);
            if (!isOrderDetailAdd){
                return false;
            }
        }
        return true;
    }


    public boolean addOrderDetail(OrderDetail orderDetail) {
        String SQL = "INSERT INTO orderdetail VALUES (?,?,?,?)";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setString(1, orderDetail.getOrderId());
            psTm.setString(2, orderDetail.getItemCode());
            psTm.setObject(3, orderDetail.getQty());
            psTm.setObject(4, orderDetail.getUnitPrice());
            return psTm.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}
