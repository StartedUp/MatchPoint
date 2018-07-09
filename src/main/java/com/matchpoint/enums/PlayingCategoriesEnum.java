package com.matchpoint.enums;

/**
 * Created by root on 6/6/18.
 */
public enum PlayingCategoriesEnum {
    MEN_SINGLES(1,"Men singles", true),
    WOMEN_SINGLES(2,"Women singles", true);

    private int playingCategory;
    private String playingCategoryName;
    private boolean isActiveCategory;

    PlayingCategoriesEnum(int playingCategory, String playingCategoryName, boolean isActiveCategory) {
        this.playingCategory = playingCategory;
        this.playingCategoryName = playingCategoryName;
        this.isActiveCategory = isActiveCategory;
    }

    public int getPlayingCategory() {
        return playingCategory;
    }

    public void setPlayingCategory(int playingCategory) {
        this.playingCategory = playingCategory;
    }

    public String getPlayingCategoryName() {
        return playingCategoryName;
    }

    public void setPlayingCategoryName(String playingCategoryName) {
        this.playingCategoryName = playingCategoryName;
    }

    public boolean isActiveCategory() {
        return isActiveCategory;
    }

    public void setActiveCategory(boolean activeCategory) {
        isActiveCategory = activeCategory;
    }
}
