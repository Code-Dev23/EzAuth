package it.codedevv.ezauth;

import co.aikar.commands.PaperCommandManager;
import it.codedevv.ezauth.auth.struct.AuthPlayerManager;
import it.codedevv.ezauth.commands.EzAuthCommand;
import it.codedevv.ezauth.commands.LoginCommand;
import it.codedevv.ezauth.commands.LogoutCommand;
import it.codedevv.ezauth.commands.RegisterCommand;
import it.codedevv.ezauth.commands.admin.ChangePasswordCommand;
import it.codedevv.ezauth.listeners.PlayerListeners;
import it.codedevv.ezauth.storage.SQLite;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

@Getter
public final class EzAuth extends JavaPlugin {

    public static final Logger LOGGER = Bukkit.getLogger();

    @Getter
    private static EzAuth instance;
    private SQLite sqLite;
    private AuthPlayerManager authPlayerManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        sqLite = new SQLite();
        authPlayerManager = new AuthPlayerManager(instance);

        loadListeners();
        loadCommands();
    }

    @Override
    public void onDisable() {
    }

    private void loadListeners() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerListeners(instance), this);
    }

    private void loadCommands() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(instance);

        paperCommandManager.registerCommand(new LoginCommand(instance));
        paperCommandManager.registerCommand(new RegisterCommand(instance));
        paperCommandManager.registerCommand(new ChangePasswordCommand(instance));
        paperCommandManager.registerCommand(new LogoutCommand(instance));
        paperCommandManager.registerCommand(new EzAuthCommand(instance));
    }
}