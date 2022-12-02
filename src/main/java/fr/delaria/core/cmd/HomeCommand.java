package fr.delaria.core.cmd;

import fr.delaria.core.Core;
import fr.sunderia.sunderiautils.commands.CommandInfo;
import fr.sunderia.sunderiautils.commands.PluginCommand;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CommandInfo(name = "home")
public class HomeCommand extends PluginCommand {

    public static final Map<Player, Map<String, Location>> playerHomes = new HashMap<>();

    public HomeCommand(JavaPlugin plugin) {
        super(Core.get());
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage("§c/home <name>");
            return;
        }
        String homeName = args[0];
        Map<String, Location> homes = playerHomes.get(player);
        if(homes == null) {
            player.sendMessage("§cThat's strange, you don't have any home.");
            return;
        }
        Location location = homes.get(homeName);
        if (location == null) {
            player.sendMessage("§cYou don't have a home named " + homeName);
            return;
        }
        player.teleport(location);
    }
}
