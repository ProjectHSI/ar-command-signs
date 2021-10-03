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

public class PlayerCooldownDataEditor extends AddonConfigurationDataEditorBase {

    private static final DisplayMessages messages = DisplayMessages.getDisplayMessages("messages/addons");

    public PlayerCooldownDataEditor(final CooldownAddon addon) {
        super(addon);
    }

    @Override
    public void editValue(AddonConfigurationData configurationData, List<String> args) throws CommandSignsCommandException {
        if (args.isEmpty()) {
            throw new CommandSignsCommandException(messages.get("error.commmand.require_args"));
        }

        try {
            String value = args.remove(0);
            long cooldown = Long.parseLong(value);
            CooldownConfigurationData data = (CooldownConfigurationData) configurationData;
            data.setPlayerCooldown(cooldown);
        } catch (NumberFormatException e) {
            throw new CommandSignsCommandException(messages.get("error.command.require_number"));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, AddonConfigurationData configurationData, List<String> args) {
        if (args.isEmpty()) {
            return getDefaultTimes();
        }
        if (args.size() == 1) {
            String arg = args.remove(0).trim();
            if ("".equals(arg)) {
                return getDefaultTimes();
            }
            return Collections.singletonList(arg);
        }

        return Collections.emptyList();
    }

    private List<String> getDefaultTimes() {
        return Arrays.asList("60", "300", "600", "3600", "86400");
    }
}
