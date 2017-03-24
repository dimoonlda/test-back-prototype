package ua.kiev.dimoon.test.back.dao.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDto {
    private Long id;
    @NotEmpty
    private String userName;
    private String passWord;
    private Boolean isActive;
    private Set<RoleDto> roles;

    public Long getId() {
        return id;
    }

    public UserDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserDto setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public Boolean getActive() {
        return isActive;
    }

    public UserDto setActive(Boolean active) {
        isActive = active;
        return this;
    }

    public Set<RoleDto> getRoles() {
        return roles;
    }

    public UserDto setRoles(Set<RoleDto> roles) {
        this.roles = roles;
        return this;
    }

    public String getPassWord() {
        return passWord;
    }

    public UserDto setPassWord(String passWord) {
        this.passWord = passWord;
        return this;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", isActive=" + isActive +
                ", roles=" + roles +
                '}';
    }
}
