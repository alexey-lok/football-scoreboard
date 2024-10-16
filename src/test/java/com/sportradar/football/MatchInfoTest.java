package com.sportradar.football;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
public class MatchInfoTest {

    @Test
    void creatingMatchInfoWithNullMatchOrScoreThrowsIllegalArgumentException() {
        String expectedMessage = "Match and score cannot be null";
        assertThatThrownBy(() -> new MatchInfo(new Match("Mexico", "Canada"), null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(expectedMessage);

        assertThatThrownBy(() -> new MatchInfo(null, new MatchScore(0, 0)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(expectedMessage);
    }

    @Test
    void totalScoreIsSumOfHomeTeamScoreAndAwayTeamScore() {
        MatchInfo matchInfo = new MatchInfo(new Match("Mexico", "Canada"), new MatchScore(1, 2));
        assertThat(matchInfo.totalScore()).isEqualTo(3);
    }
}
