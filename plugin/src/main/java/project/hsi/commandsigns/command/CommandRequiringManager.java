package project.hsi.commandsigns.command;

import org.bukkit.entity.Player;
import project.hsi.commandsigns.controller.NCommandSignsConfigurationManager;
import project.hsi.commandsigns.controller.NCommandSignsManager;
import project.hsi.commandsigns.model.CommandBlockPendingInteraction;
import project.hsi.commandsigns.model.CommandSignsCommandException;


public abstract class CommandRequiringManager extends Command {

    protected final NCommandSignsManager manager;

    protected CommandRequiringManager(NCommandSignsManager manager, String command, String[] aliases) {
        super(command, aliases);
        this.manager = manager;
    }

    public NCommandSignsManager getManager() {
        return manager;
    }

    /**
     * Checks if the player is already doing some creation/edition/deletion about a configuration.
     *
     * @param player
     * @return <code>true</code> if the player isn't doing anything
     * <code>false</code> if the player is already doing something
     * @throws CommandSignsCommandException
     */
    @SuppressWarnings("JavaDoc")
    protected boolean isPlayerAvailable(Player player) throws CommandSignsCommandException {
        NCommandSignsConfigurationManager configurationManager = manager.getPlayerConfigurationManager(player);
        if (configurationManager != null) {
            if (configurationManager.isCreating()) {
                throw new CommandSignsCommandException(commandMessages.get("warning.already_creating_configuration"));
            }
            throw new CommandSignsCommandException(commandMessages.get("warning.already_editing_configuration"));
        }

        CommandBlockPendingInteraction interaction = manager.getPendingInteraction(player);
        if (interaction != null) {
            switch (interaction.type) {
                case COPY:
                    throw new CommandSignsCommandException(commandMessages.get("warning.already_copying_configuration"));
                case DELETE:
                    throw new CommandSignsCommandException(commandMessages.get("warning.already_deleting_configuration"));
                case INFO:
                default:
                    throw new CommandSignsCommandException(commandMessages.get("warning.already_info_configuration"));
            }
        }

        return true;
    }

}
