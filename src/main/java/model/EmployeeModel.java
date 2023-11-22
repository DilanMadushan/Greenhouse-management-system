package model;

import com.sun.source.tree.WhileLoopTree;
import lk.ijse.mrGreen.db.DbConnection;
import lk.ijse.mrGreen.dto.EmployeeDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EmployeeModel {

    public boolean saveEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql ="INSERT INTO employee VALUES(?,?,?,?,?,?)";
        PreparedStatement pstm =connection.prepareStatement(sql);

        pstm.setString(1,dto.getId());
        pstm.setString(2,dto.getName());
        pstm.setInt(3,dto.getAge());
        pstm.setString(4,dto.getAddress());
        pstm.setString(5, dto.getJob());
        pstm.setDouble(6,dto.getSalary());

        try {
            return pstm.executeUpdate() > 0;
        }catch (Exception e){

        }
        return false;

    }

    public boolean deleteEmployee(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM employee WHERE emp_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,id);

        try {
            return pstm.executeUpdate() > 0;
        }catch (Exception e){

        }
        return false;
    }

    public boolean updateEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE employee SET name = ?, age = ?, address = ?," +
                " job_role = ?, salary = ? WHERE emp_id = ?;";

        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setString(1,dto.getName());
        pstm.setInt(2,dto.getAge());
        pstm.setString(3, dto.getAddress());
        pstm.setString(4,dto.getJob());
        pstm.setDouble(5,dto.getSalary());
        pstm.setString(6,dto.getId());

        try{
            return pstm.executeUpdate() > 0;
        }catch (Exception e){

        }
        return false;
    }

    public List<EmployeeDto> getAllEmployee() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql ="SELECT * FROM employee";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ArrayList<EmployeeDto> dto= new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            dto.add(new EmployeeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getDouble(6)
            ));
        }
        return dto;
    }

    public int getEmployeCount() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(*) AS num_Employee FROM employee";

        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        resultSet.next();

        int count = resultSet.getInt("num_Employee");

        return count;
    }

    public String getEmployeeName(String id) throws SQLException {

            Connection connection = DbConnection.getInstance().getConnection();
            String sql ="SELECT name FROM employee WHERE emp_id = ?";
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,id);

            ResultSet resultSet = pstm.executeQuery();

            resultSet.next();

            String name = resultSet.getString("name");

            return name;
    }

    public EmployeeDto getEmployeeDetails(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql ="SELECT * FROM employee WHERE emp_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        EmployeeDto dto = null;

        if(resultSet.next()){
            dto = new EmployeeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getDouble(6)
            );
        }
        return dto;
    }
}
