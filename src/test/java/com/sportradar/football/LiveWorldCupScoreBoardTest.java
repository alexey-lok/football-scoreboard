package com.sportradar.football;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

public class LiveWorldCupScoreBoardTest {
    
    @Test
    void canStartMatch() {
        LiveWorldCupScoreBoard scoreboard = new LiveWorldCupScoreBoard();
        scoreboard.startMatch(new Match("Mexico", "Canada"));
    }

    @Test
    void startMatchWithEmptyTeamNamesThrowsIllegalArgumentException() {
        LiveWorldCupScoreBoard scoreboard = new LiveWorldCupScoreBoard();
        String expectedMessage = "Team names cannot be null or empty";
        assertThatThrownBy(() -> {
            scoreboard.startMatch(new Match(null, null));
        })
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(expectedMessage);

        assertThatThrownBy(() -> {
            scoreboard.startMatch(new Match("", ""));
        })
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(expectedMessage);
    }

    @Test
    void startMatchWithSameHomeAndAwayTeamThrowsIllegalArgumentException() {
        LiveWorldCupScoreBoard scoreboard = new LiveWorldCupScoreBoard();
        String expectedMessage = "Home and away teams cannot be the same";
        assertThatThrownBy(() -> {
            scoreboard.startMatch(new Match("team", "team"));
        })
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(expectedMessage);
    }

    @Test
    void canNotStartTheSameMatchTwice() {
        LiveWorldCupScoreBoard scoreboard = new LiveWorldCupScoreBoard();
        
        scoreboard.startMatch(new Match("Mexico", "Canada"));
        
        String expectedMessage = "Match Mexico-Canada already started";
        
        assertThatThrownBy(() -> {
            scoreboard.startMatch(new Match("Mexico", "Canada"));
        })
        .isInstanceOf(IllegalStateException.class)
        .hasMessage(expectedMessage);
    }

    @Test
    void matchStartsWithZeroScore() {
        LiveWorldCupScoreBoard scoreboard = new LiveWorldCupScoreBoard();
        Match match = new Match("Mexico", "Canada");
        scoreboard.startMatch(match);

        assertThat(scoreboard.getMatchScore(match))
            .isEqualTo(new MatchScore(0, 0));
    }

    @Test
    void canUpdateScoreOfOngoingMatch() {
        LiveWorldCupScoreBoard scoreboard = new LiveWorldCupScoreBoard();
        Match match = new Match("Mexico", "Canada");
        scoreboard.startMatch(match);
        scoreboard.updateScore(match, new MatchScore(1, 0));

        assertThat(scoreboard.getMatchScore(match))
            .isEqualTo(new MatchScore(1, 0));
    }

    @Test
    void canNotUpdateScoreOfMatchThatHasNotStarted() {
        LiveWorldCupScoreBoard scoreboard = new LiveWorldCupScoreBoard();
        Match match = new Match("Mexico", "Canada");
        String expectedMessage = "Match Mexico-Canada has not started";
        assertThatThrownBy(() -> {
            scoreboard.updateScore(match, new MatchScore(1, 0));
        })
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(expectedMessage);
    }

    @Test
    void canGetSummaryOfMatches() {
        LiveWorldCupScoreBoard scoreboard = new LiveWorldCupScoreBoard();
        Match match1 = new Match("Mexico", "Canada");
        Match match2 = new Match("Spain", "Brazil");

        scoreboard.startMatch(match1);
        scoreboard.startMatch(match2);

        scoreboard.updateScore(match1, new MatchScore(0, 1));
        scoreboard.updateScore(match2, new MatchScore(1, 0));
        scoreboard.updateScore(match2, new MatchScore(1, 1));

        List<MatchInfo> summary = scoreboard.getSummary();

        assertThat(summary)         
            .hasSize(2)
            .contains(
                new MatchInfo(match1, new MatchScore(0, 1)),
                new MatchInfo(match2, new MatchScore(1, 1))
            );  
    }
}
