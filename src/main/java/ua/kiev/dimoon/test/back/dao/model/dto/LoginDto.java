package ua.kiev.dimoon.test.back.dao.model.dto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by admin on 23.03.2017.
 */
public class LoginDto {
    @NotEmpty
    private String userName;
    @NotEmpty
    private String passWord;

    public String getUserName() {
        return userName;
    }

    public LoginDto setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassWord() {
        return passWord;
    }

    public LoginDto setPassWord(String passWord) {
        this.passWord = passWord;
        return this;
    }
}
