package com.matchpoint.model;

import org.springframework.context.annotation.Bean;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by gokul on 19/7/17.
 */
@Entity
@Table(name = "user_role")
public class User_Role {

    @Id
    @NotNull
    private int users_id;
    @NotNull
    private int roles_id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User_Role user_role = (User_Role) o;

        if (users_id != user_role.users_id) return false;
        return roles_id == user_role.roles_id;

    }

    @Override
    public int hashCode() {
        int result = users_id;
        result = 31 * result + roles_id;
        return result;
    }

    public int getUsers_id() {
        return users_id;
    }

    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }

    public int getRoles_id() {
        return roles_id;
    }

    public void setRoles_id(int roles_id) {
        this.roles_id = roles_id;
    }



}
