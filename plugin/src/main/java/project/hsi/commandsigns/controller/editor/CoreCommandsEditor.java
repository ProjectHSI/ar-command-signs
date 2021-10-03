package project.hsi.commandsigns.controller.editor;

import org.bukkit.command.CommandSender;
import project.hsi.commandsigns.model.CommandBlock;
import project.hsi.commandsigns.model.CommandSignsCommandException;
import project.hsi.commandsigns.utils.Settings;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class CoreCommandsEditor extends CommandBlockDataEditorBase {

    private static final List<String> SUB_COMMANDS = Arrays.asList("add", "edit", "remove");

    @Override
    public void editValue(CommandBlock data, List<String> args, CommandSender sender) throws CommandSignsCommandException {
        if (args.size() < 2) {
            throw new CommandSignsCommandException(messages.get("error.command.more_args"));
        }

        try {
            List<String> commands = data.getCommands();
            String command = args.remove(0);
            if ("add".equals(command)) {
                String toAdd = String.join(" ", args);
                if (toAdd.startsWith(Character.toString(Settings.SERVER_CHAR())) || toAdd.startsWith(Character.toString(Settings.OP_CHAR()))) {
                    if (sender.hasPermission("commandsign.admin.set.super")) {
                        commands.add(toAdd);
                    } else {
                        throw new CommandSignsCommandException(messages.get("error.no_super_permission"));
                    }
                } else {
                    commands.add(toAdd);
                }
            } else {
                int index = Integer.parseUnsignedInt(args.remove(0));
                if (index > commands.size()) {
                    throw new CommandSignsCommandException(messages.get("error.command.index_too_large"));
                }

                if ("remove".equals(command)) {
                    commands.remove(index - 1);
                } else if ("edit".equals(command)) {
                    if (args.isEmpty()) {
                        throw new CommandSignsCommandException(messages.get("error.command.more_args"));
                    }

                    String toChange = String.join(" ", args);
                    if (toChange.startsWith(Character.toString(Settings.SERVER_CHAR())) || toChange.startsWith(Character.toString(Settings.OP_CHAR()))) {
                        if (sender.hasPermission("commandsign.admin.set.super")) {
                            commands.set(index - 1, toChange);
                        } else {
                            throw new CommandSignsCommandException(messages.get("error.no_super_permission"));
                        }
                    } else {
                        commands.set(index - 1, toChange);
                    }
                }
            }

        } catch (NumberFormatException e) {
            throw new CommandSignsCommandException(messages.get("error.command.require_number"));
        }
    }

    @Override
    public List<String> onTabComplete(CommandBlock data, List<String> args) {
        if (args.size() <= 1) {
            return SUB_COMMANDS;
        }

        String subCommand = args.remove(0);
        if ("add".equals(subCommand)) {
            return Collections.emptyList();
        }

        if (args.size() == 1) {
            int limit = data.getCommands().size();
            if (limit == 0) {
                return Collections.emptyList();
            }

            return IntStream.range(1, limit + 1).mapToObj(Integer::toString).collect(Collectors.toList());
        }

        return Collections.emptyList();
    }
}

