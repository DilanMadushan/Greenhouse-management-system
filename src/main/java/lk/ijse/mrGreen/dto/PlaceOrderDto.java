package lk.ijse.mrGreen.dto;

import lk.ijse.mrGreen.dto.tm.CartTm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrderDto {
    private String order_id;
    private LocalDate date;
    private String cus_id;
    private List<CartTm> cartTmList = new ArrayList<>();


}
