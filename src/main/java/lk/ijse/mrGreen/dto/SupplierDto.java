package lk.ijse.mrGreen.dto;

public class SupplierDto {

   private String sup_id;
   private String name;
   private String company;

   private String tel;
   private String User_id;

    public SupplierDto(String sup_id, String name, String company, String tel, String user_id) {
        this.sup_id = sup_id;
        this.name = name;
        this.company = company;
        this.tel = tel;
        this.User_id = user_id;
    }

    public String getSup_id() {
        return sup_id;
    }

    public void setSup_id(String sup_id) {
        this.sup_id = sup_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }
}
