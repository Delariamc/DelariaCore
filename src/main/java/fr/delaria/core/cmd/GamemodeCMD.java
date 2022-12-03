package fr.delaria.core.cmd;

import com.google.common.collect.ImmutableMap;
import fr.delaria.core.Core;
import fr.sunderia.sunderiautils.commands.CommandInfo;
import fr.sunderia.sunderiautils.commands.PluginCommand;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

@CommandInfo(name = "gamemode", aliases = {"gm"}, permission = "minecraft.command.gamemode")
public class GamemodeCMD extends PluginCommand {

    private final Core core;
    private static final Map<String, GameMode> gamemodes = ImmutableMap.<String, GameMode>builder()
            .put("0", GameMode.SURVIVAL).put("1", GameMode.CREATIVE).put("2", GameMode.ADVENTURE).put("3", GameMode.SPECTATOR)
            .put("survival", GameMode.SURVIVAL).put("creative", GameMode.CREATIVE).put("adventure", GameMode.ADVENTURE).put("spectator", GameMode.SPECTATOR)
            .put("s", GameMode.SURVIVAL).put("c", GameMode.CREATIVE).put("a", GameMode.ADVENTURE).put("sp", GameMode.SPECTATOR)
            .build();

    public GamemodeCMD(JavaPlugin core) {
        super(Core.get());
        this.core = Core.get();
    }

    @Override
    public void onCommand(Player player, String[] args) {
        Player target = getArg(args, 1).map(Bukkit::getPlayer).orElse(player);

        GameMode gameMode = gamemodes.get(getArg(args, 0).orElse("4"));
        if (gameMode == null) return;
        target.setGameMode(gameMode);
        String formatted = "%s§fMode de jeu §c%s §fpour %s&r %s".formatted(core.getPrefix(), gameMode, ChatColor.translateAlternateColorCodes('&', core.getLuckPerms().getGroupManager().getGroup(core.getLuckPerms().getUserManager().getUser(target.getName()).getPrimaryGroup()).getCachedData().getMetaData().queryPrefix().result()), target.getName());
        player.sendMessage(formatted);
        if (target != player) target.sendMessage(formatted);
    }
}
