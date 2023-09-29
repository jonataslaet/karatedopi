package br.com.karatedopi.controllers.dtos;

public class RegisterCreateDTO extends RegisterDTO {

    private String password;

    public RegisterCreateDTO() {
        super();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
