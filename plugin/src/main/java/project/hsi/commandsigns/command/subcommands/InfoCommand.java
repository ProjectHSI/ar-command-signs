package project.hsi.commandsigns.command.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import project.hsi.commandsigns.command.CommandRequiringManager;
import project.hsi.commandsigns.controller.NCommandSignsManager;
import project.hsi.commandsigns.model.CommandBlock;
import project.hsi.commandsigns.model.CommandBlockPendingInteraction;
import project.hsi.commandsigns.model.CommandSignsCommandException;
import project.hsi.commandsigns.utils.CommandSignUtils;

import java.util.List;


/**
 * Created by nokorbis on 1/20/16.
 */
public class InfoCommand extends CommandRequiringManager {

    public InfoCommand(NCommandSignsManager manager) {
        super(manager, "info", new String[]{"i"});
        this.basePermission = "commandsign.admin.info";
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) throws CommandSignsCommandException {
        if (!(sender instanceof Player)) {
            throw new CommandSignsCommandException(commandMessages.get("error.command.player_requirement"));
        }
        Player player = (Player) sender;

        if (args.isEmpty()) {
            if (isPlayerAvailable(player)) {
                CommandBlockPendingInteraction interaction = new CommandBlockPendingInteraction();
                interaction.type = CommandBlockPendingInteraction.Type.INFO;
                interaction.player = player;
                manager.addPendingInteraction(interaction);
                player.sendMessage(commandMessages.get("howto.click_for_info"));
            }
        } else {
            try {
                long id = Long.parseLong(args.get(0));
                CommandBlock cmd = manager.getCommandBlock(id);
                if (cmd == null) {
                    throw new CommandSignsCommandException(commandMessages.get("error.invalid_command_id"));
                }
                CommandSignUtils.info(player, cmd, manager.getAddons());
            } catch (NumberFormatException ex) {
                throw new CommandSignsCommandException(commandMessages.get("error.command.number_requirement"));
            }
        }
        return true;
    }

    @Override
    public void printUsage(CommandSender sender) {
        sender.sendMessage("/commandsign info [ID]");
    }
}
