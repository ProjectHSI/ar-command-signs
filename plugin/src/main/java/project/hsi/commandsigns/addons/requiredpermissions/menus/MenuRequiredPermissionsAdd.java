package project.hsi.commandsigns.addons.requiredpermissions.menus;

import org.bukkit.entity.Player;
import project.hsi.commandsigns.addons.requiredpermissions.RequiredPermissionsAddon;
import project.hsi.commandsigns.addons.requiredpermissions.data.RequiredPermissionsConfigurationData;
import project.hsi.commandsigns.api.addons.AddonConfigurationData;
import project.hsi.commandsigns.api.menu.AddonEditionLeaf;
import project.hsi.commandsigns.api.menu.AddonEditionMenu;
import project.hsi.commandsigns.api.menu.ClickableMessage;
import project.hsi.commandsigns.api.menu.MenuNavigationContext;


public class MenuRequiredPermissionsAdd extends AddonEditionLeaf {

    public MenuRequiredPermissionsAdd(RequiredPermissionsAddon addon, AddonEditionMenu parent) {
        super(addon, messages.get("menu.required_permissions.add.title"), parent);
    }

    @Override
    public String getDisplayString(AddonConfigurationData data) {
        return messages.get("menu.entry.display_name_only").replace("{NAME}", name);
    }

    @Override
    public String getDataValue(AddonConfigurationData data) {
        return "";
    }

    @Override
    public void display(Player editor, AddonConfigurationData data, MenuNavigationContext navigationContext) {
        String msg = messages.get("menu.required_permissions.add.edit");
        ClickableMessage clickableMessage = new ClickableMessage(msg);
        clickableMessage.add(CLICKABLE_CANCEL);
        clickableMessage.sendToPlayer(editor);
    }

    @Override
    public void input(Player player, AddonConfigurationData data, String message, MenuNavigationContext navigationContext) {
        if (!CANCEL_STRING.equals(message)) {
            final RequiredPermissionsConfigurationData configurationData = (RequiredPermissionsConfigurationData) data;
            configurationData.getRequiredPermissions().add(message);
        }
        navigationContext.setAddonMenu(getParent());
    }
}
