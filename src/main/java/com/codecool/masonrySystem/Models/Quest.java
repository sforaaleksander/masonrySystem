package com.codecool.masonrySystem.Models;

import java.util.Date;

public class Quest {
    private Long id;
    private String name;
    private Integer reward;
    private Rank requiredRank;
    private String description;
    private Boolean isActive;
    private Date expirationDate;
    private Boolean isCollective;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getReward() {
        return reward;
    }

    public void setReward(Integer reward) {
        this.reward = reward;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Rank getRequiredRank() {
        return requiredRank;
    }

    public void setRequiredRank(Rank requiredRank) {
        this.requiredRank = requiredRank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Boolean getIsCollective() {
        return isCollective;
    }

    public void setIsCollective(Boolean collective) {
        isCollective = collective;
    }
}
