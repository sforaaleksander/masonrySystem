package com.codecool.masonrySystem.Models;

import java.util.Date;

public class Transaction {
    Long id;
    Long userId;
    Long artifactId;
    Date openTransaction;
    Date closeTransaction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(Long artifactId) {
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
