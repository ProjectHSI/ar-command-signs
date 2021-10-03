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
public class DeleteCommand extends CommandRequiringManager {

    public DeleteCommand(NCommandSignsManager manager) {
        super(manager, "delete", new String[]{"del", "remove", "rm"});
        this.basePermission = "commandsign.admin.delete";
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) throws CommandSignsCommandException {
        if (!(sender instanceof Player)) {
            throw new CommandSignsCommandException(commandMessages.get("error.command.player_requirement"));
        }
        Player player = (Player) sender;

        if (args.isEmpty()) {
            if (isPlayerAvailable(player)) {
                addPendingDelete(player, null);
                player.sendMessage(commandMessages.get("howto.click_to_delete"));
                return true;
            }
        } else {
            try {
                long id = Long.parseLong(args.get(0));
                CommandBlockPendingInteraction interaction = manager.getPendingInteraction(player);
                if (interaction != null && CommandBlockPendingInteraction.Type.DELETE == interaction.type) {
                    CommandBlock cmd = interaction.commandBlock;
                    if (cmd != null && cmd.getId() == id) {
                        manager.deleteCommandBlock(cmd);
                        manager.removePendingInteraction(player);
                        player.sendMessage(commandMessages.get("success.command_deleted"));
                        return true;
                    }
                } else if (isPlayerAvailable(player)) {
                    CommandBlock commandBlock = manager.getCommandBlock(id);
                    addPendingDelete(player, commandBlock);
                    player.sendMessage(commandMessages.get("howto.confirm_deletion"));
                    return true;
                }
            } catch (NumberFormatException ex) {
                throw new CommandSignsCommandException(commandMessages.get("error.command.number_requirement"));
            }
        }
        return false;
    }

    private void addPendingDelete(Player player, CommandBlock commandBlock) {
        CommandBlockPendingInteraction interaction = new CommandBlockPendingInteraction();
        interaction.type = CommandBlockPendingInteraction.Type.DELETE;
        interaction.player = player;
        if (commandBlock != null) {
            interaction.commandBlock = commandBlock;
        }
        manager.addPendingInteraction(interaction);
    }

    @Override
    public void printUsage(CommandSender sender) {
        sender.sendMessage("/commandsign delete [ID]");
    }
}
