package project.hsi.commandsigns.addons.requiredpermissions;

import project.hsi.commandsigns.CommandSignsPlugin;
import project.hsi.commandsigns.addons.requiredpermissions.data.RequiredPermissionsConfigurationData;
import project.hsi.commandsigns.addons.requiredpermissions.data.RequiredPermissionsConfigurationDataPersister;
import project.hsi.commandsigns.addons.requiredpermissions.data.RequiredPermissionsDataEditor;
import project.hsi.commandsigns.addons.requiredpermissions.menus.MenuRequiredPermissions;
import project.hsi.commandsigns.api.addons.AddonBase;
import project.hsi.commandsigns.api.addons.AddonConfigurationData;
import project.hsi.commandsigns.api.addons.AddonConfigurationDataEditor;
import project.hsi.commandsigns.api.addons.AddonLifecycleHooker;
import project.hsi.commandsigns.api.menu.AddonSubmenuHolder;

import java.util.HashMap;
import java.util.Map;


public class RequiredPermissionsAddon extends AddonBase {

    private static final String IDENTIFIER = "ncs_required_permissions";

    private final RequiredPermissionsLifecycleHooker lifecycleHooker = new RequiredPermissionsLifecycleHooker(this);
    private final MenuRequiredPermissions editionMenu = new MenuRequiredPermissions(this);

    private final RequiredPermissionsConfigurationDataPersister configurationDataTransformer = new RequiredPermissionsConfigurationDataPersister(this);

    public RequiredPermissionsAddon(CommandSignsPlugin plugin) {
        super(plugin, IDENTIFIER, "Required permissions");
    }

    @Override
    public AddonLifecycleHooker getLifecycleHooker() {
        return lifecycleHooker;
    }

    @Override
    public AddonSubmenuHolder getSubmenus() {
        AddonSubmenuHolder holder = new AddonSubmenuHolder();
        holder.requirementSubmenus.add(editionMenu);
        return holder;
    }

    @Override
    public RequiredPermissionsConfigurationData createConfigurationData() {
        return new RequiredPermissionsConfigurationData(this);
    }

    @Override
    public Class<? extends AddonConfigurationData> getConfigurationDataClass() {
        return RequiredPermissionsConfigurationData.class;
    }

    @Override
    public RequiredPermissionsConfigurationDataPersister getConfigurationDataSerializer() {
        return configurationDataTransformer;
    }

    @Override
    public RequiredPermissionsConfigurationDataPersister getConfigurationDataDeserializer() {
        return configurationDataTransformer;
    }

    @Override
    public Map<String, AddonConfigurationDataEditor> getDataEditors() {
        Map<String, AddonConfigurationDataEditor> editors = new HashMap<>(1);
        editors.put("ncs.required_permissions", new RequiredPermissionsDataEditor(this));
        return editors;
    }
}
