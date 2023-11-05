package model;

import lk.ijse.mrGreen.db.DbConnection;
import lk.ijse.mrGreen.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {

    public UserDto checkUser(String name) throws SQLException {
        Connection connection= DbConnection.getInstance().getConnection();

        String sql ="SELECT * FROM user WHERE name = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,name);

        ResultSet resultSet =pstm.executeQuery();

        UserDto dto =null;

        if(resultSet.next()){
            String user_id = resultSet.getString(1);
            String user_name = resultSet.getString(2);
            String password = resultSet.getString(3);
            String job_role = resultSet.getString(4);

            dto=new UserDto(user_id,user_name,password,job_role);
        }

        return dto;
    }
}
