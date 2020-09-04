package com.codecool.masonrysystem.dao;

import com.codecool.masonrysystem.exception.ElementNotFoundException;
import com.codecool.masonrysystem.model.Quest;
import com.codecool.masonrysystem.model.Rank;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QuestDaoTest {
    private Date sqlDate;
    private QuestDao questDao;
    private Quest questMock;

    @BeforeAll
    public void setUp(){
        sqlDate = new Date(2020, 10, 10);
        questDao = new QuestDao();
        questMock = mock(Quest.class);
        stub(questMock.getId()).toReturn(999L);
        stub(questMock.getName()).toReturn("TestQuest");
        stub(questMock.getReward()).toReturn(999);
        stub(questMock.getRequiredRank()).toReturn(Rank.THEILLUMINATI);
        stub(questMock.getDescription()).toReturn("TestDescription");
        stub(questMock.getIsActive()).toReturn(true);
        stub(questMock.getExpirationDate()).toReturn(sqlDate);
    }

    @Test
    public void testIsQuestPresentFromResultSet() throws SQLException {
        ResultSet resultSetMock = mock(ResultSet.class);
        stub(resultSetMock.getLong("id")).toReturn(999L);
        stub(resultSetMock.getString("name")).toReturn("TestQuest");
        stub(resultSetMock.getInt("reward")).toReturn(999);
        stub(resultSetMock.getInt("required_rank")).toReturn(8);
        stub(resultSetMock.getString("description")).toReturn("TestDescription");
        stub(resultSetMock.getBoolean("is_active")).toReturn(true);
        stub(resultSetMock.getDate("expiration_date")).toReturn(sqlDate);

        Quest quest = questDao.create(resultSetMock);

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
    public void testAreAllElementsPresent() throws SQLException {
        assertNotEquals(0, questDao.getAll().size());
    }

    @Test
    public void testIsQuestInserting() throws SQLException {
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
        questDao.delete(999L);
    }

    @Test
    public void testIsElementPresentById() throws SQLException {
        questDao.insert(questMock);
        assertEquals(999L, questDao.getById(999L).getId());
        questDao.delete(999L);
    }

    @Test
    public void testIsQuestUpdating() throws SQLException {
        Quest questMockUpdated = mock(Quest.class);
        stub(questMockUpdated.getId()).toReturn(999L);
        stub(questMockUpdated.getName()).toReturn("TestQuestUpdated");
        stub(questMockUpdated.getReward()).toReturn(999);
        stub(questMockUpdated.getRequiredRank()).toReturn(Rank.SUPREMECOUNCILOFGRANDINSPECTORS);
        stub(questMockUpdated.getDescription()).toReturn("TestDescriptionUpdated");
        stub(questMockUpdated.getIsActive()).toReturn(true);
        stub(questMockUpdated.getExpirationDate()).toReturn(sqlDate);

        questDao.insert(questMock);
        questDao.update(questMockUpdated);
        Quest quest = questDao.getById(999L);
        assertAll("Check if db consistent with object",
                () -> assertEquals(questMockUpdated.getId(), quest.getId()),
                () -> assertEquals(questMockUpdated.getName(), quest.getName()),
                () -> assertEquals(questMockUpdated.getReward(), quest.getReward()),
                () -> assertEquals(questMockUpdated.getRequiredRank(), quest.getRequiredRank()),
                () -> assertEquals(questMockUpdated.getDescription(), quest.getDescription()),
                () -> assertEquals(questMockUpdated.getIsActive(), quest.getIsActive()),
                () -> assertEquals(questMockUpdated.getExpirationDate(), quest.getExpirationDate())
        );
        questDao.delete(999L);
    }

    @Test
    public void testIsQuestDeleting() throws SQLException {
        questDao.insert(questMock);
        questDao.delete(999L);
        assertThrows(ElementNotFoundException.class, () -> questDao.getById(999L));
    }

}