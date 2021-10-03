package project.hsi.commandsigns.command.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import project.hsi.commandsigns.CommandSignsPlugin;
import project.hsi.commandsigns.command.Command;

import java.util.List;


/**
 * Created by nokorbis on 1/20/16.
 */
public class VersionCommand extends Command {
    public VersionCommand() {
        super("version", new String[]{"v"});
        this.basePermission = "commandsign.admin.version";
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {
        sender.sendMessage(ChatColor.AQUA + "CommandSign version : " + CommandSignsPlugin.getPlugin().getDescription().getVersion() + " developed by Nokorbis.");
        sender.sendMessage(ChatColor.GOLD + (ChatColor.MAGIC + "HSI1337" + ChatColor.RESET) + ChatColor.GOLD + " Modded by " + (ChatColor.MAGIC + "HSI" + ChatColor.RESET) + ChatColor.GOLD + " [Project HSI] " + ChatColor.MAGIC + "HSI HSI1337");
        return true;
    }

    @Override
    public void printUsage(CommandSender sender) {
        sender.sendMessage("/commandsign version");
    }
}
