package com.codecool.masonrysystem.dao;


import com.codecool.masonrysystem.model.Artifact;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

class ArtifactDaoTest {
    private Date sqlDate = mock(java.sql.Date.class);
    private ArtifactDao artifactDao = new ArtifactDao();


    @Test
    public void testIsArtifactPresentFromResultSet() throws SQLException {
        ResultSet resultSetMock = mock(ResultSet.class);
        stub(resultSetMock.getLong("id")).toReturn(1L);
        stub(resultSetMock.getString("name")).toReturn("Artifact");
        stub(resultSetMock.getInt("price")).toReturn(100);
        stub(resultSetMock.getString("description")).toReturn("Description");
        stub(resultSetMock.getBoolean("is_collective")).toReturn(false);
        stub(resultSetMock.getBoolean("is_active")).toReturn(true);
        stub(resultSetMock.getDate("expiration_date")).toReturn(sqlDate);


        Artifact artifactExpected = new Artifact();
        artifactExpected.setId(1L);
        artifactExpected.setName("Artifact");
        artifactExpected.setPrice(100);
        artifactExpected.setDescription("Description");
        artifactExpected.setIsCollective(false);
        artifactExpected.setIsActive(true);
        artifactExpected.setExpirationDate(sqlDate);

        Artifact artifact = artifactDao.create(resultSetMock);

        assertEquals(artifactExpected.getId(), artifact.getId());
        assertEquals(artifactExpected.getName(), artifact.getName());
        assertEquals(artifactExpected.getPrice(), artifact.getPrice());
        assertEquals(artifactExpected.getDescription(), artifact.getDescription());
        assertEquals(artifactExpected.getIsCollective(), artifact.getIsCollective());
        assertEquals(artifactExpected.getIsActive(), artifact.getIsActive());
        assertEquals(artifactExpected.getExpirationDate(), artifact.getExpirationDate());

    }
}