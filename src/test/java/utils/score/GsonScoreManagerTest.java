package utils.score;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GsonScoreManagerTest {

    GsonScoreManager gsonScoreManager = new GsonScoreManager();
    ScoreRow scoreRow1 = new ScoreRow("Player",true,18);
    ScoreRow scoreRow2 = new ScoreRow("Player2",true,20);
    ScoreRow scoreRow3 = new ScoreRow("Player3",false,20);

    @BeforeEach
    void init(){
        gsonScoreManager.addScore(scoreRow3);
        gsonScoreManager.addScore(scoreRow1);
        gsonScoreManager.addScore(scoreRow2);
    }
    @Test
    void addScore() {
        GsonScoreManager gsonScoreManagerClone = new GsonScoreManager();
        gsonScoreManagerClone.addScore(scoreRow3);

        assertEquals(List.of(scoreRow3),gsonScoreManagerClone.getPlayersScores());
        assertNotEquals(List.of(scoreRow1,scoreRow2),gsonScoreManagerClone.getPlayersScores());

        gsonScoreManagerClone.addScore(scoreRow1);

        assertNotEquals(gsonScoreManager.getPlayersScores(),gsonScoreManagerClone.getPlayersScores());

        gsonScoreManagerClone.addScore(scoreRow2);

        assertEquals(gsonScoreManager.getPlayersScores(),gsonScoreManagerClone.getPlayersScores());
    }

    @Test
    void getPlayersScores() {
        GsonScoreManager gsonScoreManagerClone = new GsonScoreManager();
        gsonScoreManagerClone.addScore(scoreRow1);
        gsonScoreManagerClone.addScore(scoreRow3);
        assertNotEquals(gsonScoreManagerClone.getPlayersScores(),gsonScoreManager.getPlayersScores());
        gsonScoreManagerClone.addScore(scoreRow2);
        assertEquals(gsonScoreManagerClone.getPlayersScores(),gsonScoreManager.getPlayersScores());
    }
}