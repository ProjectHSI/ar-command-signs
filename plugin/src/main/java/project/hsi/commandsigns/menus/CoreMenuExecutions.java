package project.hsi.commandsigns.menus;

import project.hsi.commandsigns.api.menu.EditionMenu;
import project.hsi.commandsigns.model.CommandBlock;


public class CoreMenuExecutions extends EditionNodeCore {

    public CoreMenuExecutions(EditionMenu<CommandBlock> parent) {
        super(messages.get("menu.executions.title"), parent);
    }

    @Override
    protected void initializeSubMenus() {
        addMenu(new CoreMenuTemporaryPermissions(this));
        addMenu(new CoreMenuCommands(this));
    }
}
