package it.codedevv.ezauth.auth.struct;

import it.codedevv.ezauth.EzAuth;
import it.codedevv.ezauth.auth.model.AuthPlayer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class AuthPlayerManager {
    private final EzAuth main;

    @Getter
    public static HashMap<UUID, AuthPlayer> playerCaches = new HashMap<>();

    public AuthPlayer findAuthPlayer(Player player) {
        return playerCaches.entrySet().stream()
                .filter(entry -> entry.getKey().equals(player.getUniqueId()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }

    public boolean existAuthPlayer(Player player) {
        return findAuthPlayer(player) != null || playerCaches.containsKey(player.getUniqueId());
    }

    public boolean isValidPassword(String password) {
        return password.length() > main.getConfig().getInt("PASSWORD_SETTINGS.MIN_LENGHT");
    }

    public void logoutPlayer(Player player) {
        main.getSqLite().deletePlayer(player.getUniqueId());
        playerCaches.remove(player.getUniqueId());
    }
    public void registerPlayer(Player player, String password) {
        AuthPlayer authPlayer = new AuthPlayer(player, password);
        authPlayer.setRegistered(true);
        authPlayer.setLogged(true);
        main.getSqLite().createPlayer(authPlayer);
        playerCaches.putIfAbsent(player.getUniqueId(), authPlayer);
    }

    public void loginPlayer(Player player, String password) {
        AuthPlayer authPlayer = findAuthPlayer(player);
        authPlayer.setLogged(true);
    }

    public void loadAuthPlayers() {

    }
    public void saveAuthPlayers() {

    }
}