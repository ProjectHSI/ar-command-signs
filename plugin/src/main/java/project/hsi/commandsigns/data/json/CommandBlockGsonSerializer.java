package project.hsi.commandsigns.data.json;

import com.google.gson.*;
import org.bukkit.Location;
import project.hsi.commandsigns.api.addons.Addon;
import project.hsi.commandsigns.api.addons.AddonConfigurationData;
import project.hsi.commandsigns.model.BlockActivationMode;
import project.hsi.commandsigns.model.CommandBlock;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Nokorbis on 22/01/2016.
 */
public class CommandBlockGsonSerializer implements JsonSerializer<CommandBlock>, JsonDeserializer<CommandBlock> {

    private final Set<Addon> addons;

    public CommandBlockGsonSerializer(Set<Addon> addons) {
        this.addons = addons;
    }

    @Override
    public CommandBlock deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonContext) throws JsonParseException {
        JsonObject root = jsonElement.getAsJsonObject();

        try {
            long id = root.get("id").getAsLong();
            CommandBlock cmdBlock = new CommandBlock(id);
            JsonElement name = root.get("name");
            if (name != null) {
                cmdBlock.setName(name.getAsString());
            }

            Location loc = jsonContext.deserialize(root.get("location"), Location.class);
            if (loc != null) {
                cmdBlock.setLocation(loc);
            } else {
                return null;
            }

            JsonElement disabled = root.get("disabled");
            if (disabled != null) {
                cmdBlock.setDisabled(disabled.getAsBoolean());
            }

            JsonElement activationMode = root.get("activation_mode");
            if (activationMode != null) {
                String mode = activationMode.getAsString();
                cmdBlock.setActivationMode(BlockActivationMode.fromName(mode));
            }

            deserializeTimer(cmdBlock, root);
            deserializeStringArray(cmdBlock.getCommands(), root, "commands");
            deserializeStringArray(cmdBlock.getTemporarilyGrantedPermissions(), root, "temporarily_granted_permissions");

            JsonObject jsonAddons = root.getAsJsonObject("addons");
            for (Addon addon : addons) {
                JsonElement addonData = jsonAddons.get(addon.getIdentifier());
                if (addonData != null && !addonData.isJsonNull()) {
                    AddonConfigurationData parsedAddonData = jsonContext.deserialize(addonData, addon.getConfigurationDataClass());
                    cmdBlock.setAddonConfigurationData(addon, parsedAddonData);
                }
            }

            return cmdBlock;
        } catch (Exception ex) {
            return null;
        }
    }

    private void deserializeStringArray(ArrayList<String> stringArray, JsonObject root, String jsonKey) {
        JsonArray commands = root.getAsJsonArray(jsonKey);
        for (JsonElement element : commands) {
            stringArray.add(element.getAsString());
        }
    }

    private void deserializeTimer(CommandBlock cmdBlock, JsonObject root) {
        JsonObject timer = root.getAsJsonObject("timer");
        int duration = timer.getAsJsonPrimitive("duration").getAsInt();
        boolean cancelled = timer.getAsJsonPrimitive("cancelled_on_move").getAsBoolean();
        boolean reset = timer.getAsJsonPrimitive("reset_on_move").getAsBoolean();
        cmdBlock.setTimeBeforeExecution(duration);
        cmdBlock.setCancelledOnMove(cancelled);
        cmdBlock.setResetOnMove(reset);
    }

    @Override
    public JsonElement serialize(CommandBlock commandBlock, Type type, final JsonSerializationContext jsonContext) {
        JsonObject root = new JsonObject();

        root.addProperty("id", commandBlock.getId());
        if (commandBlock.getName() != null) {
            root.addProperty("name", commandBlock.getName());
        }
        root.addProperty("disabled", commandBlock.isDisabled());
        root.addProperty("activation_mode", commandBlock.getActivationMode().name());

        Location location = commandBlock.getLocation();
        if (location != null) {
            root.add("location", jsonContext.serialize(location));
        }

        root.add("timer", serializeTimer(commandBlock));
        root.add("commands", serializeStringArray(commandBlock.getCommands()));
        root.add("temporarily_granted_permissions", serializeStringArray(commandBlock.getTemporarilyGrantedPermissions()));

        final JsonObject addonData = new JsonObject();
        for (Addon addon : addons) {
            if (addon.getConfigurationDataClass() != null && addon.getConfigurationDataSerializer() != null && addon.getConfigurationDataDeserializer() != null) {
                AddonConfigurationData addonConfigurationData = commandBlock.getAddonConfigurationData(addon);
                addonData.add(addon.getIdentifier(), jsonContext.serialize(addonConfigurationData));
            }
        }

        root.add("addons", addonData);

        return root;
    }

    private JsonArray serializeStringArray(ArrayList<String> temporarilyGrantedPermissions) {
        JsonArray permissions = new JsonArray();
        for (String permission : temporarilyGrantedPermissions) {
            permissions.add(permission);
        }
        return permissions;
    }

    private JsonObject serializeTimer(CommandBlock commandBlock) {
        JsonObject timer = new JsonObject();
        timer.addProperty("duration", commandBlock.getTimeBeforeExecution());
        timer.addProperty("cancelled_on_move", commandBlock.isCancelledOnMove());
        timer.addProperty("reset_on_move", commandBlock.isResetOnMove());
        return timer;
    }
}
