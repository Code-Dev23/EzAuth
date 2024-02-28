package it.codedevv.ezauth.auth.model;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.UUID;

@Getter @Setter
public class AuthPlayer {
    private UUID uuid;
    private String name;
    private String password;
    private boolean logged;
    private boolean registered;
    private boolean premium;

    public AuthPlayer(Player player, String password) {
        this.uuid = player.getUniqueId();
        this.name = player.getName();
        this.password = password;
        this.logged = false;
        this.registered = false;
        this.premium = false;
    }

    public boolean checkPassword(String passw) {
        return password.equals(passw);
    }
}