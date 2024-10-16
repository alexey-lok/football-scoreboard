# Live Football World Cup Scoreboard library

## Overview
This is a Live Football World Cup Scoreboard library, developed in a TDD fashion. The scoreboard allows clients to start matches, update scores, finish matches, and retrieve a summary of all ongoing matches. The summary lists matches ordered by total score descending, with the most recently started matches appearing first.

## Design Assumptions
The following assumptions were made during the development of this library:

1. **Unique Matches:** Each match is uniquely identified by the combination of home and away teams. No two matches can have exactly the same home and away teams playing simultaneously. I assumed that it's possible to have matches with team1 (home) - team2 (away) and team2 (home) - team1 (away) at the same time (maybe there are different squads of the same national team).
2. **Match Team Names:** Team names must be non-null, non-empty, and distinct.
3. **Score Updates:** Scores cannot be negative. The system only allows non-negative integers for updating the score. Score updates are not dependent on the current score for the match (i.e. they can reduce the current match score or increase it by arbitrary number, assumption being that there can be score corrections during the match).
4. **Match Order in Summary:** The summary of ongoing matches is sorted primarily by the total score in descending order. If two matches have the same total score, the most recently started match appears first.
5. **Error Handling:** Attempts to start an already ongoing match or update a non-existent match will result in an exception.

## Classes and Methods
- **`LiveWorldCupScoreBoard`**: The main class managing the matches and their scores.
  - `startMatch(Match match)`: Starts a new match.
  - `updateScore(Match match, MatchScore score)`: Updates the score of an ongoing match.
  - `finishMatch(Match match)`: Finishes a match and removes it from the scoreboard.
  - `getMatchScore(Match match)`: Retrieves the score of a given match.
  - `getSummary()`: Returns a summary of all ongoing matches.

- **`Match`**: Represents a football match between two teams.
  - Constructor ensures that team names are valid and distinct.

- **`MatchScore`**: Represents the score of a match.
  - Constructor ensures that scores are non-negative.

- **`MatchInfo`**: Combines a `Match` and its `MatchScore` to provide information about the ongoing match.
  - `totalScore()`: Calculates the total score of the match.

## How to Run
1. Clone the repository.
2. Ensure you have Java 17 or higher and Maven.
3. Compile the project, run the tests and built the packaged jar:
   ```sh
   mvn clean package
   ```

## Possible Improvements
- **JavaDoc**: Add JavaDoc to the code. This is a must for a real library.
- **Persistence**: Currently, all matches are stored in memory. An adapter for persistent storage could be implemented to retain match data across restarts.
- **Concurrency**: The current implementation is not thread-safe. Synchronization mechanisms could be added to handle concurrent updates safely.