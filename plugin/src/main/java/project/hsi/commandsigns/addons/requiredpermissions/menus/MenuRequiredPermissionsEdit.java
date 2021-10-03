package project.hsi.commandsigns.addons.requiredpermissions.menus;

import org.bukkit.entity.Player;
import project.hsi.commandsigns.addons.requiredpermissions.RequiredPermissionsAddon;
import project.hsi.commandsigns.addons.requiredpermissions.data.RequiredPermissionsConfigurationData;
import project.hsi.commandsigns.api.addons.AddonConfigurationData;
import project.hsi.commandsigns.api.menu.AddonEditionLeaf;
import project.hsi.commandsigns.api.menu.AddonEditionMenu;
import project.hsi.commandsigns.api.menu.ClickableMessage;
import project.hsi.commandsigns.api.menu.MenuNavigationContext;


public class MenuRequiredPermissionsEdit extends AddonEditionLeaf {

    public MenuRequiredPermissionsEdit(RequiredPermissionsAddon addon, AddonEditionMenu parent) {
        super(addon, messages.get("menu.required_permissions.edit.title"), parent);
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
    public void display(final Player editor, AddonConfigurationData data, MenuNavigationContext navigationContext) {
        final RequiredPermissionsConfigurationData configurationData = (RequiredPermissionsConfigurationData) data;
        editor.sendMessage(messages.get("menu.required_permissions.display"));
        int cpt = 1;
        String format = messages.get("menu.required_permissions.format");
        for (String perm : configurationData.getRequiredPermissions()) {
            String msg = format.replace("{NUMBER}", String.valueOf(cpt++)).replace("{PERMISSION}", perm);
            editor.sendMessage(msg);
        }
        String msg = messages.get("menu.required_permissions.edit.edit");
        ClickableMessage clickableMessage = new ClickableMessage(msg);
        clickableMessage.add(CLICKABLE_CANCEL);
        clickableMessage.sendToPlayer(editor);
    }

    @Override
    public void input(Player player, AddonConfigurationData data, String message, MenuNavigationContext navigationContext) {
        try {
            if (!CANCEL_STRING.equals(message)) {
                RequiredPermissionsConfigurationData configurationData = (RequiredPermissionsConfigurationData) data;
                String[] args = message.split(" ", 2);
                int index = Integer.parseInt(args[0]);
                configurationData.getRequiredPermissions().set(index - 1, args[1]);
            }
        } catch (Exception ignored) {
        } finally {
            navigationContext.setAddonMenu(getParent());
        }
    }
}
