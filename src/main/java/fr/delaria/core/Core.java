package fr.delaria.core;

import fr.delaria.core.cmd.GamemodeCMD;
import fr.delaria.core.listeners.PlayerListener;
import fr.delaria.core.utils.CommandUtils;
import fr.sunderia.sunderiautils.SunderiaUtils;
import fr.sunderia.sunderiautils.commands.CommandBuilder;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

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
        SunderiaUtils.of(this);
        new CommandBuilder("heal").setPermission("delaria.command.heal").setFunction((player, args) -> {
            Player target = CommandUtils.getTarget(player, args, 0);
            target.setHealth(20);
        }).build();
        new CommandBuilder("feed").setAliases(new String[] {"food"}).setPermission("delaria.command.feed").setFunction((player, args) -> {
            Player target = CommandUtils.getTarget(player, args, 0);
            target.setSaturation(20);
            target.setFoodLevel(20);
        }).build();
        try {
            SunderiaUtils.registerCommands(GamemodeCMD.class.getPackageName());
            SunderiaUtils.registerListeners(PlayerListener.class.getPackageName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
