package com.codecool.masonrySystem.Models;

public class Artifact {
    Integer price;
    String description;
    Boolean isCollective;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCollective() {
        return isCollective;
    }

    public void setCollective(Boolean collective) {
        isCollective = collective;
    }
}
