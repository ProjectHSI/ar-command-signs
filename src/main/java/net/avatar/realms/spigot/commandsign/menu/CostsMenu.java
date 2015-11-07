package net.avatar.realms.spigot.commandsign.menu;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.avatar.realms.spigot.commandsign.CommandSign;
import net.avatar.realms.spigot.commandsign.model.CommandBlock;
import net.avatar.realms.spigot.commandsign.model.EditingConf;

public class CostsMenu extends EditionMenu {

	public CostsMenu(EditionMenu parent) {
		super(parent, "Costs");
		this.subMenus.put(2, new EconomyMenu(this));
	}

	@Override
	public void display(EditingConf<CommandBlock> config) {
		Player editor = config.getEditor();
		if (editor != null) {
			CommandBlock cmd = config.getEditingData();
			if (cmd != null) {
				editor.sendMessage(c + "1. Refresh");
				editor.sendMessage(c + "2. Economy ("
						+ CommandSign.getPlugin().getEconomy().format(config.getEditingData().getEconomyPrice()) + ")");
				editor.sendMessage(ChatColor.GREEN + "9. Done");
			}
		}
	}

	@Override
	public void input(EditingConf<CommandBlock> config, String message) {
		try {
			String[] args = message.split(" ");
			if (args.length == 0) {
				//No parameters, let's do nothing so that he receives the display message again
				return;
			}
			int index = Integer.parseInt(args[0]);
			if (index == 9) {
				config.setCurrentMenu(getParent());
			}
			else if (this.subMenus.containsKey(index)) {
				IEditionMenu<CommandBlock> newMenu = this.subMenus.get(index);
				config.setCurrentMenu(newMenu);
			}
			else {
				// let's do nothing so that he receives the display message again
			}
		}
		catch (NumberFormatException ex) {
			config.getEditor().sendMessage(ChatColor.DARK_RED + "You must enter a number to go through the menu.");
		}
	}
}
