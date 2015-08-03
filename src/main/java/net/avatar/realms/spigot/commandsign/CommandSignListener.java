package net.avatar.realms.spigot.commandsign;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.avatar.realms.spigot.commandsign.model.CommandBlock;
import net.avatar.realms.spigot.commandsign.model.EditingConfiguration;


public class CommandSignListener implements Listener{

	private CommandSign plugin;
	
	public CommandSignListener (CommandSign plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBlockBreakEvent (BlockBreakEvent event) {
		Block block =  event.getBlock();
		// This is a command block, so this should not be delete
		if (plugin.getCommandBlocks().containsKey(block)) {
			Player player = event.getPlayer();
			if (player != null) {
				player.sendMessage(ChatColor.RED + "This block is a command block. You must remove the commands before deleting it.");
			}
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onChatEvent (AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		
		if (!(plugin.getCreatingConfigurations().containsKey(player) || plugin.getEditingConfigurations().containsKey(player))) {
			return;
		}
		
		EditingConfiguration conf = plugin.getCreatingConfigurations().get(player);
		if (conf == null) {
			conf = plugin.getEditingConfigurations().get(player);
		}
		
		String str = event.getMessage();
		conf.input(str);
		conf.display();
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onInteractEvent (PlayerInteractEvent event) {
		
		Block block = event.getClickedBlock();
		Player player = event.getPlayer();
		
		/* Do we have to delete this command block ? */
		if (plugin.getDeletingBlocks().containsKey(player)) {
			if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				return;
			}
			deleteCommandBlock(player, block);
		}
		
		/* Do we have to edit the command block configuration ? */
		else if (plugin.getEditingConfigurations().containsKey(player)) {
			if (!CommandSign.VALID_MATERIALS.contains(block.getType())) {
				return;
			}
			
			EditingConfiguration conf = plugin.getEditingConfigurations().get(player);
			CommandBlock commandBlock = conf.getCommandBlock();
			
			// We want to select the block to edit.
			if (commandBlock == null) {
				// The block we hit is a valid block
				if (plugin.getCommandBlocks().containsKey(block)) {
					CommandBlock editingBlock = plugin.getCommandBlocks().get(block);
					conf.setCommandBlock(editingBlock);
					conf.display();
				}
			}
			// We've already selected the block we want to edit
			else {
				// Nothing to do, I think
			}
		}
		
		/* Do we have to create the command block configuration ? */
		else if (plugin.getCreatingConfigurations().containsKey(player)) {
			if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				return;
			}
			createCommandBlock(player, block);
		}
		
		/* Do we have to copy the command block configuration ? */
		else if (plugin.getCopyingConfigurations().containsKey(player)) {
			if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				return;
			}
			copyCommandBlock(player, block);
		}
		
		else if (plugin.getInfoPlayers().contains(player)) {
			if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				return;
			}
			info (player, block);
		}
		
		/* Is that a block that we can execute ? */
		else if (plugin.getCommandBlocks().containsKey(block)) {
			plugin.getCommandBlocks().get(block).execute(player);
		}
	}

	private void info(Player player, Block block) {
		if (!CommandSign.VALID_MATERIALS.contains(block.getType())) {
			player.sendMessage(ChatColor.RED + "Invalid block. Aborting info command");
			plugin.getInfoPlayers().remove(player);
			return;
		}
		
		if (plugin.getCommandBlocks().containsKey(block)) {
			plugin.getCommandBlocks().get(block).info(player, ChatColor.DARK_GREEN);
			plugin.getInfoPlayers().remove(player);
		}
		else {
			player.sendMessage(ChatColor.RED + "Invalid block. Aborting info command");
			plugin.getInfoPlayers().remove(player);
		}
	}

	private void createCommandBlock(Player player, Block block) {
		if (!CommandSign.VALID_MATERIALS.contains(block.getType())) {
			return;
		}
		
		EditingConfiguration conf = plugin.getCreatingConfigurations().get(player);
		CommandBlock commandBlock = conf.getCommandBlock();
		Block creatingBlock = commandBlock.getBlock();
		
		if (creatingBlock == null) {
			if (plugin.getCommandBlocks().containsKey(block)) {
				player.sendMessage(ChatColor.RED + "This block is already a command block.");
			}
			else {
				commandBlock.setBlock(block);
				player.sendMessage(ChatColor.GREEN + "Block set to command block configuration");
			}
		}
		else {
			player.sendMessage(ChatColor.RED + "This configuration already has a block.");
		}
	}

	private void copyCommandBlock(Player player, Block block) {
		if (!CommandSign.VALID_MATERIALS.contains(block.getType())) {
			player.sendMessage(ChatColor.RED + "Not a valid block. Aborting copying.");
			plugin.getCopyingConfigurations().remove(player);
			return;
		}
		
		CommandBlock copyingBlock = plugin.getCopyingConfigurations().get(player);
		if (copyingBlock == null) {
			if (plugin.getCommandBlocks().containsKey(block)) {
				copyingBlock = plugin.getCommandBlocks().get(block);
				plugin.getCopyingConfigurations().put(player, copyingBlock.copy());
				player.sendMessage(ChatColor.GOLD + "Block copied. Click on another block to paste the configuration.");
			}
			else {
				player.sendMessage(ChatColor.RED + "This is not a command block. Aborting copying.");
				plugin.getCopyingConfigurations().remove(player);
			}
		}
		else if (plugin.getCommandBlocks().containsKey(block)) {
			player.sendMessage(ChatColor.RED + "This block is already a command block. Aborting copying.");
			plugin.getCopyingConfigurations().remove(player);
		}
		else {
			copyingBlock.setBlock(block);
			plugin.getCommandBlocks().put(block, copyingBlock);
			plugin.getCopyingConfigurations().remove(player);
			player.sendMessage(ChatColor.GREEN + "Block properly copied.");
		}
	}

	private void deleteCommandBlock(Player player, Block block) {
		if (!CommandSign.VALID_MATERIALS.contains(block.getType())) {
			player.sendMessage(ChatColor.RED + "Not a valid block. Aborting deletion.");
			plugin.getDeletingBlocks().remove(player);
			return;
		}
		Block deletingBlock = plugin.getDeletingBlocks().get(player);
		if (deletingBlock == null) {
			/* Is it a command block ?*/
			if (plugin.getCommandBlocks().containsKey(block)) {
				plugin.getDeletingBlocks().put(player, block);
				player.sendMessage(ChatColor.GOLD + "Block selected. Click on it again to accept deletion.");
			}
			else {
				player.sendMessage(ChatColor.RED + "This is not a command block. Aborting deletion.");
				plugin.getDeletingBlocks().remove(player);
			}
			
		}
		else if (block.equals(deletingBlock)){
			plugin.getCommandBlocks().remove(block);
			plugin.getDeletingBlocks().remove(player);
			player.sendMessage(ChatColor.GREEN + "Command block properly deleted");
		}
	}
}
