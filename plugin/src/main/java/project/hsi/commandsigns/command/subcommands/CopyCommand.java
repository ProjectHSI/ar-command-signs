package project.hsi.commandsigns.command.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import project.hsi.commandsigns.command.CommandRequiringManager;
import project.hsi.commandsigns.controller.NCommandSignsManager;
import project.hsi.commandsigns.model.CommandBlock;
import project.hsi.commandsigns.model.CommandBlockPendingInteraction;
import project.hsi.commandsigns.model.CommandSignsCommandException;

import java.util.List;


/**
 * Created by nokorbis on 1/20/16.
 */
public class CopyCommand extends CommandRequiringManager {

    public CopyCommand(NCommandSignsManager manager) {
        super(manager, "copy", new String[]{"cp"});
        this.basePermission = "commandsign.admin.copy";
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) throws CommandSignsCommandException {
        if (!(sender instanceof Player)) {
            throw new CommandSignsCommandException(commandMessages.get("error.command.player_requirement"));
        }

        Player player = (Player) sender;

        if (isPlayerAvailable(player)) {
            if (args.isEmpty()) {
                addPendingCopy(player, null);
                player.sendMessage(commandMessages.get("howto.click_to_copy"));
            } else {
                try {
                    long id = Long.parseLong(args.get(0));
                    CommandBlock commandBlock = manager.getCommandBlock(id);
                    if (commandBlock == null) {
                        throw new CommandSignsCommandException(commandMessages.get("error.invalid_command_id"));
                    }
                    addPendingCopy(player, commandBlock);
                    player.sendMessage(commandMessages.get("howto.click_to_paste"));
                } catch (NumberFormatException ex) {
                    throw new CommandSignsCommandException(commandMessages.get("error.command.number_requirement"));
                }
            }

            return true;
        }

        return false;
    }

    private void addPendingCopy(Player player, CommandBlock commandBlock) {
        CommandBlockPendingInteraction interaction = new CommandBlockPendingInteraction();
        interaction.type = CommandBlockPendingInteraction.Type.COPY;
        interaction.player = player;
        if (commandBlock != null) {
            interaction.commandBlock = commandBlock.copy();
        }
        manager.addPendingInteraction(interaction);
    }

    @Override
    public void printUsage(CommandSender sender) {
        sender.sendMessage("/commandsign copy [ID]");
    }
}
