package project.hsi.commandsigns.menus;

import project.hsi.commandsigns.api.menu.EditionMenu;
import project.hsi.commandsigns.model.CommandBlock;


public class CoreMenuTimer extends EditionNodeCore {

    public CoreMenuTimer(EditionMenu<CommandBlock> parent) {
        super(messages.get("menu.timer.title"), parent);
    }

    @Override
    protected void initializeSubMenus() {
        addMenu(new CoreMenuTimerTime(this));
        addMenu(new CoreMenuTimerCancel(this));
        addMenu(new CoreMenuTimerReset(this));
    }
}
