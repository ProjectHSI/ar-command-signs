package project.hsi.commandsigns.api.menu;

import project.hsi.commandsigns.api.addons.Addon;
import project.hsi.commandsigns.api.addons.AddonConfigurationData;
import project.hsi.commandsigns.api.addons.AddonRelated;


public abstract class AddonEditionMenu extends EditionMenu<AddonConfigurationData> implements AddonRelated {

    protected final Addon addon;
    protected final AddonEditionMenu parent;

    public AddonEditionMenu(Addon addon, String name, AddonEditionMenu parent) {
        super(name, parent);
        this.addon = addon;
        this.parent = parent;
    }

    @Override
    public Addon getAddon() {
        return addon;
    }

    @Override
    public AddonEditionMenu getParent() {
        return parent;
    }
}
