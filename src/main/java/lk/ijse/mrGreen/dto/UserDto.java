package lk.ijse.mrGreen.dto;

public class UserDto {
    private String id;
    private String name;
    private String password;
    private String job_role;

    public UserDto(String id, String name, String password, String job_role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.job_role = job_role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJob_role() {
        return job_role;
    }

    public void setJob_role(String job_role) {
        this.job_role = job_role;
    }
}
