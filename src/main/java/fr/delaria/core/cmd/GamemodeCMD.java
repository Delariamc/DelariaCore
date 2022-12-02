package fr.delaria.core.cmd;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.RecordBuilder;
import fr.delaria.core.Core;
import fr.sunderia.sunderiautils.commands.CommandInfo;
import fr.sunderia.sunderiautils.commands.PluginCommand;
import net.luckperms.api.cacheddata.Result;
import net.luckperms.api.node.types.PrefixNode;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@CommandInfo(name = "gamemode", aliases = {"gm"}, permission = "minecraft.command.gamemode")
public class GamemodeCMD extends PluginCommand {

    private final Core core;
    private static final Map<String, GameMode> gamemodes = ImmutableMap.<String, GameMode>builder()
            .put("0", GameMode.SURVIVAL).put("1", GameMode.CREATIVE).put("2", GameMode.ADVENTURE).put("3", GameMode.SPECTATOR)
            .put("survival", GameMode.SURVIVAL).put("creative", GameMode.CREATIVE).put("adventure", GameMode.ADVENTURE).put("spectator", GameMode.SPECTATOR)
            .put("s", GameMode.SURVIVAL).put("c", GameMode.CREATIVE).put("a", GameMode.ADVENTURE).put("sp", GameMode.SPECTATOR)
            .build();

    public GamemodeCMD(JavaPlugin core) {
        super(core);
        if(!(core instanceof Core)) throw new UnsupportedOperationException("The plugin is not an instance of Core?");
        this.core = (Core) core;
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length > 2) return;

        AtomicReference<Player> target = new AtomicReference<>(getArg(args, 1).map(Bukkit::getPlayer).orElse(player));

        GameMode gameMode = gamemodes.get(args[0]);
        if (gameMode == null) return;
        target.get().setGameMode(gameMode);
        String formatted = "%s§fMode de jeu §c%s §fpour %s&r %s".formatted(core.getPrefix(), gameMode, ChatColor.translateAlternateColorCodes('&', core.getLuckPerms().getGroupManager().getGroup(core.getLuckPerms().getUserManager().getUser(target.get().getName()).getPrimaryGroup()).getCachedData().getMetaData().queryPrefix().result()), target.get().getName());
        player.sendMessage(formatted);
        if (target.get() != player) target.get().sendMessage(formatted);
    }
}
