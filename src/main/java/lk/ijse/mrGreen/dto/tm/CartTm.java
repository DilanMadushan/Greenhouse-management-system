package lk.ijse.mrGreen.dto.tm;

import lombok.*;
import javafx.scene.control.Button;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartTm {
    private String id;
    private String name;
    private Double qty;
    private Double unit;
    private Double total;
}
