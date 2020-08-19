package com.codecool.masonrysystem.model;

import java.util.List;

public class Apprentice extends User{
    Lodge lodge;
    Integer totalPoints;
    Integer spiritPoints;
    List<Quest> questList;
    List<Artifact> usedArtifacts;

    public Lodge getLodge() {
        return lodge;
    }

    public void setLodge(Lodge lodge) {
        this.lodge = lodge;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Integer getSpiritPoints() {
        return spiritPoints;
    }

    public void setSpiritPoints(Integer spiritPoints) {
        this.spiritPoints = spiritPoints;
    }

    public List<Quest> getQuestList() {
        return questList;
    }

    public void setQuestList(List<Quest> questList) {
        this.questList = questList;
    }

    public List<Artifact> getUsedArtifacts() {
        return usedArtifacts;
    }

    public void setUsedArtifacts(List<Artifact> usedArtifacts) {
        this.usedArtifacts = usedArtifacts;
    }
}
