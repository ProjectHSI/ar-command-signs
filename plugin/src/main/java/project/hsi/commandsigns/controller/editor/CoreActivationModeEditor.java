package project.hsi.commandsigns.controller.editor;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import project.hsi.commandsigns.model.BlockActivationMode;
import project.hsi.commandsigns.model.CommandBlock;
import project.hsi.commandsigns.model.CommandSignsCommandException;
import project.hsi.commandsigns.utils.CommandBlockValidator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class CoreActivationModeEditor extends CommandBlockDataEditorBase {

    @Override
    public void editValue(CommandBlock data, List<String> args, CommandSender sender) throws CommandSignsCommandException {
        if (args.isEmpty()) {
            throw new CommandSignsCommandException(messages.get("error.command.more_args"));
        }

        String val = args.get(0).toUpperCase();
        if ("ACTIVATED".equals(val)) {
            data.setActivationMode(BlockActivationMode.ACTIVATED);
        } else if ("DEACTIVATED".equals(val)) {
            data.setActivationMode(BlockActivationMode.DEACTIVATED);
        } else {
            data.setActivationMode(BlockActivationMode.BOTH);
        }
    }

    @Override
    public List<String> onTabComplete(CommandBlock data, List<String> args) {
        if (args.size() <= 1) {
            Location location = data.getLocation();
            if (location != null) {
                if (CommandBlockValidator.isLever(location.getBlock())) {
                    return Arrays.asList("ACTIVATED", "DEACTIVATED", "BOTH");
                }
            }
            return Collections.singletonList("BOTH");
        }

        return Collections.emptyList();
    }
}
