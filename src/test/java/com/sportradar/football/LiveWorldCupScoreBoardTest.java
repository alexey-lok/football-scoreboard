package com.sportradar.football;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LiveWorldCupScoreBoardTest {

    private LiveWorldCupScoreBoard scoreboard;
    private Match matchMexicoCanada;
    private Match matchSpainBrazil;

    @BeforeEach
    void setUp() {
        scoreboard = new LiveWorldCupScoreBoard();
        matchMexicoCanada = new Match("Mexico", "Canada");
        matchSpainBrazil = new Match("Spain", "Brazil");
    }

    @Test
    void canNotStartTheSameMatchTwice() {
        scoreboard.startMatch(matchMexicoCanada);
        
        String expectedMessage = "Match Mexico-Canada already started";
        
        assertThatThrownBy(() -> {
            scoreboard.startMatch(matchMexicoCanada);
        })
        .isInstanceOf(IllegalStateException.class)
        .hasMessage(expectedMessage);
    }

    @Test
    void matchStartsWithZeroScore() {
        scoreboard.startMatch(matchMexicoCanada);

        assertThat(scoreboard.getMatchScore(matchMexicoCanada))
            .isEqualTo(new MatchScore(0, 0));
    }

    @Test
    void canUpdateScoreOfOngoingMatch() {
        scoreboard.startMatch(matchMexicoCanada);
        scoreboard.updateScore(matchMexicoCanada, new MatchScore(1, 0));

        assertThat(scoreboard.getMatchScore(matchMexicoCanada))
            .isEqualTo(new MatchScore(1, 0));
    }

    @Test
    void canNotUpdateScoreOfMatchThatHasNotStarted() {
        String expectedMessage = "Match Mexico-Canada has not started";
        assertThatThrownBy(() -> {
            scoreboard.updateScore(matchMexicoCanada, new MatchScore(1, 0));
        })
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(expectedMessage);
    }

    @Test
    void canGetSummaryOfMatches() {
        scoreboard.startMatch(matchMexicoCanada);
        scoreboard.startMatch(matchSpainBrazil);

        scoreboard.updateScore(matchMexicoCanada, new MatchScore(0, 1));
        scoreboard.updateScore(matchSpainBrazil, new MatchScore(0, 1));
        scoreboard.updateScore(matchMexicoCanada, new MatchScore(1, 1));

        assertThat(scoreboard.getSummary())         
            .hasSize(2)
            .containsExactly(
                new MatchInfo(matchMexicoCanada, new MatchScore(1, 1)),
                new MatchInfo(matchSpainBrazil, new MatchScore(0, 1))
            );
    }
}