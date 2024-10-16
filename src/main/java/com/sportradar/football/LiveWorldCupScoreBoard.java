package com.sportradar.football;

import java.util.HashSet;
import java.util.Set;

public class LiveWorldCupScoreBoard {

    private final Set<Match> matches;

    public LiveWorldCupScoreBoard() {
        this.matches = new HashSet<>();
    }

    public void startMatch(String homeTeam, String awayTeam) {
        if (homeTeam == null || awayTeam == null || homeTeam.isEmpty() || awayTeam.isEmpty()) {
            throw new IllegalArgumentException("Team names cannot be null or empty");
        }

        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("Home and away teams cannot be the same");
        }

        Match match = new Match(homeTeam, awayTeam);
        if (matches.contains(match)) {
            throw new IllegalStateException("Match " + homeTeam + "-" + awayTeam + " already started");
        }
        matches.add(match);
    }

    private record Match(String homeTeam, String awayTeam) {        
    }

}
