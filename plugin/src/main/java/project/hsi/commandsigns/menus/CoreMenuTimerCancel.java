package project.hsi.commandsigns.menus;

import org.bukkit.entity.Player;
import project.hsi.commandsigns.api.menu.ClickableMessage;
import project.hsi.commandsigns.api.menu.EditionLeaf;
import project.hsi.commandsigns.api.menu.EditionMenu;
import project.hsi.commandsigns.api.menu.MenuNavigationContext;
import project.hsi.commandsigns.model.CommandBlock;


public class CoreMenuTimerCancel extends EditionLeaf<CommandBlock> {

    public CoreMenuTimerCancel(EditionMenu<CommandBlock> parent) {
        super(messages.get("menu.timer.cancel.title"), parent);
    }

    @Override
    public String getDataValue(CommandBlock data) {
        return data.isCancelledOnMove() ? messages.get("menu.value.yes") : messages.get("menu.value.no");
    }

    @Override
    public void display(Player editor, CommandBlock data, MenuNavigationContext navigationContext) {
        String msg = messages.get("menu.timer.cancel.edit");
        ClickableMessage clickableMessage = new ClickableMessage(msg);
        clickableMessage.add(CLICKABLE_CANCEL);
        clickableMessage.sendToPlayer(editor);
    }

    @Override
    public void input(Player player, CommandBlock data, String message, MenuNavigationContext navigationContext) {
        if (!CANCEL_STRING.equals(message)) {
            String[] args = message.split(" ");
            String arg = args[0].toUpperCase();
            data.setCancelledOnMove("YES".equals(arg) || "Y".equals(arg) || "TRUE".equals(arg));
        }

        navigationContext.setCoreMenu(getParent());
    }
}
