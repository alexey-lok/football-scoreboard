package com.sportradar.football;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class LiveWorldCupScoreBoard {

    private final Map<Match, MatchScore> matches;

    public LiveWorldCupScoreBoard() {
        this.matches = new HashMap<>();
    }

    public void startMatch(Match match) {
        if (matches.containsKey(match)) {
            throw new IllegalStateException("Match " + match.homeTeam() + "-" + match.awayTeam() + " already started");
        }
        matches.put(match, new MatchScore(0, 0));
    }

    public void updateScore(Match match, MatchScore score) {
        if (!matches.containsKey(match)) {
            throw new IllegalArgumentException("Match " + match.homeTeam() + "-" + match.awayTeam() + " has not started");
        }
        matches.put(match, score);
    }

    public Optional<MatchScore> getMatchScore(Match match) {
        return Optional.ofNullable(matches.get(match));
    }

    public List<MatchInfo> getSummary() {
        return matches.entrySet().stream()
            .map(entry -> new MatchInfo(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
    }
}
