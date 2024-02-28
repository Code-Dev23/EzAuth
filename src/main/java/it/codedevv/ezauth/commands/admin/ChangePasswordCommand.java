package it.codedevv.ezauth.commands.admin;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import it.codedevv.ezauth.EzAuth;
import it.codedevv.ezauth.auth.model.AuthPlayer;
import it.codedevv.ezauth.utilities.Messages;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("changepassword|changepassw|ezchangepassword|ezchangepassw")
@RequiredArgsConstructor
public class ChangePasswordCommand extends BaseCommand {
    private final EzAuth main;

    @Default
    public void onCommand(Player player, String[] args) {
        if(args.length <= 1) {
            Messages.send(player, "&cUse: /changepassword <player> <newpassword>");
            return;
        }
        String newPassword = args[1];
        Player target = Bukkit.getPlayerExact(args[0]);
        if(target == null) {
            Messages.send(player, "&cPlayer not found!");
            return;
        }
        if(!main.getAuthPlayerManager().existAuthPlayer(player)) {
            Messages.send(player, "&cThe player is not registered.");
            return;
        }
        if(!main.getAuthPlayerManager().isValidPassword(newPassword)) {
            Messages.send(player, "&cThe password must contain at least %min_chars% characters.".replace("%min_chars%", String.valueOf(main.getConfig().getInt("PASSWORD_SETTINGS.MIN_LENGHT"))));
            return;
        }
        AuthPlayer authPlayer = main.getAuthPlayerManager().findAuthPlayer(player);
        authPlayer.setPassword(newPassword);
        authPlayer.setRegistered(true);
        authPlayer.setLogged(false);
        Messages.send(player, "&aThe %player% password has been successfully reset.".replace("%player%", target.getName()));
        target.kickPlayer(Messages.color("&cYour password has been reset. New Password: %password%".replace("%password%", newPassword)));
    }
}