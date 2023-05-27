package me.psikuvit.betterscoreboards;

import org.bukkit.ChatColor;

public class MessagesUtils {

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
