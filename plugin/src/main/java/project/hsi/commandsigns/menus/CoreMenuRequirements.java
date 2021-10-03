package project.hsi.commandsigns.menus;

import project.hsi.commandsigns.api.menu.EditionMenu;
import project.hsi.commandsigns.model.CommandBlock;


public class CoreMenuRequirements extends CoreAddonSubmenusHandler {
    public CoreMenuRequirements(EditionMenu<CommandBlock> parent) {
        super(messages.get("menu.requirements.title"), parent);
    }
}
