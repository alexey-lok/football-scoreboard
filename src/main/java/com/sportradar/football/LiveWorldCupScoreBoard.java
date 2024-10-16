package com.sportradar.football;

import java.util.HashSet;
import java.util.Set;

public class LiveWorldCupScoreBoard {

    private final Set<Match> matches;

    public LiveWorldCupScoreBoard() {
        this.matches = new HashSet<>();
    }

    public void startMatch(Match match) {
        if (matches.contains(match)) {
            throw new IllegalStateException("Match " + match.homeTeam() + "-" + match.awayTeam() + " already started");
        }
        matches.add(match);
    }

    public MatchScore getMatchScore(String homeTeam, String awayTeam) {
        return new MatchScore(0, 0);
    }

}
