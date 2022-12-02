package fr.delaria.core.cmd;

import fr.delaria.core.Core;
import fr.sunderia.sunderiautils.commands.CommandInfo;
import fr.sunderia.sunderiautils.commands.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@CommandInfo(name = "spawn")
public class SpawnCommand extends PluginCommand {

    public SpawnCommand(JavaPlugin plugin) {
        super(Core.get());
    }

    @Override
    public void onCommand(Player player, String[] args) {
        player.teleport(player.getWorld().getSpawnLocation());
    }
}
