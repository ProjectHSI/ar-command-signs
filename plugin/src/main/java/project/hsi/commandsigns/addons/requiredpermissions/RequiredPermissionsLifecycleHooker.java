package project.hsi.commandsigns.addons.requiredpermissions;

import org.bukkit.entity.Player;
import project.hsi.commandsigns.addons.requiredpermissions.data.RequiredPermissionsConfigurationData;
import project.hsi.commandsigns.api.addons.AddonConfigurationData;
import project.hsi.commandsigns.api.addons.AddonExecutionData;
import project.hsi.commandsigns.api.addons.AddonLifecycleHookerBase;
import project.hsi.commandsigns.api.addons.NCSLifecycleHook;
import project.hsi.commandsigns.api.exceptions.CommandSignsRequirementException;

import java.util.List;


public class RequiredPermissionsLifecycleHooker extends AddonLifecycleHookerBase {

    public RequiredPermissionsLifecycleHooker(RequiredPermissionsAddon addon) {
        super(addon);
    }

    @Override
    @NCSLifecycleHook
    public void onRequirementCheck(final Player player, final AddonConfigurationData configurationData, final AddonExecutionData executionData) throws CommandSignsRequirementException {
        if (player != null) {
            final RequiredPermissionsConfigurationData configuration = (RequiredPermissionsConfigurationData) configurationData;
            final List<String> requiredPermissions = configuration.getRequiredPermissions();

            for (String permission : requiredPermissions) {
                if (!player.hasPermission(permission)) {
                    String err = messages.get("usage.miss_required_permission");
                    err = err.replace("{NEEDED_PERM}", permission);
                    throw new CommandSignsRequirementException(err);
                }
            }
        }
    }

}
