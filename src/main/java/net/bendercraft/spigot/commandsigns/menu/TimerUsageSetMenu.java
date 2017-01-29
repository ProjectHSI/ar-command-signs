package net.bendercraft.spigot.commandsigns.menu;

import net.bendercraft.spigot.commandsigns.controller.EditingConfiguration;
import net.bendercraft.spigot.commandsigns.model.CommandBlock;
import net.bendercraft.spigot.commandsigns.utils.Messages;

public class TimerUsageSetMenu extends EditionMenu {

	public TimerUsageSetMenu(EditionMenu parent) {
		super(parent, Messages.get("info.time_between_usages"));
	}

	@Override
	public void display(EditingConfiguration<CommandBlock> config) {
		config.getEditor().sendMessage(Messages.get("menu.time_between_edit"));

	}

	@Override
	public void input(EditingConfiguration<CommandBlock> config, String message) {
		try {
			config.setCurrentMenu(getParent());
			String[] args = message.split(" ", 2);
			long duration = Long.parseLong(args[0]);
			config.getEditingData().setTimeBetweenUsage(duration);
		}
		catch (Exception ignored) {
		}

	}

	@Override
	public String formatName(CommandBlock cmd) {
		//2. Time (30)
		return getName().replace("{TIME}", String.valueOf(cmd.getTimeBetweenUsage()));
	}

}
