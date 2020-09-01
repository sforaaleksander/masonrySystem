package com.codecool.masonrysystem.dao;


import com.codecool.masonrysystem.exception.ElementNotFoundException;
import com.codecool.masonrysystem.model.Artifact;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @Test
    public void testAreAllElementsPresent() throws SQLException {
        assertNotNull(artifactDao.getAll());
    }

    @Test
    public void testIsElementPresentById() throws SQLException {
        assertNotNull(artifactDao.getById(1L));
    }

    @Test
    public void testIsArtifactInserting() throws SQLException {
        Artifact artifactMock = mock(Artifact.class);
        stub(artifactMock.getId()).toReturn(12L);
        stub(artifactMock.getName()).toReturn("TestArtifact");
        stub(artifactMock.getPrice()).toReturn(999);
        stub(artifactMock.getDescription()).toReturn("TestDescription");
        stub(artifactMock.getIsCollective()).toReturn(false);
        stub(artifactMock.getIsActive()).toReturn(true);
        stub(artifactMock.getExpirationDate()).toReturn(sqlDate);
        assertTrue(artifactDao.insert(artifactMock));
    }

    @Test
    public void testIsArtifactUpdating() throws SQLException {
        Artifact artifactMock = mock(Artifact.class);
        stub(artifactMock.getId()).toReturn(12L);
        stub(artifactMock.getName()).toReturn("TestArtifact");
        stub(artifactMock.getPrice()).toReturn(999);
        stub(artifactMock.getDescription()).toReturn("TestDescriptionUpdated");
        stub(artifactMock.getIsCollective()).toReturn(false);
        stub(artifactMock.getIsActive()).toReturn(true);
        stub(artifactMock.getExpirationDate()).toReturn(sqlDate);
        assertTrue(artifactDao.update(artifactMock));
    }

    @Test
    public void testIsArtifactDeleting(){
        assertTrue(artifactDao.delete(12L));
    }

    @Test
    public void testAreArtifactsUsedByUserPresent() throws SQLException {
        assertNotNull(artifactDao.getAllUsedByUserId(12L));
    }

    @Test
    public void testExceptionWhereNoArtifactsUsedByUserPresent() {
        assertThrows(ElementNotFoundException.class, () -> artifactDao.getAllUsedByUserId(999L));
    }


}