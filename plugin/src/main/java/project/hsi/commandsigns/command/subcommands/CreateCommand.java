package project.hsi.commandsigns.command.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import project.hsi.commandsigns.command.CommandRequiringManager;
import project.hsi.commandsigns.controller.NCommandSignsConfigurationManager;
import project.hsi.commandsigns.controller.NCommandSignsManager;
import project.hsi.commandsigns.model.CommandBlock;
import project.hsi.commandsigns.model.CommandSignsCommandException;

import java.util.List;

/**
 * Created by nokorbis on 1/20/16.
 */
public class CreateCommand extends CommandRequiringManager {

    public CreateCommand(NCommandSignsManager manager) {
        super(manager, "create", new String[]{"cr", "mk", "make"});
        this.basePermission = "commandsign.admin.create";
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) throws CommandSignsCommandException {
        if (!(sender instanceof Player)) {
            throw new CommandSignsCommandException(commandMessages.get("error.command.player_requirement"));
        }
        Player player = (Player) sender;

        if (isPlayerAvailable(player)) {
            CommandBlock cmdBlock = new CommandBlock();
            NCommandSignsConfigurationManager configurationManager = new NCommandSignsConfigurationManager(player, manager);
            configurationManager.setCommandBlock(cmdBlock);
            configurationManager.setCreating(true);

            configurationManager.setCurrentMenu(manager.getMainMenu());
            configurationManager.display();

            manager.addConfigurationManager(configurationManager);

            return true;
        }

        return false;
    }

    @Override
    public void printUsage(CommandSender sender) {
        sender.sendMessage("/commandsign create");
    }
}
