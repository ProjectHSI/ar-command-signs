package project.hsi.commandsigns.addons.cooldowns;

import org.bukkit.entity.Player;
import project.hsi.commandsigns.addons.cooldowns.data.CooldownConfigurationData;
import project.hsi.commandsigns.addons.cooldowns.data.CooldownExecutionData;
import project.hsi.commandsigns.api.addons.AddonConfigurationData;
import project.hsi.commandsigns.api.addons.AddonExecutionData;
import project.hsi.commandsigns.api.addons.AddonLifecycleHookerBase;
import project.hsi.commandsigns.api.addons.NCSLifecycleHook;
import project.hsi.commandsigns.api.exceptions.CommandSignsRequirementException;
import project.hsi.commandsigns.utils.CommandSignUtils;


public class CooldownLifecycleHooker extends AddonLifecycleHookerBase {


    public CooldownLifecycleHooker(CooldownAddon addon) {
        super(addon);
    }

    @Override
    @NCSLifecycleHook
    public final void onRequirementCheck(final Player player, final AddonConfigurationData configurationData, final AddonExecutionData executionData) throws CommandSignsRequirementException {
        if (player != null) {
            final CooldownConfigurationData conf = (CooldownConfigurationData) configurationData;

            if (conf.hasGlobalCooldown() || conf.hasPlayerCooldown() || conf.isGlobalOnlyOnce() || conf.isPlayerOnlyOnce()) {

                final CooldownExecutionData data = (CooldownExecutionData) executionData;
                final Long lastTimeSomeoneUsed = data.getLastTimeUsed();
                final Long lastTimePlayerUsed = data.getLastPlayerUsage(player);

                if (!player.hasPermission("commandsign.onetime_limit.bypass")) {
                    checkIsGlobalOnlyOnce(lastTimeSomeoneUsed, conf.isGlobalOnlyOnce());
                    checkIsPlayerOnlyOnce(lastTimePlayerUsed, conf.isPlayerOnlyOnce());
                }
                if (!player.hasPermission("commandsign.timer.bypass")) {
                    checkPlayerCooldown(lastTimePlayerUsed, conf.getPlayerCooldown() * 1000);
                    checkGlobalCooldown(lastTimeSomeoneUsed, conf.getGlobalCooldown() * 1000);
                }
            }
        }
    }

    private void checkIsGlobalOnlyOnce(final Long lastTimeSomeoneUsed, final boolean isGlobalOnlyOnce) throws CommandSignsRequirementException {
        if (isGlobalOnlyOnce) {
            if (lastTimeSomeoneUsed != null) {
                throw new CommandSignsRequirementException(messages.get("usage.global_once_already_used"));
            }
        }
    }

    private void checkIsPlayerOnlyOnce(final Long lastTimePlayerUsed, final boolean isPlayerOnlyOnce) throws CommandSignsRequirementException {
        if (isPlayerOnlyOnce) {
            if (lastTimePlayerUsed != null) {
                throw new CommandSignsRequirementException(messages.get("usage.player_once_already_used"));
            }
        }
    }

    private void checkGlobalCooldown(final Long lastTimeSomeoneUsed, final long globalCooldown) throws CommandSignsRequirementException {
        if (lastTimeSomeoneUsed != null) {
            final long now = System.currentTimeMillis();
            long timeToWait = lastTimeSomeoneUsed + globalCooldown - now;
            if (timeToWait > 0) {
                String msg = messages.get("usage.general_cooldown");
                String time = CommandSignUtils.formatTime((globalCooldown - timeToWait) / 1000.0);
                String remaining = CommandSignUtils.formatTime((timeToWait) / 1000.0);
                msg = msg.replace("{TIME}", time);
                msg = msg.replace("{REMAINING}", remaining);
                throw new CommandSignsRequirementException(msg);
            }
        }
    }

    private void checkPlayerCooldown(final Long lastTimePlayerUsed, final long playerCooldown) throws CommandSignsRequirementException {
        if (lastTimePlayerUsed != null) {
            final long now = System.currentTimeMillis();
            long timeToWait = lastTimePlayerUsed + playerCooldown - now;
            if (timeToWait > 0) {
                String msg = messages.get("usage.player_cooldown");
                String time = CommandSignUtils.formatTime((now - lastTimePlayerUsed) / 1000.0);
                String remaining = CommandSignUtils.formatTime((timeToWait) / 1000.0);
                msg = msg.replace("{TIME}", time);
                msg = msg.replace("{REMAINING}", remaining);
                throw new CommandSignsRequirementException(msg);
            }
        }
    }

    @Override
    @NCSLifecycleHook
    public final void onPostExecution(final Player player, final AddonConfigurationData configurationData, final AddonExecutionData executionData) {
        final CooldownExecutionData data = (CooldownExecutionData) executionData;
        data.addPlayerUsage(player);
    }
}
