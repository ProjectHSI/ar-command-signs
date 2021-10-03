package project.hsi.commandsigns.data.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import project.hsi.commandsigns.CommandSignsPlugin;
import project.hsi.commandsigns.data.CommandBlockIDLoader;
import project.hsi.commandsigns.utils.CommandBlockValidator;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;


public class JsonCommandBlockIDLoader extends JsonCommandBlockPersister implements CommandBlockIDLoader {

    private static final String DATA_FOLDER_NAME = "configurations";

    private final Logger logger;
    private List<File> cachedFolders = null;

    public JsonCommandBlockIDLoader(CommandSignsPlugin plugin) {
        super(plugin.getDataFolder(), DATA_FOLDER_NAME);

        logger = plugin.getLogger();
    }

    public long getLastID() {
        long maxID = 0L;
        if (!dataFolder.exists()) {
            //noinspection ResultOfMethodCallIgnored
            dataFolder.mkdirs();
            return maxID;
        }

        File[] files = dataFolder.listFiles(filter);
        if (files != null && files.length > 0) {
            for (File file : files) {
                String fileName = file.getName();
                String name = fileName.substring(0, fileName.indexOf('.'));
                long id = Long.parseLong(name);
                if (id > maxID) {
                    maxID = id;
                }
            }
        }

        return maxID;
    }

    @Override
    public Map<Location, Long> loadAllIdsPerLocations() {
        if (!dataFolder.exists()) {
            //noinspection ResultOfMethodCallIgnored
            dataFolder.mkdirs();
            return Collections.emptyMap();
        }

        File[] files = dataFolder.listFiles(filter);
        if (files == null || files.length < 1) {
            return Collections.emptyMap();
        }

        return parseFilesForLocations(files);
    }

    private Map<Location, Long> parseFilesForLocations(File[] files) {
        Map<Location, Long> idsPerLocations = new HashMap<>();

        UnusedIDs unusedIDs = new UnusedIDs(logger);

        JsonParser parser = new JsonParser();
        for (File file : files) {
            try (InputStream fis = new FileInputStream(file);
                 InputStreamReader reader = new InputStreamReader(fis, UTF_8)) {
                JsonElement tree = parser.parse(reader);
                JsonObject root = tree.getAsJsonObject();

                JsonPrimitive jsonID = root.getAsJsonPrimitive("id");
                final long id = jsonID.getAsLong();

                JsonObject jsonLocation = root.getAsJsonObject("location");
                String worldName = jsonLocation.getAsJsonPrimitive("world").getAsString();
                final World world = getWorldFromString(worldName);
                if (world == null) {
                    if (isNameOfUnloadedWorld(worldName)) {
                        unusedIDs.idsForWorldNotLoaded.add(id);
                    } else {
                        unusedIDs.idsForWorldsNotFound.add(id);
                    }
                    continue;
                }

                Location location = extractLocation(world, jsonLocation);
                if (!CommandBlockValidator.isValidBlock(location.getBlock())) {
                    unusedIDs.idsForNotValidLocation.add(id);
                    continue;
                }

                idsPerLocations.put(location, id);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        unusedIDs.displayWarnings();

        return idsPerLocations;
    }

    private Location extractLocation(World world, JsonObject jsonLocation) {
        double x = jsonLocation.getAsJsonPrimitive("x").getAsDouble();
        double y = jsonLocation.getAsJsonPrimitive("y").getAsDouble();
        double z = jsonLocation.getAsJsonPrimitive("z").getAsDouble();

        return new Location(world, x, y, z);
    }

    private boolean isNameOfUnloadedWorld(final String worldName) {
        List<File> worldFolders = getWorldFolders();

        for (File worldFolder : worldFolders) {
            if (worldFolder.getName().equals(worldName)) {
                return true;
            }
        }

        return false;
    }

    private List<File> getWorldFolders() {
        if (cachedFolders == null) {
            File workingDir = new File(".");
            if (workingDir.isDirectory()) {
                File[] files = workingDir.listFiles(new WorldFolderFilter());
                cachedFolders = Arrays.asList(Objects.requireNonNull(files));
            }
        }
        return cachedFolders;
    }

    private World getWorldFromString(final String worldName) {
        try {
            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                final UUID worldUuid = UUID.fromString(worldName);
                world = Bukkit.getWorld(worldUuid);
            }
            return world;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static class UnusedIDs {
        private final Logger logger;

        private final List<Long> idsForWorldsNotFound = new ArrayList<>();
        private final List<Long> idsForWorldNotLoaded = new ArrayList<>();
        private final List<Long> idsForNotValidLocation = new ArrayList<>();

        UnusedIDs(Logger logger) {
            this.logger = logger;
        }

        void displayWarnings() {
            displayConfigurationsWarning("Some command signs were not loaded because their location is not valid: %s", idsForNotValidLocation);
            displayConfigurationsWarning("Some command signs were not loaded because their world is not loaded: %s", idsForWorldNotLoaded);
            displayConfigurationsWarning("Some command signs were not loaded because their world could not be found: %s", idsForWorldsNotFound);
        }

        private void displayConfigurationsWarning(String warningMessage, List<Long> ids) {
            if (!ids.isEmpty()) {
                String parameter = ids.stream().sorted().map(id -> Long.toString(id))
                        .collect(Collectors.joining(","));
                logger.warning(String.format(warningMessage, parameter));
            }
        }
    }

    private static class WorldFolderFilter implements FileFilter {

        @Override
        public boolean accept(File file) {
            if (!file.isDirectory()) {
                return false;
            }
            if ("logs".equals(file.getName())) {
                return false;
            }
            return !"plugins".equals(file.getName());
        }
    }

}
