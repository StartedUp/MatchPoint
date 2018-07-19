package com.matchpoint.service;

import com.matchpoint.model.PlayingCategory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by root on 30/6/18.
 */
public interface PlayingCategoryService {
    List<PlayingCategory> listPlayingCategory();

    void addPlayingCategory(PlayingCategory playingCategory);

    void updatePlayingCategory(PlayingCategory playingCategory);

    void removePlayingCategory(int id);

    PlayingCategory getPlayingCategoryById(int id);
}
