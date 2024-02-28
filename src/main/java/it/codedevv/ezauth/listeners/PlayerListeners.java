package it.codedevv.ezauth.listeners;

import it.codedevv.ezauth.EzAuth;
import it.codedevv.ezauth.auth.model.AuthPlayer;
import it.codedevv.ezauth.utilities.Messages;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@RequiredArgsConstructor
public class PlayerListeners implements Listener {
    private final EzAuth main;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(!main.getAuthPlayerManager().existAuthPlayer(player)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 255, false, true, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 255, false, true, false));

            Messages.send(player, "&eUse: /register <password> <confirm password>");
            Messages.send(player, "&c&lRegister", "&7Use: /register <password> <confirm password>", 100000);
            return;
        }
        AuthPlayer authPlayer = main.getAuthPlayerManager().findAuthPlayer(player);
        if(!authPlayer.isRegistered()) {
            Messages.send(player, "&eUse: /register <password> <confirm password>");
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 255, false, true, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 255, false, true, false));

            Messages.send(player, "&c&lRegister", "&7Use: /register <password> <confirm password>", 100000);
            return;
        }
        if(!authPlayer.isLogged()) {
            Messages.send(player, "&eUse: /login <password>");
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 255, false, true, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 255, false, true, false));
            Messages.send(player, "&c&lLogin", "&7Use: /login <password>", 100000);
            return;
        }
        Messages.send(player, "", "", 1);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Messages.send(player, "", "", 1);
        if(!main.getAuthPlayerManager().existAuthPlayer(player))
            return;
        AuthPlayer authPlayer = main.getAuthPlayerManager().findAuthPlayer(player);
        if(authPlayer.isLogged())
            authPlayer.setLogged(false);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if(!main.getAuthPlayerManager().existAuthPlayer(player))
            return;
        AuthPlayer authPlayer = main.getAuthPlayerManager().findAuthPlayer(player);
        if(authPlayer == null)
            return;
        if(!authPlayer.isLogged() || !authPlayer.isRegistered())
            event.setCancelled(true);
    }

    @EventHandler
    public void onMove(EntityDamageByEntityEvent event) {
        if((event.getDamager() instanceof Player player)) {
            if (!main.getAuthPlayerManager().existAuthPlayer(player)) {
                return;
            }
            AuthPlayer authPlayer = main.getAuthPlayerManager().findAuthPlayer(player);
            if (authPlayer == null)
                return;
            if (!authPlayer.isLogged() || !authPlayer.isRegistered())
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove(EntityDamageEvent event) {
        if((event.getEntity() instanceof Player player)) {
            if (!main.getAuthPlayerManager().existAuthPlayer(player)) {
                return;
            }
            AuthPlayer authPlayer = main.getAuthPlayerManager().findAuthPlayer(player);
            if (authPlayer == null)
                return;
            if (!authPlayer.isLogged() || !authPlayer.isRegistered())
                event.setCancelled(true);
        }
    }
}