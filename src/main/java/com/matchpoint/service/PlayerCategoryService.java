package com.matchpoint.service;

import com.matchpoint.model.PlayerCategory;

import java.util.List;

/**
 * Created by Balaji on 24/7/18.
 */
public interface PlayerCategoryService {
    List<PlayerCategory> listplayerCategory();

    PlayerCategory addPlayerCategory(PlayerCategory playerCategory);

    PlayerCategory updatePlayerCategory(PlayerCategory playerCategory);

    void removePlayerCategory(int id);

    PlayerCategory getPlayerCategoryById(int id);
}
