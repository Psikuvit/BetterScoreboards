package me.psikuvit.betterscoreboards.commands;

import me.psikuvit.betterscoreboards.BetterScoreboards;
import me.psikuvit.betterscoreboards.MessagesUtils;
import me.psikuvit.betterscoreboards.commands.args.ReloadArg;
import me.psikuvit.betterscoreboards.commands.args.SendBoardArg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandRegisterer implements CommandExecutor, TabCompleter {
    private final Map<String, CommandAbstract> commandAbstractMap;

    public CommandRegisterer(final BetterScoreboards plugin) {
        this.commandAbstractMap = new HashMap<>();
        this.commandAbstractMap.put("reload", new ReloadArg(plugin));
        this.commandAbstractMap.put("send", new SendBoardArg(plugin));


    }

    public boolean onCommand(final @NotNull CommandSender commandSender, final @NotNull Command command, final @NotNull String label, String[] args) {
        if (args.length == 0) {
            return true;
        }
        final String subCommand = args[0];
        boolean found = false;
        for (final String cmdAlias : this.commandAbstractMap.keySet()) {
            if (cmdAlias.equalsIgnoreCase(subCommand)) {
                final int argsCount = args.length - 1;
                final boolean isSenderPlayer = commandSender instanceof Player;
                final CommandAbstract cmd = this.commandAbstractMap.get(cmdAlias);
                if (cmd.bypassArgLimit() == 0) {
                    if (argsCount > cmd.requiredArg()) {
                        commandSender.sendMessage(MessagesUtils.color("§cCorrect usage: §e" + cmd.correctArg()));
                        return true;
                    }
                    if (argsCount < cmd.requiredArg()) {
                        commandSender.sendMessage(MessagesUtils.color("§cCorrect usage: §e" + cmd.correctArg()));
                        return true;
                    }
                } else if (cmd.bypassArgLimit() > 0) {
                    if (argsCount < cmd.bypassArgLimit()) {
                        commandSender.sendMessage(MessagesUtils.color("§cCorrect usage: §e" + cmd.correctArg()));
                        return true;
                    }
                }
                if (!isSenderPlayer && cmd.onlyPlayer()) {
                    commandSender.sendMessage("you are not a player");
                    return true;
                }
                args = this.move(args);
                cmd.executeCommand(args, commandSender);
                found = true;
                break;
            }
        }
        if (!found) {
            commandSender.sendMessage(MessagesUtils.color("&cIncorrect"));
        }
        return true;
    }

    private String[] move(final String[] args) {
        final String[] result = new String[args.length - 1];
        System.arraycopy(args, 1, result, 0, args.length - 1);
        return result;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (args.length == 1) {
            // Return available sub-commands as tab completions
            List<String> completions = new ArrayList<>(commandAbstractMap.keySet());
            completions.removeIf(cmdAlias -> !cmdAlias.toLowerCase().startsWith(args[0].toLowerCase()));
            return completions;
        } else if (args.length > 1) {
            // Retrieve the CommandAbstract associated with the given sub-command
            CommandAbstract cmd = commandAbstractMap.get(args[0].toLowerCase());
            if (cmd != null) {
                // Delegate tab completion to the associated CommandAbstract
                return cmd.tabComplete(args);
            }
        }
        return new ArrayList<>();
    }
}
