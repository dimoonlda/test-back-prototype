package ua.kiev.dimoon.test.back.dao.model.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class RoleDto {
    private Integer id;
    @NotEmpty
    private String name;

    public Integer getId() {
        return id;
    }

    public RoleDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RoleDto setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "RoleDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
