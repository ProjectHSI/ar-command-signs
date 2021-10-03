package project.hsi.commandsigns.model;

import project.hsi.commandsigns.api.addons.Addon;
import project.hsi.commandsigns.api.addons.AddonExecutionData;

import java.util.HashMap;
import java.util.Map;


public class AddonExecutionDataObject {
    public long id;
    public Map<Addon, AddonExecutionData> addonExecutions = new HashMap<>();
}
