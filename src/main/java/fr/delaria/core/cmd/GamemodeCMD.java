package fr.delaria.core.cmd;

import fr.delaria.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCMD implements CommandExecutor {

    private Core core;

    public GamemodeCMD(Core core){
        this.core = core;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String gamemode = args[1];
        if (!(sender instanceof Player player) || !player.hasPermission("minecraft.command.gamemode") || args.length < 2) return true;

        Player target = args.length == 2 ? player:Bukkit.getPlayer(args[2]);

        if (gamemode.equalsIgnoreCase("survie") || gamemode.equals("0")) target.setGameMode(GameMode.SURVIVAL);
        if (gamemode.equalsIgnoreCase("creative") || gamemode.equals("1")) target.setGameMode(GameMode.CREATIVE);
        if (gamemode.equalsIgnoreCase("aventure") || gamemode.equals("2")) target.setGameMode(GameMode.ADVENTURE);
        if (gamemode.equalsIgnoreCase("spectator") || gamemode.equals("3")) target.setGameMode(GameMode.SPECTATOR);
        player.sendMessage(core.getPrefix() + "§fMode de jeu §c"+ target.getGameMode() + "§fpour " + core.getLuckPerms().getGroupManager().getGroup(core.getLuckPerms().getUserManager().getUser(target.getName()).getPrimaryGroup()).getCachedData().getMetaData().queryPrefix());
        if (target != player) {
            target.sendMessage(core.getPrefix() + "§fMode de jeu §c"+ target.getGameMode() + "§fpour " + core.getLuckPerms().getGroupManager().getGroup(core.getLuckPerms().getUserManager().getUser(target.getName()).getPrimaryGroup()).getCachedData().getMetaData().queryPrefix());
        }

        return false;
    }
}
