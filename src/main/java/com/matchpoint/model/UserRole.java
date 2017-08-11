package com.matchpoint.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by gokul on 19/7/17.
 */
@Entity
@Table(name = "user_role")
public class UserRole {

    @Id
    @NotNull
    @Column(name = "user_id")
    private int userId;
    @NotNull
    @Column(name = "role_id")
    private int roleId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole user_role = (UserRole) o;

        if (userId != user_role.userId) return false;
        return roleId == user_role.roleId;

    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + roleId;
        return result;
    }

    public int getuserId() {
        return userId;
    }

    public void setuserId(int userId) {
        this.userId = userId;
    }

    public int getRoles_id() {
        return roleId;
    }

    public void setRoles_id(int roleId) {
        this.roleId = roleId;
    }



}
