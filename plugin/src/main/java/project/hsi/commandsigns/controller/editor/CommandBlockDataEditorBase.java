package project.hsi.commandsigns.controller.editor;

import project.hsi.commandsigns.api.DisplayMessages;
import project.hsi.commandsigns.model.CommandBlock;

import java.util.Collections;
import java.util.List;

public abstract class CommandBlockDataEditorBase implements CommandBlockDataEditor {

    protected final static DisplayMessages messages = DisplayMessages.getDisplayMessages("messages/commands");

    protected final boolean parseBooleanValue(String value) {
        value = value.toUpperCase();
        return "Y".equals(value) || "YES".equals(value) || "TRUE".equals(value);
    }

    @Override
    public List<String> onTabComplete(CommandBlock data, List<String> args) {
        return Collections.emptyList();
    }
}
