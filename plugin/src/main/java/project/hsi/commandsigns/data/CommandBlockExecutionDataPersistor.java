package project.hsi.commandsigns.data;

import project.hsi.commandsigns.api.addons.Addon;
import project.hsi.commandsigns.model.CommandBlock;

import java.util.Set;


public interface CommandBlockExecutionDataPersistor {
    void setAddons(Set<Addon> addons);

    void loadExecutionData(CommandBlock commandBlock);

    void saveExecutionData(CommandBlock commandBlock);

    boolean deleteExecutionData(CommandBlock commandBlock);
}
