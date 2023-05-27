package me.psikuvit.betterscoreboards;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;

public class BoardData {

    private final String title;
    private final List<String> lines;
    private final String id;
    private final Scoreboard scoreboard;

    BoardData(String title, List<String> lines, String id) {
        this.title = title;
        this.lines = lines;
        this.id = id;

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective(title, "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(title);

        int i = 0;
        for (String line : lines) {
            objective.getScore(line).setScore(i);
            i++;
        }
    }

    public List<String> getLines() {
        return lines;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}
