package project.hsi.commandsigns.api.menu;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;


public class ClickableMessage {

    private final TextComponent textComponent;

    public ClickableMessage(String message, String command) {
        textComponent = asTextComponent(message, command);
    }

    public ClickableMessage(String message) {
        this(message, null);
    }

    public static TextComponent asTextComponent(String message, String command) {
        BaseComponent[] baseComponents = TextComponent.fromLegacyText(message);
        TextComponent component = new TextComponent(baseComponents);
        if (command != null) {
            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        }
        return component;
    }

    public static TextComponent asTextComponent(String message) {
        return asTextComponent(message, null);
    }

    public void sendToPlayer(Player player) {
        if (player != null && player.isOnline()) {
            player.spigot().sendMessage(textComponent);
        }
    }

    public void add(ClickableMessage clickableMessage) {
        textComponent.addExtra(clickableMessage.textComponent);
    }

    public TextComponent asTextComponent() {
        return textComponent;
    }
}
