package be.nokorbis.spigot.commandsigns.menus;

import be.nokorbis.spigot.commandsigns.api.menu.*;
import be.nokorbis.spigot.commandsigns.model.CommandBlock;
import org.bukkit.entity.Player;


public class CoreMenuCommandsAdd extends EditionLeaf<CommandBlock> {

	public CoreMenuCommandsAdd(EditionMenu<CommandBlock> parent) {
		super(messages.get("menu.commands.add.title"), parent);
	}

	@Override
	public String getDisplayString(CommandBlock data) {
		return messages.get("menu.entry.display_name_only").replace("{NAME}", name);
	}

	@Override
	public String getDataValue(CommandBlock data) {
		return "";
	}

	@Override
	public void display(Player editor, CommandBlock data, MenuNavigationContext navigationContext) {
		String msg = messages.get("menu.commands.add.edit");
		ClickableMessage clickableMessage = new ClickableMessage(msg);
		clickableMessage.add(CLICKABLE_CANCEL);
		clickableMessage.sendToPlayer(editor);
	}

	@Override
	public void input(Player player, CommandBlock data, String message, MenuNavigationContext navigationContext) {
		if (!CANCEL_STRING.equals(message)) {
			if (message.startsWith("#") || message.startsWith("^")) {
				if (player.hasPermission("commandsign.admin.set.super")) {
					data.getCommands().add(message);
				} else {
					player.sendRawMessage(messages.get("error.no_super_permission"));
				}
			} else {
				data.getCommands().add(message);
			}
		}
		navigationContext.setCoreMenu(getParent());
	}
}
