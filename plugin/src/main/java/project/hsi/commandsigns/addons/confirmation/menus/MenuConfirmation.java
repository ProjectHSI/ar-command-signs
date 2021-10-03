package project.hsi.commandsigns.addons.confirmation.menus;

import org.bukkit.entity.Player;
import project.hsi.commandsigns.addons.confirmation.ConfirmationAddon;
import project.hsi.commandsigns.addons.confirmation.data.ConfirmationConfigurationData;
import project.hsi.commandsigns.api.addons.AddonConfigurationData;
import project.hsi.commandsigns.api.menu.AddonEditionLeaf;
import project.hsi.commandsigns.api.menu.ClickableMessage;
import project.hsi.commandsigns.api.menu.MenuNavigationContext;

public class MenuConfirmation extends AddonEditionLeaf {

    public MenuConfirmation(ConfirmationAddon addon) {
        super(addon, messages.get("menu.confirmation.title"), null);
    }

    @Override
    public String getDataValue(AddonConfigurationData data) {
        ConfirmationConfigurationData configurationData = (ConfirmationConfigurationData) data;
        if (configurationData.isRequireConfirmation()) {
            return messages.get("menu.value.yes");
        }
        return messages.get("menu.value.no");
    }

    @Override
    public void display(Player editor, AddonConfigurationData data, MenuNavigationContext navigationContext) {
        final String msg = messages.get("menu.confirmation.edit");
        ClickableMessage clickableMessage = new ClickableMessage(msg);
        clickableMessage.add(CLICKABLE_CANCEL);
        clickableMessage.sendToPlayer(editor);
    }

    @Override
    public void input(Player player, AddonConfigurationData data, String message, MenuNavigationContext navigationContext) {
        try {
            if (!CANCEL_STRING.equals(message)) {
                ConfirmationConfigurationData configurationData = (ConfirmationConfigurationData) data;
                String[] args = message.split(" ", 2);
                String val = args[0];
                configurationData.parseRequireConfirmation(val);
            }
        } catch (Exception ignored) {
        } finally {
            navigationContext.setAddonMenu(getParent());
        }
    }
}
