package fr.delaria.core.listeners;

import fr.delaria.core.Core;
import fr.delaria.core.cmd.HomeCommand;
import fr.delaria.core.utils.EssentialsHomeConvertor;
import net.luckperms.api.model.user.User;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        User user = Core.get().getLuckPerms().getUserManager().getUser(player.getName());

        player.setResourcePack("https://cdn.discordapp.com/attachments/1003357762991960223/1046533161485615277/k_cDelariaflPack_k.zip");
        String prefix = user.getCachedData().getMetaData().getPrefix().replace("&", "§");
        player.setPlayerListName(prefix + " " + player.getName());
        player.setPlayerListHeaderFooter("\n\n\n\nΩ","\n     §fdiscord.gg/delaria    ");
        event.setJoinMessage("§a[+] " + prefix + " " + player.getName());
        loadHomes(player);
    }

    private void loadHomes(Player player) {
        if (!HomeCommand.playerHomes.containsKey(player)) {
            Map<String, Location> homes = new HashMap<>();
            homes.put("bed", Objects.requireNonNullElseGet(player.getBedSpawnLocation(), () -> player.getWorld().getSpawnLocation()));
            try {
                Path resolve = Core.get().getDataFolder().toPath().resolve(player.getUniqueId() + "-home.json");
                if(resolve.toFile().exists()) homes.putAll(Core.getGson().fromJson(Files.readString(resolve, StandardCharsets.UTF_8), homes.getClass()));
                else homes.putAll(EssentialsHomeConvertor.convertHomes(player));
            } catch (IOException e) {
                Core.get().getLogger().warning("Failed to convert homes for player " + player.getName() + "\n" + ExceptionUtils.getFullStackTrace(e));
            }
            HomeCommand.playerHomes.put(player, homes);

        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        User user = Core.get().getLuckPerms().getUserManager().getUser(event.getPlayer().getName());

        event.setQuitMessage("§c[-] " + user.getCachedData().getMetaData().getPrefix().replace("&", "§")+ " " + event.getPlayer().getName());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        User user = Core.get().getLuckPerms().getUserManager().getUser(event.getPlayer().getName());

        event.setFormat(playerOP(event.getPlayer()) + user.getCachedData().getMetaData().getPrefix().replace("&","§") + " " +event.getPlayer().getName() +" §7↦ §f" + event.getMessage());
    }

    public String playerOP(Player player){
        if (!player.isOp()) return "";
        return "§7[§4OP§7]";
    }
}