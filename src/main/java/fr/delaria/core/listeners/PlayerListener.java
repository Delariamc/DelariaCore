package fr.delaria.core.listeners;

import fr.delaria.core.Core;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        User user = Core.get().getLuckPerms().getUserManager().getUser(event.getPlayer().getName());

        event.getPlayer().setResourcePack("https://cdn.discordapp.com/attachments/1003357762991960223/1046533161485615277/k_cDelariaflPack_k.zip");
        //event.getPlayer().setPlayerListName(user.getCachedData().getMetaData().getPrefix()+" "+ event.getPlayer());
        String prefix = user.getCachedData().getMetaData().getPrefix().replace("&", "§");
        event.getPlayer().setPlayerListName(prefix + " " + event.getPlayer().getName());
        event.getPlayer().setPlayerListHeaderFooter("\n\n\n\nΩ","\n     §fdiscord.gg/delaria    ");
        event.setJoinMessage("§a[+] " + prefix + " " + event.getPlayer().getName());
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