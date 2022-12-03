package fr.delaria.core.cmd;

import fr.delaria.core.Core;
import fr.sunderia.sunderiautils.commands.CommandInfo;
import fr.sunderia.sunderiautils.commands.PluginCommand;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@CommandInfo(name="sethome")
public class SetHomeCommand extends PluginCommand {

    public SetHomeCommand(JavaPlugin plugin) {
        super(Core.get());
    }

    @Override
    public void onCommand(Player player, String[] args){
        if (args.length == 0) {
            player.sendMessage("§c/sethome <name>");
            return;
        }
        String homeName = args[0];
        Location location = HomeCommand.playerHomes.get(player).put(homeName,player.getLocation());
        if (location != null) {
            player.sendMessage("§cYou already have a home with this name.");
            return;
        }
        player.sendMessage("§cYour home has been saved");
    }
}
