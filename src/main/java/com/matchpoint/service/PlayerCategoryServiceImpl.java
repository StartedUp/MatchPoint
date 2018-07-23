package com.matchpoint.service;

import com.matchpoint.model.PlayerCategory;
import com.matchpoint.repository.PlayerCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Balaji on 24/7/18.
 */
@Service
public class PlayerCategoryServiceImpl implements PlayerCategoryService{

    @Autowired
    private PlayerCategoryRepository playerCategoryRepository;


    @Override
    public List<PlayerCategory> listplayerCategory() {
        return playerCategoryRepository.findAll();
    }

    @Override
    public PlayerCategory addPlayerCategory(PlayerCategory playerCategory) {
        return playerCategoryRepository.save(playerCategory);
    }

    @Override
    public PlayerCategory updatePlayerCategory(PlayerCategory playerCategory) {
        return playerCategoryRepository.save(playerCategory);
    }

    @Override
    public void removePlayerCategory(int id) {
        playerCategoryRepository.delete(id);
    }

    @Override
    public PlayerCategory getPlayerCategoryById(int id) {
        return playerCategoryRepository.findOne(id);
    }
}
