package fr.delaria.core.cmd;

import fr.delaria.core.utils.TextComponentBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PackCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player player)) return true;

        player.setTexturePack("https://cdn.discordapp.com/attachments/1003357762991960223/1046533161485615277/k_cDelariaflPack_k.zip");
        TextComponent textComponent = new TextComponent("§7[§cDelaria§fPack§7] §8»§f Si vous n'avez reçu aucune demande de Resource Pack, cliquez");
        textComponent.addExtra(new TextComponentBuilder(" §cici§f.").setClickAction(ClickEvent.Action.OPEN_URL,"https://cdn.discordapp.com/attachments/1003357762991960223/1046533161485615277/k_cDelariaflPack_k.zip").build());
        player.spigot().sendMessage(textComponent);
        return false;
    }
}
