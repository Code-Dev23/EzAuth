package it.codedevv.ezauth.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import it.codedevv.ezauth.EzAuth;
import it.codedevv.ezauth.auth.model.AuthPlayer;
import it.codedevv.ezauth.utilities.Messages;
import it.codedevv.ezauth.utilities.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@CommandAlias("ezauth|eza")
@RequiredArgsConstructor
public class EzAuthCommand extends BaseCommand {
    private final EzAuth main;

    @Default
    public void onCommand(Player player) {
        Messages.send(player, "&ePlugin created by CodeDevv_");
    }

    @Subcommand("help")
    public void onSubHelp(Player player) {
        Messages.send(player, "&8&m                          &f");
        Messages.send(player, "&a&lEz&2&lAuth &7| &fHelp Page");
        Messages.send(player, "&7- &f/ezauth setspawn");
        Messages.send(player, "&7- &f/ezauth tpspawn");
        Messages.send(player, "&7- &f/ezauth infoplayer <player>");
        Messages.send(player, "&7- &f/ezauth help");
        Messages.send(player, "&7- &f/login <password>");
        Messages.send(player, "&7- &f/register <password> <confirm password>");
        Messages.send(player, "&7- &f/logout");
        Messages.send(player, "&7- &f/changepassword <player> <new password>");
        Messages.send(player, "&8&m                          &f");
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
    }

    @Subcommand("setspawn")
    public void onSubSetSpawn(Player player) {
        main.getConfig().set("AUTH_SPAWN", player.getLocation());
        Messages.send(player, "&aSpawn set with successfully!");
    }

    @Subcommand("tpspawn")
    public void onSubTPSpawn(Player player) {
        Location loc = (Location) main.getConfig().get("AUTH_SPAWN");
        if(loc == null) {
            EzAuth.LOGGER.warning("Spawn is not set. Use: /ezauth setspawn");
            return;
        }
        player.teleport(loc);
    }

    @Subcommand("infoplayer")
    public void onSubInfoPlayer(Player player, String[] args) {
        if(args.length == 0) {
            Messages.send(player, "&cEnter the player name.");
            return;
        }
        Player target = Bukkit.getPlayerExact(args[0]);
        if(target == null) {
            Messages.send(player, "&cPlayer not found!");
            return;
        }
        if(!main.getAuthPlayerManager().existAuthPlayer(player)) {
            Messages.send(player, "&cThe player is not registered!");
            return;
        }
        AuthPlayer authPlayer = main.getAuthPlayerManager().findAuthPlayer(player);
        Messages.send(player, "&8&m                          &f");
        Messages.send(player, "&7%player%&2' Information".replace("%player%", target.getName()));
        Messages.send(player, "&fRegistered: &a" + (authPlayer.isRegistered() ? "Yes" : "No"));
        Messages.send(player, "&fLogged: &a" + (authPlayer.isLogged() ? "Yes" : "No"));
        Messages.send(player, "&fPremium: &a" + (authPlayer.isPremium() ? "Yes" : "No"));
        Messages.send(player, "&8&m                          &f");
    }
}