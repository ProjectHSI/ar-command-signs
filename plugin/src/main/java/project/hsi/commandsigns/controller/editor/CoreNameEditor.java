package project.hsi.commandsigns.controller.editor;

import org.bukkit.command.CommandSender;
import project.hsi.commandsigns.model.CommandBlock;
import project.hsi.commandsigns.model.CommandSignsCommandException;

import java.util.List;

public final class CoreNameEditor extends CommandBlockDataEditorBase {

    @Override
    public void editValue(CommandBlock commandBlock, List<String> args, CommandSender sender) throws CommandSignsCommandException {
        String name = String.join(" ", args).trim();
        if (name.isEmpty()) {
            throw new CommandSignsCommandException(messages.get("error.command.set.name.invalid"));
        }
        commandBlock.setName(name);
    }

}
