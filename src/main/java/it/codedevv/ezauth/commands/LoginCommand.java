package it.codedevv.ezauth.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import it.codedevv.ezauth.EzAuth;
import it.codedevv.ezauth.auth.model.AuthPlayer;
import it.codedevv.ezauth.utilities.Messages;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@CommandAlias("login|l|ezlogin|ezl")
@RequiredArgsConstructor
public class LoginCommand extends BaseCommand {
    private final EzAuth main;

    @Default
    public void onCommand(Player player, String[] args) {
        if(args.length == 0) {
            Messages.send(player, "&cEnter the your password");
            return;
        }
        AuthPlayer authPlayer = main.getAuthPlayerManager().findAuthPlayer(player);
        String password = args[0];
        if(authPlayer.isLogged()) {
            Messages.send(player, "&cAre you already logged in!");
            return;
        }
        if(!authPlayer.isRegistered()) {
            Messages.send(player, "&cDo haven't registered yet..");
            return;
        }
        if(!authPlayer.checkPassword(password)) {
            Messages.send(player, "&cThe password you entered is incorrect.");
            return;
        }
        Messages.send(player, "", "", 1);
        Messages.send(player, "&aLogged with success! Password: " + password);
        authPlayer.setLogged(true);
        player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
    }
}