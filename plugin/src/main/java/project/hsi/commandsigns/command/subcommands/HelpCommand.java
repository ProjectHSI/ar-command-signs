package project.hsi.commandsigns.command.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import project.hsi.commandsigns.command.Command;
import project.hsi.commandsigns.command.ICommand;
import project.hsi.commandsigns.model.CommandSignsCommandException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Created by nokorbis on 1/20/16.
 */
public class HelpCommand extends Command {

    private List<ICommand> commands;

    public HelpCommand(List<ICommand> commands) {
        super("help", new String[]{"h", "?"});
        this.basePermission = "commandsign.admin.help";
        this.commands = commands;
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) throws CommandSignsCommandException {
        if (args.isEmpty()) {
            if (!(sender instanceof Player)) {
                throw new CommandSignsCommandException(commandMessages.get("error.command.player_requirement"));
            }
            for (ICommand cmd : this.commands) {
                cmd.printUsage(sender, false);
            }
        } else {
            Counter count = new Counter();
            String subCmd = args.get(0).toLowerCase();
            this.commands.stream().filter((cmd) -> cmd.isCommand(subCmd)).forEach((cmd) -> {
                cmd.printUsage(sender);
                count.increment();
            });
            if (count.value == 0) {
                sender.sendMessage(commandMessages.get("error.command.help.invalid"));
            }
        }
        return true;
    }

    @Override
    public void printUsage(CommandSender sender) {
        sender.sendMessage("/commandsign help [command]");
    }

    @Override
    public List<String> autoComplete(CommandSender sender, List<String> args) {
        Stream<String> commandStream = this.commands.stream().filter(cmd -> cmd.hasBasePermission(sender)).map(ICommand::getCommand);

        if (!args.isEmpty()) {
            final String start = args.get(0).toLowerCase();
            commandStream = commandStream.filter(value -> value.startsWith(start));
        }
        return commandStream.collect(Collectors.toList());
    }

    private static class Counter {
        int value = 0;

        void increment() {
            value += 1;
        }
    }
}
