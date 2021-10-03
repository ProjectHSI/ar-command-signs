package project.hsi.commandsigns.data;

import project.hsi.commandsigns.api.addons.Addon;
import project.hsi.commandsigns.model.CommandBlock;

import java.util.List;
import java.util.Set;


public interface CommandBlockConfigurationDataPersister {

    void setAddons(Set<Addon> addons);

    boolean saveConfiguration(CommandBlock commandBlock);

    CommandBlock load(long id);

    boolean delete(long id);

    List<CommandBlock> loadAllConfigurations();

}
