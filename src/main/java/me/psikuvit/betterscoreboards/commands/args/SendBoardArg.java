package me.psikuvit.betterscoreboards.commands.args;

import me.psikuvit.betterscoreboards.BetterScoreboards;
import me.psikuvit.betterscoreboards.BoardData;
import me.psikuvit.betterscoreboards.BoardsUtils;
import me.psikuvit.betterscoreboards.MessagesUtils;
import me.psikuvit.betterscoreboards.commands.CommandAbstract;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

public class SendBoardArg extends CommandAbstract {

    public SendBoardArg(BetterScoreboards plugin) {
        super(plugin);
    }

    @Override
    public void executeCommand(String[] args, CommandSender sender) {
        BoardData boardData = BoardsUtils.getBoardByID(args[1]);
        Player player = Bukkit.getPlayer(args[2]);
        if (player == null) {
            sender.sendMessage(MessagesUtils.color("&cCouldn't find this player"));
            return;
        }
        if (boardData == null) {
            sender.sendMessage(MessagesUtils.color("&cCouldn't find this Scoreboard id"));
            return;
        }
        BoardsUtils.sendBoard(player, boardData);

    }

    @Override
    public String correctArg() {
        return "/bs send <board> <player>";
    }

    @Override
    public boolean onlyPlayer() {
        return true;
    }

    @Override
    public int requiredArg() {
        return 2;
    }

    @Override
    public int bypassArgLimit() {
        return 0;
    }

    @Override
    public List<String> tabComplete(String[] args) {
        List<String> tab = new ArrayList<>();
        if (args.length == 1) {
            tab.add("send");
        } else if (args.length == 2) {
            BoardsUtils.getBoardList().forEach(boardData -> tab.add(boardData.getId()));

        }
        return tab;
    }
}
