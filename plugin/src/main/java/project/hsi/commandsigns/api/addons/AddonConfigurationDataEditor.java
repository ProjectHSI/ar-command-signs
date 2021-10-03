package project.hsi.commandsigns.api.addons;

import org.bukkit.command.CommandSender;
import project.hsi.commandsigns.model.CommandSignsCommandException;

import java.util.List;


public interface AddonConfigurationDataEditor extends AddonRelated {

    void editValue(AddonConfigurationData configurationData, List<String> args) throws CommandSignsCommandException;

    List<String> onTabComplete(CommandSender sender, AddonConfigurationData configurationData, List<String> args);
}
