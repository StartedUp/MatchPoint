package com.matchpoint.repository;

import com.matchpoint.model.Fee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Balaji on 27/7/18.
 */

public interface FeeRepository extends JpaRepository<Fee, Integer>{

    List<Fee> findByPlayerCategoryId(int playerCategoryId);

}
