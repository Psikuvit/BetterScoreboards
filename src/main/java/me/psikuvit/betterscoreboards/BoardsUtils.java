package me.psikuvit.betterscoreboards;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

public class BoardsUtils {

    private static final BetterScoreboards plugin = BetterScoreboards.getPlugin();

    private static final List<BoardData> boardList = new ArrayList<>();

    public static void loadBoards() {
        ScoreboardsFiles scoreboardsFiles = new ScoreboardsFiles(plugin);
        FileConfiguration file = scoreboardsFiles.getConfig();
        for (String boards : file.getKeys(false)) {
            String title = file.getString(boards + ".title");
            List<String> lines = file.getStringList(boards + ".lines");
            BoardData boardData = new BoardData(title, lines, boards);
            boardList.add(boardData);
        }
    }

    public static void sendBoard(Player player, BoardData boardData) {
        player.setScoreboard(boardData.getScoreboard());
    }

    public static BoardData getBoardByID(String id) {
        for (BoardData boardData : boardList) {
            if (boardData.getId().equalsIgnoreCase(id))
                return boardData;
        }
        return null;
    }

    public static List<BoardData> getBoardList() {
        return boardList;
    }
}
