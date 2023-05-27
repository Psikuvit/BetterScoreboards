package me.psikuvit.betterscoreboards.commands.args;

import me.psikuvit.betterscoreboards.BetterScoreboards;
import me.psikuvit.betterscoreboards.ScoreboardsFiles;
import me.psikuvit.betterscoreboards.commands.CommandAbstract;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ReloadArg extends CommandAbstract {

    public ReloadArg(BetterScoreboards plugin) {
        super(plugin);
    }

    @Override
    public void executeCommand(String[] args, CommandSender sender) {
        ScoreboardsFiles files = new ScoreboardsFiles(plugin);
        files.reloadConfig();

    }

    @Override
    public String correctArg() {
        return "/bs reload";
    }

    @Override
    public boolean onlyPlayer() {
        return false;
    }

    @Override
    public int requiredArg() {
        return 0;
    }

    @Override
    public int bypassArgLimit() {
        return 0;
    }

    @Override
    public List<String> tabComplete(String[] args) {
        List<String> tab = new ArrayList<>();
        tab.add("reload");
        return tab;
    }
}
