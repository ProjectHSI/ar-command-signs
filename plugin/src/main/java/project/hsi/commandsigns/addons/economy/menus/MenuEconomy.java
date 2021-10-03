package project.hsi.commandsigns.addons.economy.menus;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import project.hsi.commandsigns.addons.economy.EconomyAddon;
import project.hsi.commandsigns.addons.economy.data.EconomyConfigurationData;
import project.hsi.commandsigns.api.addons.AddonConfigurationData;
import project.hsi.commandsigns.api.menu.AddonEditionLeaf;
import project.hsi.commandsigns.api.menu.ClickableMessage;
import project.hsi.commandsigns.api.menu.MenuNavigationContext;


public class MenuEconomy extends AddonEditionLeaf {

    private Economy economy;

    public MenuEconomy(EconomyAddon addon, Economy economy) {
        super(addon, messages.get("menu.economy.title"), null);
        this.economy = economy;
    }

    @Override
    public String getDataValue(AddonConfigurationData data) {
        EconomyConfigurationData configurationData = (EconomyConfigurationData) data;
        return economy.format(configurationData.getPrice());
    }

    @Override
    public void display(Player editor, AddonConfigurationData data, MenuNavigationContext navigationContext) {
        String msg = messages.get("menu.economy.edit");
        msg = msg.replace("{PRICE}", getDataValue(data));
        ClickableMessage clickableMessage = new ClickableMessage(msg);
        clickableMessage.add(CLICKABLE_CANCEL);
        clickableMessage.sendToPlayer(editor);
    }

    @Override
    public void input(Player player, AddonConfigurationData data, String message, MenuNavigationContext navigationContext) {
        try {
            if (!CANCEL_STRING.equals(message)) {
                EconomyConfigurationData configurationData = (EconomyConfigurationData) data;
                String[] args = message.split(" ");
                double value = Double.parseDouble(args[0]);
                configurationData.setPrice(value);
            }
        } catch (Exception ignored) {
        } finally {
            navigationContext.setAddonMenu(getParent());
        }
    }
}
