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
    private double qty;
    private double seed;
    private double unit;
    private String suppId;


}
