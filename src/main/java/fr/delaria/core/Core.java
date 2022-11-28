package fr.delaria.core;

import fr.delaria.core.cmd.GamemodeCMD;
import fr.delaria.core.cmd.PackCMD;
import fr.delaria.core.listeners.PlayerListener;
import lombok.Getter;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {

    public static Core core;
    public LuckPerms api;

    @Override
    public void onEnable() {
        core = this;
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            this.api = provider.getProvider();
        }
        registerListeners();
        registerCommands();
    }

    private void registerCommands(){
        Bukkit.getServer().getPluginCommand("delariapack").setExecutor(new PackCMD());
        Bukkit.getServer().getPluginCommand("gamemode").setExecutor(new GamemodeCMD(this));
    }

    public void registerListeners(){
        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new PlayerListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public String getPrefix(){
        return "";
    }
    public LuckPerms getLuckPerms() {
        return api;
    }

    public static Core get() {
        return core;
    }
}
