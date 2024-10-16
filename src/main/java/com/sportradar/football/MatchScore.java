package com.sportradar.football;

public record MatchScore(int homeTeamScore, int awayTeamScore) {

    public MatchScore {
        if (homeTeamScore < 0 || awayTeamScore < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
        }
    }
}
