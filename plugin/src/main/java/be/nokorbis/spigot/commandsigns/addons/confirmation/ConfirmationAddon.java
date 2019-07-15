package be.nokorbis.spigot.commandsigns.addons.confirmation;

import be.nokorbis.spigot.commandsigns.addons.confirmation.data.ConfirmationConfigurationData;
import be.nokorbis.spigot.commandsigns.addons.confirmation.data.ConfirmationConfigurationDataPersister;
import be.nokorbis.spigot.commandsigns.addons.confirmation.data.ConfirmationExecutionData;
import be.nokorbis.spigot.commandsigns.api.addons.AddonBase;
import be.nokorbis.spigot.commandsigns.api.addons.AddonConfigurationData;
import be.nokorbis.spigot.commandsigns.api.addons.AddonExecutionData;
import be.nokorbis.spigot.commandsigns.api.addons.AddonLifecycleHooker;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import org.bukkit.plugin.Plugin;

public class ConfirmationAddon extends AddonBase {

    private static final String IDENTIFIER = "ncs_confirmation";

    private final ConfirmationLifecycleHooker lifecycleHooker = new ConfirmationLifecycleHooker(this);
    private final ConfirmationConfigurationDataPersister persister = new ConfirmationConfigurationDataPersister(this);

    public ConfirmationAddon(Plugin plugin) {
        super(plugin, IDENTIFIER, "Confirmation");
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
    public JsonSerializer<? extends AddonConfigurationData> getConfigurationDataSerializer() {
        return persister;
    }

    @Override
    public JsonDeserializer<? extends AddonConfigurationData> getConfigurationDataDeserializer() {
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
}
