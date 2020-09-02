package com.codecool.masonrysystem.dao;

import com.codecool.masonrysystem.exception.ElementNotFoundException;
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
    private Date sqlDate = new Date(2020, 10, 10);
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

        assertAll("Check if db consistent with object",
                () -> assertEquals(questExpected.getId(), quest.getId()),
                () -> assertEquals(questExpected.getName(), quest.getName()),
                () -> assertEquals(questExpected.getReward(), quest.getReward()),
                () -> assertEquals(questExpected.getRequiredRank(), quest.getRequiredRank()),
                () -> assertEquals(questExpected.getDescription(), quest.getDescription()),
                () -> assertEquals(questExpected.getIsActive(), quest.getIsActive()),
                () -> assertEquals(questExpected.getExpirationDate(), quest.getExpirationDate())
        );
    }

    @Test
    public void testAreAllElementsPresent() throws SQLException {
        assertNotEquals(0, questDao.getAll().size());
    }

    @Test
    public void testIsQuestInserting() throws SQLException {
        Quest questMock = mock(Quest.class);
        stub(questMock.getId()).toReturn(999L);
        stub(questMock.getName()).toReturn("TestQuest");
        stub(questMock.getReward()).toReturn(999);
        stub(questMock.getRequiredRank()).toReturn(Rank.THEILLUMINATI);
        stub(questMock.getDescription()).toReturn("TestDescription");
        stub(questMock.getIsActive()).toReturn(true);
        stub(questMock.getExpirationDate()).toReturn(sqlDate);
        questDao.insert(questMock);
        Quest quest = questDao.getById(999L);
        assertAll("Check if db consistent with object",
                () -> assertEquals(questMock.getId(), quest.getId()),
                () -> assertEquals(questMock.getName(), quest.getName()),
                () -> assertEquals(questMock.getReward(), quest.getReward()),
                () -> assertEquals(questMock.getRequiredRank(), quest.getRequiredRank()),
                () -> assertEquals(questMock.getDescription(), quest.getDescription()),
                () -> assertEquals(questMock.getIsActive(), quest.getIsActive()),
                () -> assertEquals(questMock.getExpirationDate(), quest.getExpirationDate())
        );
    }

    @Test
    public void testIsElementPresentById() throws SQLException {
        assertEquals(999L, questDao.getById(999L).getId());
    }

    @Test
    public void testIsQuestUpdating() throws SQLException {
        Quest questMock = mock(Quest.class);
        stub(questMock.getId()).toReturn(999L);
        stub(questMock.getName()).toReturn("TestQuestUpdated");
        stub(questMock.getReward()).toReturn(999);
        stub(questMock.getRequiredRank()).toReturn(Rank.SUPREMECOUNCILOFGRANDINSPECTORS);
        stub(questMock.getDescription()).toReturn("TestDescriptionUpdated");
        stub(questMock.getIsActive()).toReturn(true);
        stub(questMock.getExpirationDate()).toReturn(sqlDate);
        questDao.update(questMock);
        Quest quest = questDao.getById(999L);
        assertAll("Check if db consistent with object",
                () -> assertEquals(questMock.getId(), quest.getId()),
                () -> assertEquals(questMock.getName(), quest.getName()),
                () -> assertEquals(questMock.getReward(), quest.getReward()),
                () -> assertEquals(questMock.getRequiredRank(), quest.getRequiredRank()),
                () -> assertEquals(questMock.getDescription(), quest.getDescription()),
                () -> assertEquals(questMock.getIsActive(), quest.getIsActive()),
                () -> assertEquals(questMock.getExpirationDate(), quest.getExpirationDate())
        );
    }

    @Test
    public void testIsQuestDeleting(){
        questDao.delete(999L);
        assertThrows(ElementNotFoundException.class, () -> questDao.getById(999L));
    }

}