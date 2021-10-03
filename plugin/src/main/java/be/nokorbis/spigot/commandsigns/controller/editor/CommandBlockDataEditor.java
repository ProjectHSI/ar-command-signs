package be.nokorbis.spigot.commandsigns.controller.editor;

import be.nokorbis.spigot.commandsigns.model.CommandBlock;
import be.nokorbis.spigot.commandsigns.model.CommandSignsCommandException;
import org.bukkit.command.CommandSender;

import java.util.List;

public interface CommandBlockDataEditor {

    void editValue(CommandBlock commandBlock, List<String> args, CommandSender sender) throws CommandSignsCommandException;
    List<String> onTabComplete(CommandBlock data, List<String> args);

}
