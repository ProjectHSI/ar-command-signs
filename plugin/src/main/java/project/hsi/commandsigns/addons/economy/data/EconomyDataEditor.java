package project.hsi.commandsigns.addons.economy.data;

import org.bukkit.command.CommandSender;
import project.hsi.commandsigns.addons.economy.EconomyAddon;
import project.hsi.commandsigns.api.DisplayMessages;
import project.hsi.commandsigns.api.addons.AddonConfigurationData;
import project.hsi.commandsigns.api.addons.AddonConfigurationDataEditorBase;
import project.hsi.commandsigns.model.CommandSignsCommandException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EconomyDataEditor extends AddonConfigurationDataEditorBase {

    private static final DisplayMessages messages = DisplayMessages.getDisplayMessages("messages/addons");

    public EconomyDataEditor(final EconomyAddon addon) {
        super(addon);
    }

    @Override
    public void editValue(AddonConfigurationData configurationData, List<String> args) throws CommandSignsCommandException {
        if (args.isEmpty()) {
            throw new CommandSignsCommandException(messages.get("error.command.require_args"));
        }

        try {
            EconomyConfigurationData data = (EconomyConfigurationData) configurationData;
            double value = Double.parseDouble(args.remove(0));
            data.setPrice(value);
        } catch (NumberFormatException e) {
            throw new CommandSignsCommandException(messages.get("error.command.require_number"));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, AddonConfigurationData configurationData, List<String> args) {
        if (args.isEmpty() || (args.size() == 1 && "".equals(args.get(0).trim()))) {
            return Arrays.asList("0", "10", "50", "100", "500", "1000");
        }
        return Collections.emptyList();
    }
}
