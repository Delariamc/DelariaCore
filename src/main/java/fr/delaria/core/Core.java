package fr.delaria.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.delaria.core.cmd.GamemodeCMD;
import fr.delaria.core.cmd.HomeCommand;
import fr.delaria.core.listeners.PlayerListener;
import fr.delaria.core.utils.CommandUtils;
import fr.delaria.core.utils.SerializerUtils;
import fr.sunderia.sunderiautils.SunderiaUtils;
import fr.sunderia.sunderiautils.commands.CommandBuilder;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Map;

public final class Core extends JavaPlugin {

    private static Core core;
    private LuckPerms api;
    private static final Gson gson = new GsonBuilder().serializeNulls().registerTypeAdapter(Location.class, new SerializerUtils.LocationSerializer()).setPrettyPrinting().create();

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
        Map<Player, Map<String, Location>> playerHomes = HomeCommand.playerHomes;
        if(!getDataFolder().exists()) getDataFolder().mkdirs();
        playerHomes.forEach((player, map) -> {
            try {
                Files.writeString(getDataFolder().toPath().resolve(player.getUniqueId() + "-home.json"),
                        gson.toJson(map), StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public String getPrefix(){
        return "";
    }

    public LuckPerms getLuckPerms() {
        return api;
    }
    public static Gson getGson() {
        return gson;
    }

    public static Core get() {
        return core;
    }
}
