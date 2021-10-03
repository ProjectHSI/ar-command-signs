package project.hsi.commandsigns.api.addons;

import org.bukkit.entity.Player;
import project.hsi.commandsigns.api.exceptions.CommandSignsRequirementException;


public interface AddonLifecycleHooker extends AddonRelated {
    void onStarted(final Player player, final AddonConfigurationData configurationData, final AddonExecutionData executionData);

    void onRequirementCheck(final Player player, final AddonConfigurationData configurationData, final AddonExecutionData executionData) throws CommandSignsRequirementException;

    void onCostWithdraw(final Player player, final AddonConfigurationData configurationData, final AddonExecutionData executionData);

    void onPreExecution(final Player player, final AddonConfigurationData configurationData, final AddonExecutionData executionData);

    void onPostExecution(final Player player, final AddonConfigurationData configurationData, final AddonExecutionData executionData);

    void onCompleted(final Player player, final AddonConfigurationData configurationData, final AddonExecutionData executionData);
}
