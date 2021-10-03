package project.hsi.commandsigns.addons.items;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import project.hsi.commandsigns.addons.items.data.ItemsConfigurationData;
import project.hsi.commandsigns.addons.items.data.NCSItem;
import project.hsi.commandsigns.api.addons.AddonConfigurationData;
import project.hsi.commandsigns.api.addons.AddonExecutionData;
import project.hsi.commandsigns.api.addons.AddonLifecycleHookerBase;
import project.hsi.commandsigns.api.addons.NCSLifecycleHook;
import project.hsi.commandsigns.api.exceptions.CommandSignsRequirementException;

import java.util.List;
import java.util.stream.Collectors;

public class ItemsLifecycleHooker extends AddonLifecycleHookerBase {

    public ItemsLifecycleHooker(ItemsAddon addon) {
        super(addon);
    }

    @Override
    @NCSLifecycleHook
    public void onRequirementCheck(Player player, AddonConfigurationData configurationData, AddonExecutionData executionData) throws CommandSignsRequirementException {
        ItemsConfigurationData data = (ItemsConfigurationData) configurationData;

        if (!data.getRequirementNCSItems().isEmpty()) {
            for (NCSItem item : data.getRequirementNCSItems()) {
                if (isPlayerMeetingItemRequirementInvert(player, item)) {
                    throw new CommandSignsRequirementException(getMessage(item));
                }
            }
        }

        if (!data.getCostsNCSItems().isEmpty()) {
            for (NCSItem item : data.getCostsNCSItems()) {
                if (isPlayerMeetingItemRequirementInvert(player, item)) {
                    throw new CommandSignsRequirementException(getMessage(item));
                }
            }
        }
    }
    @SuppressWarnings("AlwaysInverted")
    private boolean isPlayerMeetingItemRequirementInvert(Player player, NCSItem item) {
        PlayerInventory inventory = player.getInventory();
        ItemStack itemInHand = inventory.getItemInMainHand();
        if (areItemsMatching(item, itemInHand)) {
            return false;
        }

        itemInHand = inventory.getItemInOffHand();
        if (areItemsMatching(item, itemInHand)) {
            return false;
        }

        if (!item.isHandOnly()) {
            ItemStack[] contents = inventory.getContents();

            for (ItemStack content : contents) {
                if (areItemsMatching(item, content)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean areItemsMatching(NCSItem requiredItem, ItemStack item) {
        if (item.getType() == requiredItem.getType()) {
            if (item.getAmount() >= requiredItem.getQuantity()) {
                if (requiredItem.getName() == null && requiredItem.getLore().isEmpty()) {
                    return true;
                }

                ItemMeta itemMeta = item.getItemMeta();
                if (itemMeta == null) {
                    return false;
                }

                if (requiredItem.getName() != null) {
                    String displayName = itemMeta.getDisplayName();
                    if (!displayName.contains(requiredItem.getName())) {
                        return false;
                    }
                }

                if (!requiredItem.getLore().isEmpty()) {
                    List<String> lores = itemMeta.getLore();
                    if (lores == null) {
                        return false;
                    }

                    for (String requiredLore : requiredItem.getLore()) {
                        if (!hasRequiredLore(lores, requiredLore)) {
                            return false;
                        }
                    }
                }

                return true;
            }
        }

        return false;
    }

    private boolean hasRequiredLore(List<String> lores, String requiredLore) {
        for (String lore : lores) {
            if (lore.contains(requiredLore)) {
                return true;
            }
        }
        return false;
    }

    @Override
    @NCSLifecycleHook
    public void onCostWithdraw(Player player, AddonConfigurationData configurationData, AddonExecutionData executionData) {
    }

    private String getMessage(NCSItem item) {
        String msg = messages.get("usage.items.required");
        msg = msg.replace("{QUANTITY}", Integer.toString(item.getQuantity()));
        msg = msg.replace("{TYPE}", item.getType().name());

        if (item.getName() == null) {
            msg = msg.replace("{NAME}", "");
        } else {
            String format = messages.get("usage.items.name_format");
            format = format.replace("{NAME}", item.getName());
            msg = msg.replace("{NAME}", format);
        }

        if (item.getLore().isEmpty()) {
            msg = msg.replace("{LORES}", "");
        } else {
            String lores = item.getLore().stream().map(l -> "\"" + l + "\"").collect(Collectors.joining(","));
            String format = messages.get("usage.items.lores_format");
            format = format.replace("{LORES}", lores);
            msg = msg.replace("{LORES}", format);
        }

        return msg;
    }
}
