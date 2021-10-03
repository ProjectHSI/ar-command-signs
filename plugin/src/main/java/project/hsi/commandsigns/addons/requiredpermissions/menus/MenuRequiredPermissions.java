package project.hsi.commandsigns.addons.requiredpermissions.menus;

import org.bukkit.entity.Player;
import project.hsi.commandsigns.addons.requiredpermissions.RequiredPermissionsAddon;
import project.hsi.commandsigns.addons.requiredpermissions.data.RequiredPermissionsConfigurationData;
import project.hsi.commandsigns.api.addons.AddonConfigurationData;
import project.hsi.commandsigns.api.menu.AddonEditionNode;
import project.hsi.commandsigns.api.menu.MenuNavigationContext;


public class MenuRequiredPermissions extends AddonEditionNode {

    public MenuRequiredPermissions(RequiredPermissionsAddon addon) {
        super(addon, messages.get("menu.required_permissions.title"));
    }

    @Override
    protected void initializeSubMenus() {
        addMenu(new MenuRequiredPermissionsAdd((RequiredPermissionsAddon) addon, this));
        addMenu(new MenuRequiredPermissionsEdit((RequiredPermissionsAddon) addon, this));
        addMenu(new MenuRequiredPermissionsRemove((RequiredPermissionsAddon) addon, this));
    }

    @Override
    public void display(final Player editor, final AddonConfigurationData data, final MenuNavigationContext navigationContext) {
        final RequiredPermissionsConfigurationData configurationData = (RequiredPermissionsConfigurationData) data;

        displayBreadcrumb(editor);

        final String format = messages.get("menu.required_permissions.format");
        int cpt = 1;
        for (String perm : configurationData.getRequiredPermissions()) {
            String msg = format.replace("{NUMBER}", String.valueOf(cpt++)).replace("{PERMISSION}", perm);
            editor.sendMessage(msg);
        }

        displayMenus(editor, data, navigationContext);
    }
}
