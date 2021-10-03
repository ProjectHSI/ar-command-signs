package project.hsi.commandsigns.menus;

import org.bukkit.entity.Player;
import project.hsi.commandsigns.api.menu.ClickableMessage;
import project.hsi.commandsigns.api.menu.EditionLeaf;
import project.hsi.commandsigns.api.menu.EditionMenu;
import project.hsi.commandsigns.api.menu.MenuNavigationContext;
import project.hsi.commandsigns.model.CommandBlock;


public class CoreMenuDisable extends EditionLeaf<CommandBlock> {

    public CoreMenuDisable(EditionMenu<CommandBlock> parent) {
        super(messages.get("menu.disable.title"), parent);
    }

    @Override
    public String getDataValue(CommandBlock data) {
        if (data.isDisabled()) {
            return messages.get("menu.value.yes");
        }
        return messages.get("menu.value.no");
    }

    @Override
    public void display(Player editor, CommandBlock data, MenuNavigationContext navigationResult) {
        String msg = messages.get("menu.disable.edit");
        ClickableMessage clickableMessage = new ClickableMessage(msg, null);
        clickableMessage.add(CLICKABLE_CANCEL);
        clickableMessage.sendToPlayer(editor);
    }

    @Override
    public void input(final Player player, final CommandBlock data, final String message, final MenuNavigationContext navigationResult) {
        try {
            if (!CANCEL_STRING.equals(message)) {
                String[] args = message.split(" ");
                String val = args[0].toUpperCase();
                data.setDisabled("Y".equals(val) || "YES".equals(val) || "TRUE".equals(val));
            }
        } catch (Exception ignored) {
        } finally {
            navigationResult.setCoreMenu(getParent());
        }
    }
}
