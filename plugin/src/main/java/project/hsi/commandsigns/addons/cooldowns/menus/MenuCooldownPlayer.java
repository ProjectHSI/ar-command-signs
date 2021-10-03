package project.hsi.commandsigns.addons.cooldowns.menus;

import org.bukkit.entity.Player;
import project.hsi.commandsigns.addons.cooldowns.data.CooldownConfigurationData;
import project.hsi.commandsigns.api.addons.Addon;
import project.hsi.commandsigns.api.addons.AddonConfigurationData;
import project.hsi.commandsigns.api.menu.AddonEditionLeaf;
import project.hsi.commandsigns.api.menu.AddonEditionMenu;
import project.hsi.commandsigns.api.menu.ClickableMessage;
import project.hsi.commandsigns.api.menu.MenuNavigationContext;


public class MenuCooldownPlayer extends AddonEditionLeaf {

    public MenuCooldownPlayer(Addon addon, AddonEditionMenu parent) {
        super(addon, messages.get("menu.cooldowns.player.title"), parent);
    }

    @Override
    public String getDataValue(AddonConfigurationData data) {
        CooldownConfigurationData configurationData = (CooldownConfigurationData) data;
        return String.valueOf(configurationData.getPlayerCooldown());
    }

    @Override
    public void display(Player editor, AddonConfigurationData data, MenuNavigationContext navigationContext) {
        final String msg = messages.get("menu.cooldowns.player.edit");
        ClickableMessage clickableMessage = new ClickableMessage(msg);
        clickableMessage.add(CLICKABLE_CANCEL);
        clickableMessage.sendToPlayer(editor);
    }

    @Override
    public void input(Player player, AddonConfigurationData data, String message, MenuNavigationContext navigationContext) {
        try {
            if (!CANCEL_STRING.equals(message)) {
                CooldownConfigurationData configurationData = (CooldownConfigurationData) data;
                String[] args = message.split(" ", 2);
                long duration = Long.parseLong(args[0]);
                configurationData.setPlayerCooldown(duration);
            }
        } catch (Exception ignored) {
        } finally {
            navigationContext.setAddonMenu(getParent());
        }
    }
}
