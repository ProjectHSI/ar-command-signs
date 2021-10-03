package project.hsi.commandsigns.addons.items;

import org.bukkit.plugin.Plugin;
import project.hsi.commandsigns.addons.items.data.ItemsConfigurationData;
import project.hsi.commandsigns.addons.items.data.ItemsConfigurationDataPersister;
import project.hsi.commandsigns.addons.items.data.ItemsCostsDataEditor;
import project.hsi.commandsigns.addons.items.data.ItemsRequirementDataEditor;
import project.hsi.commandsigns.api.addons.AddonBase;
import project.hsi.commandsigns.api.addons.AddonConfigurationDataEditor;
import project.hsi.commandsigns.api.menu.AddonSubmenuHolder;

import java.util.HashMap;
import java.util.Map;

public class ItemsAddon extends AddonBase {

    private static final String IDENTIFIER = "ncs_items";

    private final ItemsLifecycleHooker lifecycleHooker = new ItemsLifecycleHooker(this);
    private final ItemsConfigurationDataPersister persister = new ItemsConfigurationDataPersister(this);

    public ItemsAddon(Plugin plugin) {
        super(plugin, IDENTIFIER, "Items");
    }

    @Override
    public AddonSubmenuHolder getSubmenus() {
        return new AddonSubmenuHolder();
    }

    @Override
    public ItemsConfigurationData createConfigurationData() {
        return new ItemsConfigurationData(this);
    }

    @Override
    public Class<ItemsConfigurationData> getConfigurationDataClass() {
        return ItemsConfigurationData.class;
    }

    @Override
    public ItemsConfigurationDataPersister getConfigurationDataSerializer() {
        return persister;
    }

    @Override
    public ItemsConfigurationDataPersister getConfigurationDataDeserializer() {
        return persister;
    }

    @Override
    public ItemsLifecycleHooker getLifecycleHooker() {
        return lifecycleHooker;
    }

    @Override
    public Map<String, AddonConfigurationDataEditor> getDataEditors() {
        Map<String, AddonConfigurationDataEditor> editors = new HashMap<>();

        editors.put("ncs.items.requirements", new ItemsRequirementDataEditor(this));
        editors.put("ncs.items.costs", new ItemsCostsDataEditor(this));

        return editors;
    }
}
