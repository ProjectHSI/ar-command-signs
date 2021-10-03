package project.hsi.commandsigns.api.addons;

import org.bukkit.entity.Player;
import project.hsi.commandsigns.api.DisplayMessages;
import project.hsi.commandsigns.api.exceptions.CommandSignsRequirementException;


public class AddonLifecycleHookerBase implements AddonLifecycleHooker {

    protected static final DisplayMessages messages = DisplayMessages.getDisplayMessages("messages/addons");

    protected Addon addon;

    public AddonLifecycleHookerBase(Addon addon) {
        this.addon = addon;
    }

    @Override
    public Addon getAddon() {
        return addon;
    }

    @Override
    public void onStarted(Player player, AddonConfigurationData configurationData, AddonExecutionData executionData) {

    }

    @Override
    public void onRequirementCheck(Player player, AddonConfigurationData configurationData, AddonExecutionData executionData) throws CommandSignsRequirementException {

    }

    @Override
    public void onCostWithdraw(Player player, AddonConfigurationData configurationData, AddonExecutionData executionData) {

    }

    @Override
    public void onPreExecution(Player player, AddonConfigurationData configurationData, AddonExecutionData executionData) {

    }

    @Override
    public void onPostExecution(Player player, AddonConfigurationData configurationData, AddonExecutionData executionData) {

    }

    @Override
    public void onCompleted(Player player, AddonConfigurationData configurationData, AddonExecutionData executionData) {

    }
}
