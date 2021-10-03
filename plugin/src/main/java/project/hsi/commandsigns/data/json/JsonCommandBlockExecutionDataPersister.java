package project.hsi.commandsigns.data.json;

import com.google.gson.Gson;
import org.bukkit.Location;
import project.hsi.commandsigns.CommandSignsPlugin;
import project.hsi.commandsigns.api.addons.Addon;
import project.hsi.commandsigns.data.CommandBlockExecutionDataPersistor;
import project.hsi.commandsigns.model.AddonExecutionDataObject;
import project.hsi.commandsigns.model.CommandBlock;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import static java.nio.charset.StandardCharsets.UTF_8;


public class JsonCommandBlockExecutionDataPersister extends JsonCommandBlockPersister implements CommandBlockExecutionDataPersistor {

    private static final String DATA_FOLDER_NAME = "executions";

    public JsonCommandBlockExecutionDataPersister(File pluginFolder) {
        super(pluginFolder, DATA_FOLDER_NAME);
        registerPersister(Location.class, new JsonLocationPersister());
    }

    @Override
    public void setAddons(Set<Addon> addons) {
        for (Addon addon : addons) {
            registerPersister(addon.getExecutionDataClass(), addon.getExecutionDataSerializer());
        }
        registerPersister(AddonExecutionDataObject.class, new JsonExecutionDataObjectSerializer(addons));
    }

    @Override
    public void loadExecutionData(CommandBlock commandBlock) {
        final File configFile = new File(dataFolder, commandBlock.getId() + EXTENSION);
        if (!configFile.exists()) {
            return;
        }
        try (InputStream is = new FileInputStream(configFile);
             InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            Gson gson = getGson();
            AddonExecutionDataObject object = gson.fromJson(reader, AddonExecutionDataObject.class);
            commandBlock.importExecutionData(object);
        } catch (IOException e) {
            CommandSignsPlugin.getPlugin().getLogger().severe("Was not able to read a file while loading a command block : " + configFile.getName());
            CommandSignsPlugin.getPlugin().getLogger().severe(e.getMessage());
        }
    }

    @Override
    public void saveExecutionData(CommandBlock commandBlock) {
        File blockDataFile = new File(dataFolder, commandBlock.getId() + EXTENSION);

        try {
            if (!blockDataFile.exists()) {
                //noinspection ResultOfMethodCallIgnored
                blockDataFile.createNewFile();
            }

            try (OutputStream os = new FileOutputStream(blockDataFile);
                 OutputStreamWriter writer = new OutputStreamWriter(os, UTF_8)) {
                Gson gson = getGson();

                String json = gson.toJson(commandBlock.exportExecutionData());

                writer.write(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean deleteExecutionData(CommandBlock commandBlock) {
        final File configFile = new File(dataFolder, commandBlock.getId() + EXTENSION);
        if (!configFile.exists()) {
            return false;
        }
        return configFile.delete();
    }
}
