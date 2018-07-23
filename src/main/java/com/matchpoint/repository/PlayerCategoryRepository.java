package com.matchpoint.repository;

import com.matchpoint.model.PlayerCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Prithu on 24/7/18.
 */
@Repository
public interface PlayerCategoryRepository extends JpaRepository<PlayerCategory, Integer> {

}
