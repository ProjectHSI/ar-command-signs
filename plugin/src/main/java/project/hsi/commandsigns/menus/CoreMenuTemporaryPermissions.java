package project.hsi.commandsigns.menus;

import org.bukkit.entity.Player;
import project.hsi.commandsigns.api.menu.EditionMenu;
import project.hsi.commandsigns.api.menu.MenuNavigationContext;
import project.hsi.commandsigns.model.CommandBlock;


public class CoreMenuTemporaryPermissions extends EditionNodeCore {

    public CoreMenuTemporaryPermissions(EditionMenu<CommandBlock> parent) {
        super(messages.get("menu.temporary_permissions.title"), parent);
    }

    @Override
    protected void initializeSubMenus() {
        addMenu(new CoreMenuTemporaryPermissionsAdd(this));
        addMenu(new CoreMenuTemporaryPermissionsEdit(this));
        addMenu(new CoreMenuTemporaryPermissionsRemove(this));
    }

    @Override
    public void display(final Player editor, final CommandBlock data, final MenuNavigationContext navigationContext) {

        displayBreadcrumb(editor);

        final String format = messages.get("menu.temporary_permissions.format");
        int cpt = 1;
        for (String perm : data.getTemporarilyGrantedPermissions()) {
            String msg = format.replace("{NUMBER}", String.valueOf(cpt++)).replace("{PERMISSION}", perm);
            editor.sendMessage(msg);
        }

        displayMenus(editor, data, navigationContext);
    }
}
