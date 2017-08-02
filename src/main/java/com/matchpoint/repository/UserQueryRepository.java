package com.matchpoint.repository;

import com.matchpoint.model.UserQuery;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by gokul on 25/7/17.
 */
public interface UserQueryRepository extends JpaRepository<UserQuery,Integer> {
}
