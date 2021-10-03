package project.hsi.commandsigns.addons.cooldowns.menus;

import project.hsi.commandsigns.api.addons.Addon;
import project.hsi.commandsigns.api.menu.AddonEditionNode;


public class MenuCooldowns extends AddonEditionNode {

    public MenuCooldowns(Addon addon) {
        super(addon, messages.get("menu.cooldowns.title"));
    }

    @Override
    protected void initializeSubMenus() {
        addMenu(new MenuCooldownGlobal(addon, this));
        addMenu(new MenuCooldownPlayer(addon, this));
        addMenu(new MenuCooldownGlobalOnce(addon, this));
        addMenu(new MenuCooldownPlayerOnce(addon, this));
    }
}
