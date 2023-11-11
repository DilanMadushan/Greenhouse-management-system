package model;

import com.sun.source.tree.WhileLoopTree;
import lk.ijse.mrGreen.db.DbConnection;
import lk.ijse.mrGreen.dto.LettuceDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LettuceModel {

    public boolean saveLettuce(LettuceDto dto) throws SQLException {
        Connection connection= DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO lettuce VALUES(?,?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getId());
        pstm.setString(2,dto.getName());
        pstm.setInt(3,dto.getTemp());
        pstm.setInt(4,dto.getHumid());
        pstm.setDouble(5,dto.getQty());
        pstm.setDouble(6,dto.getUnit());
        pstm.setString(7,dto.getSuppId());

        try{
            boolean isSaved = pstm.executeUpdate() > 0;
            return  isSaved;
        }catch (Exception e){

        }
        return false;
    }

    public List<LettuceDto> getAllLettuceDetails() throws SQLException {
        Connection connection =DbConnection.getInstance().getConnection();

        String sql ="SELECT * FROM lettuce";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<LettuceDto> dtoList = new ArrayList<>();

        while(resultSet.next()){
            dtoList.add(new LettuceDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4),
                    resultSet.getDouble(5),
                    resultSet.getDouble(6),
                    resultSet.getString(7)
            ));
        }

        return dtoList;
    }

    public boolean deleteLettuce(String id) throws SQLException {

        Connection connection= DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM lettuce WHERE l_id = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,id);


        return pstm.executeUpdate() > 0;

    }

    public boolean updateLettuce(LettuceDto dto) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE lettuce SET name = ?,Temp = ?,humidity = ?,qty_on_hand = ?, unit_price = ?, sup_id = ? WHERE l_id = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getName());
        pstm.setInt(2,dto.getTemp());
        pstm.setInt(3,dto.getHumid());
        pstm.setDouble(4,dto.getQty());
        pstm.setDouble(5,dto.getUnit());
        pstm.setString(6,dto.getSuppId());
        pstm.setString(7,dto.getId());

        return pstm.executeUpdate() > 0 ;
    }

    public int getCount() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(*) AS num_lettuce From lettuce";

        Statement pstm = connection.createStatement();

        ResultSet resultSet = pstm.executeQuery(sql);

        resultSet.next();
        int lettCount = resultSet.getInt("num_lettuce");

        return lettCount;
    }
}
