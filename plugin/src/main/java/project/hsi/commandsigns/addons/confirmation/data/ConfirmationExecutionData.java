package project.hsi.commandsigns.addons.confirmation.data;

import project.hsi.commandsigns.api.addons.Addon;
import project.hsi.commandsigns.api.addons.AddonExecutionData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ConfirmationExecutionData extends AddonExecutionData {

    private Map<UUID, Long> warnedPlayers = new HashMap<>();

    public ConfirmationExecutionData(Addon addon) {
        super(addon);
    }

    @Override
    public AddonExecutionData copy() {
        ConfirmationExecutionData data = new ConfirmationExecutionData(addon);
        data.warnedPlayers = new HashMap<>(warnedPlayers);
        return data;
    }

    public Map<UUID, Long> getWarnedPlayers() {
        return warnedPlayers;
    }
}
