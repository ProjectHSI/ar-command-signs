package project.hsi.commandsigns.menus;


import org.bukkit.Location;
import org.bukkit.entity.Player;
import project.hsi.commandsigns.api.menu.EditionMenu;
import project.hsi.commandsigns.api.menu.EditionNode;
import project.hsi.commandsigns.api.menu.MenuNavigationContext;
import project.hsi.commandsigns.model.CommandBlock;
import project.hsi.commandsigns.utils.CommandBlockValidator;


public abstract class EditionNodeCore extends EditionNode<CommandBlock> {

    public EditionNodeCore(String name, EditionMenu<CommandBlock> parent) {
        super(name, parent);
    }

    public EditionNodeCore(String name) {
        super(name);
    }

    @Override
    public void input(final Player player, final CommandBlock data, final String message, final MenuNavigationContext navigationResult) {
        try {
            final int choice = Integer.parseInt(message);
            final int page = navigationResult.getPage();
            final int numberEntriesToDisplay = getNumberEntriesToDisplay();

            if (0 < choice && choice <= numberEntriesToDisplay) {
                EditionMenu<CommandBlock> menu = menus.get((page - 1) * numberEntriesToDisplay + (choice - 1));
                if (menu.shouldBeDisplayed(data)) {
                    navigationResult.setPage(1);
                    navigationResult.setCoreMenu(menu);
                }
            } else if (choice == DONE) {
                moveToParent(player, data, navigationResult);
            } else if (choice == CANCEL) {
                cancel(player, navigationResult);
            } else if (shouldDisplayNavigation()) {
                if (choice == PREVIOUS && page > 1) navigationResult.setPage(page - 1);
            }
        } catch (NumberFormatException e) {
            player.sendMessage(messages.get("menu.entry.number_required"));
        }
    }

    private void cancel(Player player, MenuNavigationContext navigationResult) {
        navigationResult.setCancelled(true);
        navigationResult.setPage(1);
        navigationResult.setCoreMenu(getParent());
    }

    private void moveToParent(Player player, CommandBlock data, MenuNavigationContext navigationResult) {
        boolean canMoveToParent = true;
        EditionMenu<CommandBlock> parent = getParent();
        if (parent == null) {
            Location lca = data.getLocation();
            if (lca == null || !CommandBlockValidator.isValidBlock(lca.getBlock())) {
                canMoveToParent = false;
            }
        }

        if (canMoveToParent) {
            navigationResult.setPage(1);
            navigationResult.setCoreMenu(parent);
        } else {
            player.sendMessage(messages.get("menu.block.required"));
        }
    }
}
