package it.codedevv.ezauth.utilities;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Messages {
    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
    public static void send(CommandSender sender, String message) {
        sender.sendMessage(color(message));
    }
    public static void send(CommandSender sender, String... messages) {
        for(String message : messages)
            send(sender, message);
    }
    public static void send(Player player, String title, String subTitle, int seconds) {
        player.sendTitle(color(title), color(subTitle), 10, seconds * 20, 10);
    }
}
