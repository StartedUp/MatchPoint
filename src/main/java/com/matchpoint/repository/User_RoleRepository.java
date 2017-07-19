package com.matchpoint.repository;

import com.matchpoint.model.User_Role;
import org.hibernate.*;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.jdbc.Work;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.stat.SessionStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.sql.Connection;

/**
 * Created by gokul on 19/7/17.
 */
public interface User_RoleRepository extends JpaRepository <User_Role, Integer> {
}
