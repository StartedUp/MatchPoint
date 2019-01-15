package com.matchpoint.service;

import com.matchpoint.model.PlayingCategory;
import com.matchpoint.model.TrainingProgram;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Prithviprakash on 12/11/18.
 */
@Service
public class TrainingProgramService {
    public List<TrainingProgram> listPlayingCategory() {
        return null;
    }

    public void addPlayingCategory(TrainingProgram trainingProgram) {
    }

    public void updatePlayingCategory(TrainingProgram trainingProgram) {
    }

    public void removePlayingCategory(int id) {
    }

    public TrainingProgram getPlayingCategoryById(int id) {
        return null;
    }

    public List<TrainingProgram> findByEndDateAfter(Date time) {
        return new ArrayList<>();
    }
}
