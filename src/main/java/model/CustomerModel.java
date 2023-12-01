package model;

import lk.ijse.mrGreen.db.DbConnection;
import lk.ijse.mrGreen.dto.CustomerDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {
    public boolean saveCustomer(CustomerDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO customer VALUES(?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getId());
        pstm.setString(2,dto.getName());
        pstm.setString(3,dto.getAddress());
        pstm.setString(4,dto.getTel());

        try{
            return pstm.executeUpdate() > 0;
        }catch (Exception e){

        }
        return false;
    }

    public boolean deleteCustomer(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM customer WHERE cus_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,id);

        try{
            return pstm.executeUpdate() > 0;
        }catch (Exception e){

        }
        return false;
    }

    public boolean updateCustomer(CustomerDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE customer SET name = ?, address = ?, tel = ? WHERE cus_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getName());
        pstm.setString(2,dto.getAddress());
        pstm.setString(3, dto.getTel());
        pstm.setString(4,dto.getId());

        try{
            return pstm.executeUpdate() > 0;
        }catch (Exception e){

        }
        return false;
    }

    public List<CustomerDto> loadAllCustomer() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM customer";
        PreparedStatement pstm=connection.prepareStatement(sql);

        ArrayList<CustomerDto> dto = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            dto.add(new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return dto;
    }

    public int getCustomerCount() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(*) AS num_Customer FROM customer";
        PreparedStatement pstm =connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        resultSet.next();

        int count = resultSet.getInt("num_Customer");

        return count;
    }

    public String getName(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT name FROM customer WHERE cus_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()){
            return resultSet.getString("name");
        }
        return null;
    }

    public CustomerDto getCustomerDetils(String cusId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql ="SELECT * FROM customer WHERE cus_id = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,cusId);

        ResultSet resultSet=pstm.executeQuery();

        CustomerDto dto = null;

        if(resultSet.next()){
           dto = new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
        }
        return dto;
    }
}
