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
            .hasValue(new MatchScore(0, 0));
    }

    @Test
    void matchScoreForNonExistingMatchIsEmpty() {
        assertThat(scoreboard.getMatchScore(matchMexicoCanada))
            .isNotPresent();
    }

    @Test
    void canUpdateScoreOfOngoingMatch() {
        scoreboard.startMatch(matchMexicoCanada);
        scoreboard.updateScore(matchMexicoCanada, new MatchScore(1, 0));

        assertThat(scoreboard.getMatchScore(matchMexicoCanada))
            .hasValue(new MatchScore(1, 0));
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
    void finishingOngoingMatchRemovesItFromScoreboard() {
        scoreboard.startMatch(matchMexicoCanada);
        scoreboard.finishMatch(matchMexicoCanada);

        assertThat(scoreboard.getMatchScore(matchMexicoCanada))
            .isNotPresent();
    }

    @Test
    void finishingNonExistingMatchThrowsIllegalArgumentException() {
        String expectedMessage = "Match Mexico-Canada is not ongoing so can't be finished";
        assertThatThrownBy(() -> {
            scoreboard.finishMatch(matchMexicoCanada);
        })
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(expectedMessage);
    }

    @Test
    void canGetMatchScoreForTheHomeTeam() {
        scoreboard.startMatch(matchMexicoCanada);
        scoreboard.updateScore(matchMexicoCanada, new MatchScore(5, 2));

        assertThat(scoreboard.getScoreForTeam("Mexico")).hasValue(5);
        assertThat(scoreboard.getScoreForTeam("Canada")).hasValue(2);
    }

    @Test
    void getSummaryReturnsMatchesInOrderOfTheirTotalScoreWithMostRecentlyStartedAtTheTop() {
        Match matchGermanyFrance = new Match("Germany", "France");
        Match matchUruguayItaly = new Match("Uruguay", "Italy");
        Match matchArgentinaAustralia = new Match("Argentina", "Australia");

        scoreboard.startMatch(matchMexicoCanada);
        scoreboard.startMatch(matchSpainBrazil);
        scoreboard.startMatch(matchGermanyFrance);
        scoreboard.startMatch(matchUruguayItaly);
        scoreboard.startMatch(matchArgentinaAustralia);

        scoreboard.updateScore(matchMexicoCanada, new MatchScore(0, 5));
        scoreboard.updateScore(matchSpainBrazil, new MatchScore(10, 2));
        scoreboard.updateScore(matchGermanyFrance, new MatchScore(2, 2));
        scoreboard.updateScore(matchUruguayItaly, new MatchScore(6, 6));
        scoreboard.updateScore(matchArgentinaAustralia, new MatchScore(3, 1));

        assertThat(scoreboard.getSummary())
            .hasSize(5)
            .containsExactly(
                new MatchInfo(matchUruguayItaly, new MatchScore(6, 6)),
                new MatchInfo(matchSpainBrazil, new MatchScore(10, 2)),
                new MatchInfo(matchMexicoCanada, new MatchScore(0, 5)),
                new MatchInfo(matchArgentinaAustralia, new MatchScore(3, 1)),
                new MatchInfo(matchGermanyFrance, new MatchScore(2, 2))
            );
    }
}