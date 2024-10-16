package com.sportradar.football;

import org.junit.jupiter.api.Test;

public class LiveWorldCupScoreBoardTest {
    
    @Test
    void testCanStartMatch() {
        LiveWorldCupScoreBoard scoreboard = new LiveWorldCupScoreBoard();
        scoreboard.startMatch("Mexico", "Canada");
    }
}
