package com.codecool.masonrySystem.Models;

import java.util.Date;

public class Transaction {
    Integer id;
    Long userId;
    Integer artifactId;
    Date openTransaction;
    Date closeTransaction;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(Integer artifactId) {
        this.artifactId = artifactId;
    }

    public Date getOpenTransaction() {
        return openTransaction;
    }

    public void setOpenTransaction(Date openTransaction) {
        this.openTransaction = openTransaction;
    }

    public Date getCloseTransaction() {
        return closeTransaction;
    }

    public void setCloseTransaction(Date closeTransaction) {
        this.closeTransaction = closeTransaction;
    }
}
