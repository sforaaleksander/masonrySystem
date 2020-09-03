package com.codecool.masonrysystem.dao;

import com.codecool.masonrysystem.exception.ElementNotFoundException;
import com.codecool.masonrysystem.model.Artifact;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ArtifactDaoTest {
    private Date sqlDate;
    private ArtifactDao artifactDao;
    private Artifact artifactMock;

    @BeforeAll
    public void setUp(){
        sqlDate = new Date(2020, 10, 10);
        artifactDao = new ArtifactDao();
        artifactMock = mock(Artifact.class);
        stub(artifactMock.getId()).toReturn(999L);
        stub(artifactMock.getName()).toReturn("TestArtifact");
        stub(artifactMock.getPrice()).toReturn(999);
        stub(artifactMock.getDescription()).toReturn("TestDescription");
        stub(artifactMock.getIsCollective()).toReturn(false);
        stub(artifactMock.getIsActive()).toReturn(true);
        stub(artifactMock.getExpirationDate()).toReturn(sqlDate);
    }


    @Test
    public void testIsArtifactPresentFromResultSet() throws SQLException {
        ResultSet resultSetMock = mock(ResultSet.class);
        stub(resultSetMock.getLong("id")).toReturn(999L);
        stub(resultSetMock.getString("name")).toReturn("TestArtifact");
        stub(resultSetMock.getInt("price")).toReturn(999);
        stub(resultSetMock.getString("description")).toReturn("TestDescription");
        stub(resultSetMock.getBoolean("is_collective")).toReturn(false);
        stub(resultSetMock.getBoolean("is_active")).toReturn(true);
        stub(resultSetMock.getDate("expiration_date")).toReturn(sqlDate);

        Artifact artifact = artifactDao.create(resultSetMock);

        assertAll("Check if db consistent with object",
                () -> assertEquals(artifactMock.getId(), artifact.getId()),
                () -> assertEquals(artifactMock.getName(), artifact.getName()),
                () -> assertEquals(artifactMock.getPrice(), artifact.getPrice()),
                () -> assertEquals(artifactMock.getDescription(), artifact.getDescription()),
                () -> assertEquals(artifactMock.getIsCollective(), artifact.getIsCollective()),
                () -> assertEquals(artifactMock.getIsActive(), artifact.getIsActive()),
                () -> assertEquals(artifactMock.getExpirationDate(), artifact.getExpirationDate())
        );
    }

    @Test
    public void testAreAllElementsPresent() throws SQLException {
        assertNotEquals(0, artifactDao.getAll().size());
    }

    @Test
    public void testIsArtifactInserting() throws SQLException {
        artifactDao.insert(artifactMock);
        Artifact artifact = artifactDao.getById(999L);
        assertAll("Check if db consistent with object",
                () -> assertEquals(artifactMock.getId(), artifact.getId()),
                () -> assertEquals(artifactMock.getName(), artifact.getName()),
                () -> assertEquals(artifactMock.getPrice(), artifact.getPrice()),
                () -> assertEquals(artifactMock.getDescription(), artifact.getDescription()),
                () -> assertEquals(artifactMock.getIsCollective(), artifact.getIsCollective()),
                () -> assertEquals(artifactMock.getIsActive(), artifact.getIsActive()),
                () -> assertEquals(artifactMock.getExpirationDate(), artifact.getExpirationDate())
        );
        artifactDao.delete(999L);
    }

    @Test
    public void testIsElementPresentById() throws SQLException {
        artifactDao.insert(artifactMock);
        assertEquals(999L, artifactDao.getById(999L).getId());
        artifactDao.delete(999L);
    }

    @Test
    public void testIsArtifactUpdating() throws SQLException {
        Artifact artifactMockUpdated = mock(Artifact.class);
        stub(artifactMockUpdated.getId()).toReturn(999L);
        stub(artifactMockUpdated.getName()).toReturn("TestArtifactUpdated");
        stub(artifactMockUpdated.getPrice()).toReturn(999);
        stub(artifactMockUpdated.getDescription()).toReturn("TestDescriptionUpdated");
        stub(artifactMockUpdated.getIsCollective()).toReturn(false);
        stub(artifactMockUpdated.getIsActive()).toReturn(true);
        stub(artifactMockUpdated.getExpirationDate()).toReturn(sqlDate);

        artifactDao.insert(artifactMock);
        artifactDao.update(artifactMockUpdated);
        Artifact artifact = artifactDao.getById(999L);
        assertAll("Check if db consistent with object",
                () -> assertEquals(artifactMockUpdated.getId(), artifact.getId()),
                () -> assertEquals(artifactMockUpdated.getName(), artifact.getName()),
                () -> assertEquals(artifactMockUpdated.getPrice(), artifact.getPrice()),
                () -> assertEquals(artifactMockUpdated.getDescription(), artifact.getDescription()),
                () -> assertEquals(artifactMockUpdated.getIsCollective(), artifact.getIsCollective()),
                () -> assertEquals(artifactMockUpdated.getIsActive(), artifact.getIsActive()),
                () -> assertEquals(artifactMockUpdated.getExpirationDate(), artifact.getExpirationDate())
        );
        artifactDao.delete(999L);
    }

    @Test
    public void testIsArtifactDeleting() throws SQLException {
        artifactDao.insert(artifactMock);
        artifactDao.delete(999L);
        assertThrows(ElementNotFoundException.class, () -> artifactDao.getById(999L));
    }

    @Test
    public void testAreArtifactsUsedByUserPresent() throws SQLException {
        assertNotEquals(0, artifactDao.getAllUsedByUserId(12L).size());
    }

    @Test
    public void testExceptionWhereNoArtifactsUsedByUserPresent() {
        assertThrows(ElementNotFoundException.class, () -> artifactDao.getAllUsedByUserId(999L));
    }
}