package project.hsi.commandsigns.api.menu;

import project.hsi.commandsigns.api.addons.Addon;
import project.hsi.commandsigns.api.addons.AddonConfigurationData;


public abstract class AddonEditionLeaf extends AddonEditionMenu {
    protected static final String CANCEL_STRING = "</{!(C@NCEL)!}\\>";
    protected static final ClickableMessage CLICKABLE_CANCEL = new ClickableMessage(messages.get("menu.entry.click_to_cancel"), CANCEL_STRING);

    public AddonEditionLeaf(final Addon addon, final String name, final AddonEditionMenu parent) {
        super(addon, name, parent);
    }

    @Override
    public String getDisplayString(AddonConfigurationData data) {
        return messages.get("menu.entry.display")
                .replace("{NAME}", name)
                .replace("{VALUE}", getDataValue(data));
    }

    public abstract String getDataValue(AddonConfigurationData data);
}
