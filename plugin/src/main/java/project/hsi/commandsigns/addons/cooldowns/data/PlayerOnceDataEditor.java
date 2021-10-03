package project.hsi.commandsigns.addons.cooldowns.data;

import org.bukkit.command.CommandSender;
import project.hsi.commandsigns.addons.cooldowns.CooldownAddon;
import project.hsi.commandsigns.api.DisplayMessages;
import project.hsi.commandsigns.api.addons.AddonConfigurationData;
import project.hsi.commandsigns.api.addons.AddonConfigurationDataEditorBase;
import project.hsi.commandsigns.model.CommandSignsCommandException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlayerOnceDataEditor extends AddonConfigurationDataEditorBase {

    private static final DisplayMessages messages = DisplayMessages.getDisplayMessages("messages/addons");

    public PlayerOnceDataEditor(final CooldownAddon addon) {
        super(addon);
    }

    @Override
    public void editValue(AddonConfigurationData configurationData, List<String> args) throws CommandSignsCommandException {
        if (args.isEmpty()) {
            throw new CommandSignsCommandException(messages.get("error.commmand.require_args"));
        }

        CooldownConfigurationData data = (CooldownConfigurationData) configurationData;
        String value = args.remove(0);
        boolean newValue = parseBooleanValue(value);
        data.setPlayerOnlyOnce(newValue);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, AddonConfigurationData configurationData, List<String> args) {
        if (args.size() < 2) {
            return Arrays.asList("Yes", "No");
        }
        return Collections.emptyList();
    }
}
