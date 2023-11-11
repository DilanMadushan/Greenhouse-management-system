package lk.ijse.mrGreen.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeTm {
    private String id;
    private String name;
    private int age;
    private String address;
    private String job;
    private double salary;

}
