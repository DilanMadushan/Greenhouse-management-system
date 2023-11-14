package lk.ijse.mrGreen.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GreenHouseTm {
    private String id ;
    private String name;
    private String l_id;
    private int temp ;
    private double ph;

}
