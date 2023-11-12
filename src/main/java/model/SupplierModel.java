package model;

import lk.ijse.mrGreen.db.DbConnection;
import lk.ijse.mrGreen.dto.SupplierDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {
    public List<SupplierDto> loadAllSupplier() throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM supplier";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<SupplierDto> supList = new ArrayList<>();


        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()){
            supList.add(new SupplierDto(
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getInt(4),
                resultSet.getString(5)
            ));

        }

        return supList;
    }

    public boolean saveSupplier(SupplierDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO supplier VALUES(?,?,?,?,?)";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getSup_id());
        pstm.setString(2,dto.getName());
        pstm.setString(3, dto.getCompany());
        pstm.setInt(4,dto.getTel());
        pstm.setString(5,dto.getUser_id());

        try {
            return pstm.executeUpdate() > 0;
        }catch (Exception e){

        }
        return false;
    }

    public boolean deleteSupplier(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM supplier WHERE sup_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,id);

        return pstm.executeUpdate() > 0;
    }

    public boolean updateSupplier(SupplierDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE supplier SET name = ?, company = ?, tel = ?, user_id = ? WHERE sup_id =?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getName());
        pstm.setString(2, dto.getCompany());
        pstm.setInt(3,dto.getTel());
        pstm.setString(4,dto.getUser_id());
        pstm.setString(5,dto.getSup_id());

        return pstm.executeUpdate() > 0;
    }
}
