package project.hsi.commandsigns.menus;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import project.hsi.commandsigns.api.menu.ClickableMessage;
import project.hsi.commandsigns.api.menu.EditionLeaf;
import project.hsi.commandsigns.api.menu.EditionMenu;
import project.hsi.commandsigns.api.menu.MenuNavigationContext;
import project.hsi.commandsigns.model.BlockActivationMode;
import project.hsi.commandsigns.model.CommandBlock;
import project.hsi.commandsigns.utils.CommandBlockValidator;


public class CoreMenuActivationMode extends EditionLeaf<CommandBlock> {

    public CoreMenuActivationMode(EditionMenu<CommandBlock> parent) {
        super(messages.get("menu.activation_mode.title"), parent);
    }

    @Override
    public boolean shouldBeDisplayed(CommandBlock data) {
        Location location = data.getLocation();
        if (location != null) return CommandBlockValidator.isLever(location.getBlock());
        return false;
    }

    @Override
    public String getDataValue(CommandBlock data) {
        return data.getActivationMode().name();
    }

    @Override
    public void display(Player editor, CommandBlock data, MenuNavigationContext navigationResult) {
        String msg = messages.get("menu.activation_mode.edit");
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
                if ("ACTIVATED".equals(val)) {
                    data.setActivationMode(BlockActivationMode.ACTIVATED);
                } else if ("DEACTIVATED".equals(val)) {
                    data.setActivationMode(BlockActivationMode.DEACTIVATED);
                } else {
                    data.setActivationMode(BlockActivationMode.BOTH);
                }
            }
        } catch (Exception ignored) {
        } finally {
            navigationResult.setCoreMenu(getParent());
        }
    }
}
