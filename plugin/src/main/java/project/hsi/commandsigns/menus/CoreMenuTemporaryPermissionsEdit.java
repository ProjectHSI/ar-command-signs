package project.hsi.commandsigns.menus;

import org.bukkit.entity.Player;
import project.hsi.commandsigns.api.menu.ClickableMessage;
import project.hsi.commandsigns.api.menu.EditionLeaf;
import project.hsi.commandsigns.api.menu.EditionMenu;
import project.hsi.commandsigns.api.menu.MenuNavigationContext;
import project.hsi.commandsigns.model.CommandBlock;
import project.hsi.commandsigns.utils.Settings;


public class CoreMenuTemporaryPermissionsEdit extends EditionLeaf<CommandBlock> {

    public CoreMenuTemporaryPermissionsEdit(EditionMenu<CommandBlock> parent) {
        super(messages.get("menu.temporary_permissions.edit.title"), parent);
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
    public void display(final Player editor, CommandBlock data, MenuNavigationContext navigationContext) {
        editor.sendMessage(messages.get("menu.temporary_permissions.display"));
        int cpt = 1;
        final String format = messages.get("menu.temporary_permissions.format");
        for (String perm : data.getTemporarilyGrantedPermissions()) {
            String msg = format.replace("{NUMBER}", String.valueOf(cpt++)).replace("{PERMISSION}", perm);
            editor.sendMessage(msg);
        }

        String msg = messages.get("menu.temporary_permissions.edit.edit");
        ClickableMessage clickableMessage = new ClickableMessage(msg);
        clickableMessage.add(CLICKABLE_CANCEL);
        clickableMessage.sendToPlayer(editor);
    }

    @Override
    public void input(Player player, CommandBlock data, String message, MenuNavigationContext navigationContext) {
        try {
            if (!CANCEL_STRING.equals(message)) {
                String[] args = message.split(" ", 2);
                int index = Integer.parseInt(args[0]);
                if (player.hasPermission("commandsign.admin.set.perms")) {
                    data.getTemporarilyGrantedPermissions().add(message);
                } else {
                    player.sendRawMessage(messages.get("error.no_perms_permission"));
                }
            }
        } catch (Exception ignored) {
        } finally {
            navigationContext.setCoreMenu(getParent());
        }
    }
}
