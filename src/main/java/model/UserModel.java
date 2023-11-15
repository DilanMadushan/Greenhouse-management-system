package model;

import lk.ijse.mrGreen.db.DbConnection;
import lk.ijse.mrGreen.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<UserDto> loadAllUseres() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM user";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ArrayList<UserDto> dtoList= new ArrayList<>();

        ResultSet resultSet=pstm.executeQuery();

        while (resultSet.next()){
            dtoList.add(new UserDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return dtoList;
    }

    public String getPassword(String name) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT password FROM user WHERE name = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,name);

        try{
            ResultSet resultSet = pstm.executeQuery();

            resultSet.next();
            return resultSet.getString("password");

        }catch (Exception e){

        }

       return null;
    }
}
