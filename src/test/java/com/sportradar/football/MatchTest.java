package com.sportradar.football;

import org.junit.jupiter.api.Test;  
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MatchTest {

    @Test
    void creatingMatchWithEmptyTeamNamesThrowsIllegalArgumentException() {
        String expectedMessage = "Team names cannot be null or empty";
        
        assertThatThrownBy(() -> new Match(null, null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(expectedMessage);

        assertThatThrownBy(() -> new Match("", ""))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(expectedMessage);
    }

    @Test
    void startMatchWithSameHomeAndAwayTeamThrowsIllegalArgumentException() {
        String expectedMessage = "Home and away teams cannot be the same";
        assertThatThrownBy(() -> new Match("team", "team"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(expectedMessage);
    }
}
