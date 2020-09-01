package com.codecool.masonrysystem.dao;

import com.codecool.masonrysystem.model.Quest;
import com.codecool.masonrysystem.model.Rank;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

class QuestDaoTest {
    private Date sqlDate = mock(java.sql.Date.class);
    private QuestDao questDao = new QuestDao();

    @Test
    public void testIsQuestPresentFromResultSet() throws SQLException {
        ResultSet resultSetMock = mock(ResultSet.class);
        stub(resultSetMock.getLong("id")).toReturn(1L);
        stub(resultSetMock.getString("name")).toReturn("Quest");
        stub(resultSetMock.getInt("reward")).toReturn(100);
        stub(resultSetMock.getInt("required_rank")).toReturn(1);
        stub(resultSetMock.getString("description")).toReturn("Description");
        stub(resultSetMock.getBoolean("is_active")).toReturn(true);
        stub(resultSetMock.getDate("expiration_date")).toReturn(sqlDate);

        Quest questExpected = new Quest();
        questExpected.setId(1L);
        questExpected.setName("Quest");
        questExpected.setReward(100);
        questExpected.setRequiredRank(Rank.GRANDSUVERENINSPECTORS33RDDEGREE);
        questExpected.setDescription("Description");
        questExpected.setIsActive(true);
        questExpected.setExpirationDate(sqlDate);

        Quest quest = questDao.create(resultSetMock);

        assertEquals(questExpected.getId(), quest.getId());
        assertEquals(questExpected.getName(), quest.getName());
        assertEquals(questExpected.getReward(), quest.getReward());
        assertEquals(questExpected.getRequiredRank(), quest.getRequiredRank());
        assertEquals(questExpected.getDescription(), quest.getDescription());
        assertEquals(questExpected.getIsActive(), quest.getIsActive());
        assertEquals(questExpected.getExpirationDate(), quest.getExpirationDate());
    }

    @Test
    public void testAreAllElementsPresent() throws SQLException {
        assertNotNull(questDao.getAll());
    }

    @Test
    public void testIsElementPresentById() throws SQLException {
        assertNotNull(questDao.getById(1L));
    }

    @Test
    public void testIsQuestInserting() throws SQLException {
        Quest questMock = mock(Quest.class);
        stub(questMock.getId()).toReturn(15L);
        stub(questMock.getName()).toReturn("TestQuest");
        stub(questMock.getReward()).toReturn(999);
        stub(questMock.getRequiredRank()).toReturn(Rank.THEILLUMINATI);
        stub(questMock.getDescription()).toReturn("TestDescription");
        stub(questMock.getIsActive()).toReturn(true);
        stub(questMock.getExpirationDate()).toReturn(sqlDate);
        assertTrue(questDao.insert(questMock));
    }

    @Test
    public void testIsQuestUpdating() throws SQLException {
        Quest questMock = mock(Quest.class);
        stub(questMock.getId()).toReturn(15L);
        stub(questMock.getName()).toReturn("TestQuest");
        stub(questMock.getReward()).toReturn(999);
        stub(questMock.getRequiredRank()).toReturn(Rank.THEILLUMINATI);
        stub(questMock.getDescription()).toReturn("TestDescriptionUpdated");
        stub(questMock.getIsActive()).toReturn(true);
        stub(questMock.getExpirationDate()).toReturn(sqlDate);
        assertTrue(questDao.update(questMock));
    }

    @Test
    public void testIsQuestDeleting(){
        assertTrue(questDao.delete(15L));
    }
    
}