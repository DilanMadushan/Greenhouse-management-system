package model;

import lk.ijse.mrGreen.db.DbConnection;
import lk.ijse.mrGreen.dto.LettuceDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LettuceModel {

    public static boolean saveLettuce(LettuceDto dto) throws SQLException {
        Connection connection= DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO lettuce VALUES(?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getId());
        pstm.setString(2,dto.getName());
        pstm.setString(3,dto.getHumid());
        pstm.setString(4,dto.getHumid());
        pstm.setString(5,dto.getQty());

        Boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }
}
