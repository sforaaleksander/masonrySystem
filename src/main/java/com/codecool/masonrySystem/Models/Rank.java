package com.codecool.masonrySystem.Models;

public enum Rank {

    NONE("None"),
    THEROYALARCH("The Royal Arch"),
    GRANDSUVERENINSPECTORS33RDDEGREE("Grand Suveren Inspectors 33rd Degree"),
    SUPREMECOUNCILOFGRANDINSPECTORS("Supreme Council Of Grand Inspectors"),
    THEORDEROFTHETRAPEZOID("The Order Of The Trapezoid"),
    ANCIENTRITES("Ancient Rites"),
    ORDOTEMPLIORIENTIS("Ordo Templis Orientis"),
    THEPALLADIUM("The Palladium"),
    THEILLUMINATI("The Illuminati"),
    AINSOPHAUR("Ain Soph Aur");

    String rankString;


    Rank(String rankString){
        this.rankString = rankString;
    }
}
