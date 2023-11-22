package model;

import lk.ijse.mrGreen.db.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.Collection;

public class OrderModel {
    public String genarateOrderId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()){
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    private String splitOrderId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("O0");
            int id = Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if (length < 2){
                return "O00"+id;
            }else {
                if (length < 3){
                    return "O0"+id;
                }else {
                    return "O"+id;
                }
            }
        }
        return "O001";
    }

    public boolean saveOrder(String oId, String cusId, LocalDate date) throws SQLException {
        System.out.println(oId+""+cusId+""+date);
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO orders VALUES(?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,oId);
        pstm.setString(2,cusId);
        pstm.setDate(3, Date.valueOf(date));

        try {
            return pstm.executeUpdate() > 0;
        }catch (Exception e){

        }
        return false;
    }

    public int getOrderCount() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql ="SELECT COUNT(*) AS num_of_orders FROM orders";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet=pstm.executeQuery();

        resultSet.next();

        int count = resultSet.getInt("num_of_orders");
        return count;
    }
}
