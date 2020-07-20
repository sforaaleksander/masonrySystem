package com.codecool.masonrySystem.Models;

import java.util.List;

public class Lodge {
    Integer id;
    String name;
    Journeyman owner;
    List<Apprentice> members;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Journeyman getOwner() {
        return owner;
    }

    public void setOwner(Journeyman owner) {
        this.owner = owner;
    }

    public List<Apprentice> getMembers() {
        return members;
    }

    public void setMembers(List<Apprentice> members) {
        this.members = members;
    }
}
