package project.hsi.commandsigns.addons.cooldowns.data;

import org.bukkit.entity.Player;
import project.hsi.commandsigns.api.addons.Addon;
import project.hsi.commandsigns.api.addons.AddonExecutionData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class CooldownExecutionData extends AddonExecutionData {

    private Map<UUID, Long> playerUsages = new HashMap<>();
    private Long lastTimeUsed = null;

    public CooldownExecutionData(final Addon addon) {
        super(addon);
    }

    public Long getLastTimeUsed() {
        return lastTimeUsed;
    }

    public Long getLastPlayerUsage(final Player player) {
        return playerUsages.get(player.getUniqueId());
    }

    public void addPlayerUsage(final UUID uuid, final long time) {
        this.playerUsages.put(uuid, time);
        if (lastTimeUsed == null || time > lastTimeUsed) {
            this.lastTimeUsed = time;
        }
    }

    public void addPlayerUsage(final Player player, final long time) {
        addPlayerUsage(player.getUniqueId(), time);
    }

    public void addPlayerUsage(final Player player) {
        addPlayerUsage(player, System.currentTimeMillis());
    }

    Map<UUID, Long> getPlayerUsages() {
        return this.playerUsages;
    }

    @Override
    public AddonExecutionData copy() {
        CooldownExecutionData executionData = new CooldownExecutionData(addon);

        executionData.lastTimeUsed = this.lastTimeUsed;
        executionData.playerUsages = new HashMap<>(this.playerUsages);

        return executionData;
    }
}
