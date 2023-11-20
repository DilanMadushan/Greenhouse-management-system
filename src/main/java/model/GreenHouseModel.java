package model;

import lk.ijse.mrGreen.db.DbConnection;
import lk.ijse.mrGreen.dto.GreenHouseDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GreenHouseModel {

    public boolean saveGreenhouse(GreenHouseDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO greenhouse(g_id,name,l_id,water_temp,water_ph) VALUES(?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2,dto.getName());
        pstm.setString(3,dto.getL_id());
        pstm.setInt(4,dto.getTemp());
        pstm.setDouble(5,dto.getPh());

        try {
            return pstm.executeUpdate() > 0;
        }catch (Exception e){

        }
        return false;
    }

    public boolean deleteGreenhouse(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql ="DELETE FROM greenhouse WHERE g_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,id);

        try {
            return pstm.executeUpdate() > 0;
        }catch (Exception e){

        }
        return false;
    }

    public boolean updateGreenhouse(GreenHouseDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE greenhouse SET name = ?, l_id = ?,water_temp = ?, water_ph = ? WHERE g_id = ? ";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getName());
        pstm.setString(2,dto.getL_id());
        pstm.setInt(3,dto.getTemp());
        pstm.setDouble(4,dto.getPh());
        pstm.setString(5,dto.getId());

        try {
            return pstm.executeUpdate() > 0;
        }catch (Exception e){

        }
        return false;
    }

    public List<GreenHouseDto> getAllGreenhouse() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM greenhouse";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<GreenHouseDto> dto = new ArrayList<>();

        while (resultSet.next()){
            dto.add(new GreenHouseDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getInt(5),
                    resultSet.getDouble(6)
            ));
        }
        return dto;
    }

    public int getGreenhouseCount() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql= "SELECT COUNT(*) AS num_green FROM greenhouse";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        resultSet.next();
        int count = resultSet.getInt("num_green");

        return count;
    }
}
