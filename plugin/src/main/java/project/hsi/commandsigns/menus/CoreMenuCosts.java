package project.hsi.commandsigns.menus;

import project.hsi.commandsigns.api.menu.EditionMenu;
import project.hsi.commandsigns.model.CommandBlock;


public class CoreMenuCosts extends CoreAddonSubmenusHandler {

    public CoreMenuCosts(EditionMenu<CommandBlock> parent) {
        super(messages.get("menu.costs.title"), parent);
    }

}
