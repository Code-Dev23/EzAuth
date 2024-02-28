package it.codedevv.ezauth.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.Default;
import it.codedevv.ezauth.EzAuth;
import it.codedevv.ezauth.auth.model.AuthPlayer;
import it.codedevv.ezauth.utilities.Messages;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class LogoutCommand extends BaseCommand {
    private final EzAuth main;

    @Default
    public void onCommand(Player player, String[] args) {
        if(!main.getAuthPlayerManager().existAuthPlayer(player)) {
            Messages.send(player, "&cYou are not registered!");
            return;
        }
        AuthPlayer authPlayer = main.getAuthPlayerManager().findAuthPlayer(player);
        if(!authPlayer.isLogged() || !authPlayer.isRegistered()) {
            Messages.send(player, "&cYou are not logged!");
            return;
        }
        main.getAuthPlayerManager().logoutPlayer(player);
        player.kickPlayer(Messages.color("&cYou have been disconnected."));
    }
}