package lk.ijse.mrGreen.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LettuceDto {

    private String id;
    private String name;
    private int temp;
    private int humid;
    private double unit;
    private double qty;
    private String suppId;


}
