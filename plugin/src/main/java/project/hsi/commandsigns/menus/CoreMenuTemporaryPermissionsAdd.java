package project.hsi.commandsigns.menus;

import org.bukkit.entity.Player;
import project.hsi.commandsigns.api.menu.ClickableMessage;
import project.hsi.commandsigns.api.menu.EditionLeaf;
import project.hsi.commandsigns.api.menu.EditionMenu;
import project.hsi.commandsigns.api.menu.MenuNavigationContext;
import project.hsi.commandsigns.model.CommandBlock;


public class CoreMenuTemporaryPermissionsAdd extends EditionLeaf<CommandBlock> {

    public CoreMenuTemporaryPermissionsAdd(EditionMenu<CommandBlock> parent) {
        super(messages.get("menu.temporary_permissions.add.title"), parent);
    }

    @Override
    public String getDisplayString(CommandBlock data) {
        return messages.get("menu.entry.display_name_only").replace("{NAME}", name);
    }

    @Override
    public String getDataValue(CommandBlock data) {
        return "";
    }

    @Override
    public void display(Player editor, CommandBlock data, MenuNavigationContext navigationContext) {
        String msg = messages.get("menu.temporary_permissions.add.edit");
        ClickableMessage clickableMessage = new ClickableMessage(msg);
        clickableMessage.add(CLICKABLE_CANCEL);
        clickableMessage.sendToPlayer(editor);
    }

    @Override
    public void input(Player player, CommandBlock data, String message, MenuNavigationContext navigationContext) {
        if (!CANCEL_STRING.equals(message)) {
            data.getTemporarilyGrantedPermissions().add(message);
        }
        navigationContext.setCoreMenu(getParent());
    }
}
