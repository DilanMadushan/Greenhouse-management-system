package lk.ijse.mrGreen.dto;

import lk.ijse.mrGreen.db.DbConnection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkDto {
    private String emp_id;
    private String g_id;
    private String date;
    private String desc;

    public boolean saveWork(WorkDto workDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO greenhouse_employee_details VALUES(?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,workDto.getEmp_id());
        pstm.setString(2,workDto.getG_id());
        pstm.setString(3,workDto.getDate());
        pstm.setString(4,workDto.getDesc());

        try {
            return pstm.executeUpdate() >0;
        }catch (Exception E){

        }
        return false;
    }
}
