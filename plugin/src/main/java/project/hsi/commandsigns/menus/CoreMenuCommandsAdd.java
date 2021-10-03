package project.hsi.commandsigns.menus;

import org.bukkit.entity.Player;
import project.hsi.commandsigns.api.menu.ClickableMessage;
import project.hsi.commandsigns.api.menu.EditionLeaf;
import project.hsi.commandsigns.api.menu.EditionMenu;
import project.hsi.commandsigns.api.menu.MenuNavigationContext;
import project.hsi.commandsigns.model.CommandBlock;
import project.hsi.commandsigns.utils.Settings;


public class CoreMenuCommandsAdd extends EditionLeaf<CommandBlock> {

    public CoreMenuCommandsAdd(EditionMenu<CommandBlock> parent) {
        super(messages.get("menu.commands.add.title"), parent);
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
        String msg = messages.get("menu.commands.add.edit");
        ClickableMessage clickableMessage = new ClickableMessage(msg);
        clickableMessage.add(CLICKABLE_CANCEL);
        clickableMessage.sendToPlayer(editor);
    }

    @Override
    public void input(Player player, CommandBlock data, String message, MenuNavigationContext navigationContext) {
        if (!CANCEL_STRING.equals(message)) {
            if (message.startsWith(Character.toString(Settings.SERVER_CHAR())) || message.startsWith(Character.toString(Settings.OP_CHAR()))) {
                if (player.hasPermission("commandsign.admin.set.super")) {
                    data.getCommands().add(message);
                } else {
                    player.sendRawMessage(messages.get("error.no_super_permission"));
                }
            } else {
                data.getCommands().add(message);
            }
        }
        navigationContext.setCoreMenu(getParent());
    }
}
