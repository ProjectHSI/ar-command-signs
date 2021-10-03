package project.hsi.commandsigns.addons.cooldowns.menus;

import org.bukkit.entity.Player;
import project.hsi.commandsigns.addons.cooldowns.data.CooldownConfigurationData;
import project.hsi.commandsigns.api.addons.Addon;
import project.hsi.commandsigns.api.addons.AddonConfigurationData;
import project.hsi.commandsigns.api.menu.AddonEditionLeaf;
import project.hsi.commandsigns.api.menu.AddonEditionMenu;
import project.hsi.commandsigns.api.menu.ClickableMessage;
import project.hsi.commandsigns.api.menu.MenuNavigationContext;


public class MenuCooldownGlobalOnce extends AddonEditionLeaf {

    public MenuCooldownGlobalOnce(final Addon addon, AddonEditionMenu parent) {
        super(addon, messages.get("menu.cooldowns.global_once.title"), parent);
    }

    @Override
    public String getDataValue(AddonConfigurationData data) {
        CooldownConfigurationData configurationData = (CooldownConfigurationData) data;
        if (configurationData.isGlobalOnlyOnce()) {
            return messages.get("menu.value.yes");
        }
        return messages.get("menu.value.no");
    }

    @Override
    public void display(Player editor, AddonConfigurationData data, MenuNavigationContext navigationContext) {
        final String msg = messages.get("menu.cooldowns.global_once.edit");
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
                String val = args[0].toUpperCase();
                configurationData.setGlobalOnlyOnce("Y".equals(val) || "YES".equals(val) || "TRUE".equals(val));
            }
        } catch (Exception ignored) {
        } finally {
            navigationContext.setAddonMenu(getParent());
        }
    }
}