package com.codecool.masonrySystem.Models;

import java.util.Date;

public class Transaction {
    Integer id;
    Integer artifactId;
    Date openTransactionDate;
    Date closeTransactionDate;
    Integer collectedSum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(Integer artifactId) {
        this.artifactId = artifactId;
    }

    public Date getOpenTransactionDate() {
        return openTransactionDate;
    }

    public void setOpenTransactionDate(Date openTransactionDate) {
        this.openTransactionDate = openTransactionDate;
    }

    public Date getCloseTransactionDate() {
        return closeTransactionDate;
    }

    public void setCloseTransactionDate(Date closeTransactionDate) {
        this.closeTransactionDate = closeTransactionDate;
    }

    public Integer getCollectedSum() {
        return collectedSum;
    }

    public void setCollectedSum(Integer collectedSum) {
        this.collectedSum = collectedSum;
    }
}
