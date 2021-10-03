package project.hsi.commandsigns.model;

import project.hsi.commandsigns.api.addons.Addon;
import project.hsi.commandsigns.api.menu.AddonEditionMenu;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class CoreAddonSubmenusHolder {

    public final Map<Addon, List<AddonEditionMenu>> requirementSubmenus = new LinkedHashMap<>();
    public final Map<Addon, List<AddonEditionMenu>> costSubmenus = new LinkedHashMap<>();
    public final Map<Addon, List<AddonEditionMenu>> executionSubmenus = new LinkedHashMap<>();

}
