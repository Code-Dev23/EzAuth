package it.codedevv.ezauth.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import it.codedevv.ezauth.EzAuth;
import it.codedevv.ezauth.auth.model.AuthPlayer;
import it.codedevv.ezauth.utilities.Messages;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@CommandAlias("register|reg|ezreg|ezregister")
@RequiredArgsConstructor
public class RegisterCommand extends BaseCommand {
    private final EzAuth main;

    @Default
    public void onCommand(Player player, String[] args) {
        if(args.length <= 1) {
            Messages.send(player, "&cYou didn't enter your password! Use: /register <password> <confirm password>");
            return;
        }
        String password = args[0];
        if(!password.equals(args[1])) {
            Messages.send(player, "&cPasswords do not match.");
            return;
        }
        if(!main.getAuthPlayerManager().isValidPassword(password)) {
            Messages.send(player, "&cThe password must contain at least %min_chars% characters.".replace("%min_chars%", String.valueOf(main.getConfig().getInt("PASSWORD_SETTINGS.MIN_LENGHT"))));
            return;
        }
        if(main.getAuthPlayerManager().existAuthPlayer(player)) {
            AuthPlayer authPlayer = main.getAuthPlayerManager().findAuthPlayer(player);
            if (authPlayer.isRegistered()) {
                Messages.send(player, "&cYou are already registered!");
            }
            if(!authPlayer.isLogged()) {
                Messages.send(player, "&cYou are already registered! Use: /login <password>");
                return;
            }
            return;
        }
        main.getAuthPlayerManager().registerPlayer(player, password);
        Messages.send(player, "", "", 1);
        Messages.send(player, "&aRegistered with successfully!");
        player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
    }
}