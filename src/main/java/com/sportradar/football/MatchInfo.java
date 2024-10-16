package com.sportradar.football;

public record MatchInfo(Match match, MatchScore score) {
    
    public int totalScore() {
        return score.homeTeamScore() + score.awayTeamScore();
    }
}
