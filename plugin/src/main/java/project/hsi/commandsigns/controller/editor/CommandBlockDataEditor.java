package project.hsi.commandsigns.controller.editor;

import org.bukkit.command.CommandSender;
import project.hsi.commandsigns.model.CommandBlock;
import project.hsi.commandsigns.model.CommandSignsCommandException;

import java.util.List;

public interface CommandBlockDataEditor {

    void editValue(CommandBlock commandBlock, List<String> args, CommandSender sender) throws CommandSignsCommandException;

    List<String> onTabComplete(CommandBlock data, List<String> args);

}
