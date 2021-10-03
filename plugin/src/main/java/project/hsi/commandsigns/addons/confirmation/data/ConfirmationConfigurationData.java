package project.hsi.commandsigns.addons.confirmation.data;

import org.bukkit.entity.Player;
import project.hsi.commandsigns.api.addons.Addon;
import project.hsi.commandsigns.api.addons.AddonConfigurationData;

public class ConfirmationConfigurationData extends AddonConfigurationData {

    private boolean requireConfirmation = false;

    public ConfirmationConfigurationData(Addon addon) {
        super(addon);
    }

    public boolean isRequireConfirmation() {
        return requireConfirmation;
    }

    public void setRequireConfirmation(boolean requireConfirmation) {
        this.requireConfirmation = requireConfirmation;
    }

    public void parseRequireConfirmation(String value) {
        value = value.toUpperCase();
        this.setRequireConfirmation("Y".equals(value) || "YES".equals(value) || "TRUE".equals(value));
    }

    @Override
    public AddonConfigurationData copy() {
        ConfirmationConfigurationData copy = new ConfirmationConfigurationData(addon);
        copy.requireConfirmation = this.requireConfirmation;
        return copy;
    }

    @Override
    public void info(Player player) {
        if (requireConfirmation) {
            player.sendMessage(addonMessages.get("info.require_confirmation"));
        }
        super.info(player);
    }
}
