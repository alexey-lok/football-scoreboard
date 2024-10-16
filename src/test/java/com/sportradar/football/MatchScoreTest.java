package com.sportradar.football;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class MatchScoreTest {

    @Test
    void creatingMatchScoreWithNegativeScoresThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> new MatchScore(-1, 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Score cannot be negative");
    }   
}
