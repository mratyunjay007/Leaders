package com.spirit.tech.leaders;

public class LeaderVision {
    private String vision;
    private String title;
    private String life;
    private String Story;

    public LeaderVision(String vision, String title,String life, String story) {
        this.vision = vision;
        this.title=title;
        this.life = life;
        Story = story;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLife() {
        return life;
    }

    public void setLife(String life) {
        this.life = life;
    }

    public String getStory() {
        return Story;
    }

    public void setStory(String story) {
        Story = story;
    }
}
