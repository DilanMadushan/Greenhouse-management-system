package model;

import lk.ijse.mrGreen.db.DbConnection;
import lk.ijse.mrGreen.dto.tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailModel {

    public boolean saveOrderDetails(String orderId, List<CartTm> cartTmList) throws SQLException {
        for (CartTm tm: cartTmList) {
            if(!saveOrderDetail(orderId,tm)){
                return false;
            }
        }
        return true;
    }

    private boolean saveOrderDetail(String orderId, CartTm tm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO order_details VALUES (?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,orderId);
        pstm.setString(2,tm.getId());
        pstm.setDouble(3,tm.getQty());
        pstm.setDouble(4,tm.getUnit());
        pstm.setDouble(5,tm.getQty()*tm.getUnit());

        return pstm.executeUpdate() >0;

    }
}
