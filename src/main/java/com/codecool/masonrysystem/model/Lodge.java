package com.codecool.masonrysystem.model;

import java.util.List;

public class Lodge {
    Long id;
    String name;
    Journeyman owner;
    List<Apprentice> members;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
