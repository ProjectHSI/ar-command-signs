package project.hsi.commandsigns.addons.economy.data;

import org.bukkit.entity.Player;
import project.hsi.commandsigns.api.addons.Addon;
import project.hsi.commandsigns.api.addons.AddonConfigurationData;


public class EconomyConfigurationData extends AddonConfigurationData {

    private double price;

    public EconomyConfigurationData(Addon addon) {
        super(addon);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public AddonConfigurationData copy() {
        EconomyConfigurationData data = new EconomyConfigurationData(addon);
        data.price = this.price;
        return data;
    }

    @Override
    public void info(Player player) {
        if (this.price != 0) {
            player.sendMessage(addonMessages.get("info.costs_format").replace("{PRICE}", String.valueOf(price)));
        }
    }
}
