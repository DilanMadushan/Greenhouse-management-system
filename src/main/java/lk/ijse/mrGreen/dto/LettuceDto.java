package lk.ijse.mrGreen.dto;

public class LettuceDto {
    private String id;
    private String name;
    private String temp;
    private String humid;
    private String qty;

    public LettuceDto(String id, String name, String temp, String humid, String qty) {
        this.id = id;
        this.name = name;
        this.temp = temp;
        this.humid = humid;
        this.qty = qty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHumid() {
        return humid;
    }

    public void setHumid(String humid) {
        this.humid = humid;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

}