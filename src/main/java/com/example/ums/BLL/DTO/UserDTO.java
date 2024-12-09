package com.example.ums.BLL.DTO;

public class UserDTO {
    public String Id;
    public String login;
    public String password;

    public UserDTO(String id, String login, String password) {
        Id = id;
        this.login = login;
        this.password = password;
    }

    public String getId() {
        return Id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
