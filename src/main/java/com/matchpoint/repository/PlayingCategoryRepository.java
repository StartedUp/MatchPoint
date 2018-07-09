package com.matchpoint.repository;

import com.matchpoint.model.PlayingCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by root on 1/7/18.
 */
public interface PlayingCategoryRepository extends JpaRepository<PlayingCategory,Integer> {
}
