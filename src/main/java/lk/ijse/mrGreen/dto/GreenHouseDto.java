package lk.ijse.mrGreen.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GreenHouseDto {
    private String id ;
    private String name;
    private String l_id;
    private int seed;
    private int temp ;
    private double ph;
}
