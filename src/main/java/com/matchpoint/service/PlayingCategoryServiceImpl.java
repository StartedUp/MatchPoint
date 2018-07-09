package com.matchpoint.service;

import com.matchpoint.model.PlayingCategory;
import com.matchpoint.repository.PlayingCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by root on 1/7/18.
 */
public class PlayingCategoryServiceImpl implements PlayingCategoryService{

    @Autowired
    private PlayingCategoryRepository playingCategoryRepository;

    @Override
    public List<PlayingCategory> listPlayingCategory() {
        return playingCategoryRepository.findAll();
    }

    @Override
    public void addPlayingCategory(PlayingCategory playingCategory) {
        playingCategoryRepository.save(playingCategory);
    }

    @Override
    public void updatePlayingCategory(PlayingCategory playingCategory) {
        playingCategoryRepository.save(playingCategory);
    }

    @Override
    public void removePlayingCategory(int id) {

    }

    @Override
    public PlayingCategory getPlayingCategoryById(int id) {
        return playingCategoryRepository.findOne(id);
    }
}
