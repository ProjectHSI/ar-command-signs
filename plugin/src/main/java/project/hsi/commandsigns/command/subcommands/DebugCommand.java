package project.hsi.commandsigns.command.subcommands;

import org.bukkit.command.CommandSender;
import project.hsi.commandsigns.command.CommandRequiringManager;
import project.hsi.commandsigns.controller.NCommandSignsManager;
import project.hsi.commandsigns.model.CommandSignsCommandException;

import java.util.List;


public class DebugCommand extends CommandRequiringManager {

    public DebugCommand(NCommandSignsManager manager) {
        super(manager, "debug", new String[]{"dbg"});
        this.basePermission = "commandsign.admin.debug";
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {
        manager.debug(sender);
        return true;
    }

    @Override
    public void printUsage(CommandSender sender) {
        sender.sendMessage("/commandsign debug");
    }
}
