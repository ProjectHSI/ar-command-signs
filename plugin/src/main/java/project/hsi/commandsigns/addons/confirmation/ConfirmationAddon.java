package project.hsi.commandsigns.addons.confirmation;

import org.bukkit.plugin.Plugin;
import project.hsi.commandsigns.addons.confirmation.data.ConfimationDataEditor;
import project.hsi.commandsigns.addons.confirmation.data.ConfirmationConfigurationData;
import project.hsi.commandsigns.addons.confirmation.data.ConfirmationConfigurationDataPersister;
import project.hsi.commandsigns.addons.confirmation.data.ConfirmationExecutionData;
import project.hsi.commandsigns.addons.confirmation.menus.MenuConfirmation;
import project.hsi.commandsigns.api.addons.AddonBase;
import project.hsi.commandsigns.api.addons.AddonConfigurationData;
import project.hsi.commandsigns.api.addons.AddonConfigurationDataEditor;
import project.hsi.commandsigns.api.addons.AddonLifecycleHooker;
import project.hsi.commandsigns.api.menu.AddonSubmenuHolder;

import java.util.HashMap;
import java.util.Map;

public class ConfirmationAddon extends AddonBase {

    private static final String IDENTIFIER = "ncs_confirmation";

    private final ConfirmationLifecycleHooker lifecycleHooker = new ConfirmationLifecycleHooker(this);
    private final ConfirmationConfigurationDataPersister persister = new ConfirmationConfigurationDataPersister(this);

    private final MenuConfirmation menu = new MenuConfirmation(this);

    public ConfirmationAddon(Plugin plugin) {
        super(plugin, IDENTIFIER, "Confirmation");
    }

    @Override
    public AddonSubmenuHolder getSubmenus() {
        AddonSubmenuHolder holder = new AddonSubmenuHolder();
        holder.requirementSubmenus.add(menu);
        return holder;
    }

    @Override
    public AddonLifecycleHooker getLifecycleHooker() {
        return lifecycleHooker;
    }

    @Override
    public ConfirmationConfigurationData createConfigurationData() {
        return new ConfirmationConfigurationData(this);
    }

    @Override
    public Class<? extends AddonConfigurationData> getConfigurationDataClass() {
        return ConfirmationConfigurationData.class;
    }

    @Override
    public ConfirmationConfigurationDataPersister getConfigurationDataSerializer() {
        return persister;
    }

    @Override
    public ConfirmationConfigurationDataPersister getConfigurationDataDeserializer() {
        return persister;
    }

    @Override
    public ConfirmationExecutionData createExecutionData() {
        return new ConfirmationExecutionData(this);
    }

    @Override
    public Class<ConfirmationExecutionData> getExecutionDataClass() {
        return ConfirmationExecutionData.class;
    }

    @Override
    public Map<String, AddonConfigurationDataEditor> getDataEditors() {
        HashMap<String, AddonConfigurationDataEditor> editors = new HashMap<>(1);
        editors.put("ncs.confirmation", new ConfimationDataEditor(this));
        return editors;
    }
}
