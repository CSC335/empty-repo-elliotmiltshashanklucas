package model;

import java.util.List;
import java.util.stream.Collectors;

import model.account.AccountManager;
import model.game.Game;

public class LeaderboardManager {

    private AccountManager accountManager; 

    public LeaderboardManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public List<Leaderboard> getLeaderboardByDifficulty(Game.Difficulty difficulty) {
        return accountManager.getAccounts().values().stream()
            .map(account -> new Leaderboard(account.getUserName(), account.getStats(difficulty).getBestGuesses()))
            .filter(entry -> entry.getScore() > 0)
            .sorted((a, b) -> Integer.compare(a.getScore(), b.getScore()))
            .collect(Collectors.toList());
    }
}
