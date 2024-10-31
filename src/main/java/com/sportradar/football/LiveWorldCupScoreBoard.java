package com.sportradar.football;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class LiveWorldCupScoreBoard {

    private final Map<Match, MatchScore> matches;

    public LiveWorldCupScoreBoard() {
        // LinkedHashMap maintains insertion order
        this.matches = new LinkedHashMap<>();
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

    public void finishMatch(Match match) {
        if (!matches.containsKey(match)) {
            throw new IllegalArgumentException("Match " + match.homeTeam() + "-" + match.awayTeam() + " is not ongoing so can't be finished");
        }
        matches.remove(match);
    }

    public Optional<MatchScore> getMatchScore(Match match) {
        return Optional.ofNullable(matches.get(match));
    }

    public List<MatchInfo> getSummary() {
        return matches.entrySet().stream()
        .map(entry -> new MatchInfo(entry.getKey(), entry.getValue()))
        .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
            // Reverse the list to have the most recently started matches first
            Collections.reverse(list);
            // Sort the list by total score in descending order
            list.sort(Comparator.comparing(MatchInfo::totalScore).reversed());
            return list;
        }));
    }

    public Optional<Integer> getScoreForTeam(String team) {
        return matches.entrySet().stream().filter(entry -> {
            Match match = entry.getKey();
            return match.homeTeam().equals(team) || match.awayTeam().equals(team);
        }).findFirst().map(value -> {
            Match match = value.getKey();
            MatchScore matchScore = value.getValue();

            return match.homeTeam().equals(team) ? matchScore.homeTeamScore() : matchScore.awayTeamScore();
        });
    }
}
