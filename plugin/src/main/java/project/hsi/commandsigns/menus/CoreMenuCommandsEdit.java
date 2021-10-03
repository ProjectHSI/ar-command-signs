package project.hsi.commandsigns.menus;

import org.bukkit.entity.Player;
import project.hsi.commandsigns.api.menu.ClickableMessage;
import project.hsi.commandsigns.api.menu.EditionLeaf;
import project.hsi.commandsigns.api.menu.EditionMenu;
import project.hsi.commandsigns.api.menu.MenuNavigationContext;
import project.hsi.commandsigns.model.CommandBlock;
import project.hsi.commandsigns.utils.Settings;


public class CoreMenuCommandsEdit extends EditionLeaf<CommandBlock> {

    public CoreMenuCommandsEdit(EditionMenu<CommandBlock> parent) {
        super(messages.get("menu.commands.edit.title"), parent);
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
        editor.sendMessage(messages.get("menu.commands.display"));
        int cpt = 1;
        String format = messages.get("menu.commands.format");
        for (String perm : data.getCommands()) {
            String msg = format.replace("{NUMBER}", String.valueOf(cpt++)).replace("{COMMAND}", perm);
            editor.sendMessage(msg);
        }
        String msg = messages.get("menu.commands.edit.edit");
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
                if (args[1].startsWith(Character.toString(Settings.SERVER_CHAR())) || args[1].startsWith(Character.toString(Settings.OP_CHAR()))) {
                    if (player.hasPermission("commandsign.admin.set.super")) {
                        data.getCommands().set(index - 1, args[1]);
                    } else {
                        player.sendRawMessage(messages.get("error.no_super_permission"));
                    }
                } else {
                    data.getCommands().set(index - 1, args[1]);
                }
            }
        } catch (Exception ignored) {
        } finally {
            navigationContext.setCoreMenu(getParent());
        }
    }
}
