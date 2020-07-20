package com.codecool.masonrySystem.Models;

import java.util.List;

public class Apprentice extends User{
    Integer lodgeId;
    Integer totalPoints;
    Integer balance;
    List<Quest> questList;
    List<Artifact> usedArtifacts;

    public Integer getLodgeId() {
        return lodgeId;
    }

    public void setLodgeId(Integer lodgeId) {
        this.lodgeId = lodgeId;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
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
