package com.sportradar.football;

public record MatchInfo(Match match, MatchScore score) {
    public MatchInfo {
        if (match == null || score == null) {
            throw new IllegalArgumentException("Match and score cannot be null");
        }
    }

    public int totalScore() {
        return score.homeTeamScore() + score.awayTeamScore();
    }
}
