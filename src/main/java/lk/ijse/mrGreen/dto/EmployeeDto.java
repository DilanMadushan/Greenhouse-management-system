package lk.ijse.mrGreen.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto{
    private String id;
    private String name;
    private int age;
    private String address;
    private String job;
    private double salary;

}
