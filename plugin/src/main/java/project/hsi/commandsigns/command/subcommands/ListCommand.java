package project.hsi.commandsigns.command.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import project.hsi.commandsigns.command.CommandRequiringManager;
import project.hsi.commandsigns.controller.NCommandSignsManager;
import project.hsi.commandsigns.model.CommandBlock;
import project.hsi.commandsigns.model.CommandSignsCommandException;
import project.hsi.commandsigns.utils.CommandSignUtils;

import java.util.List;


/**
 * Created by nokorbis on 1/20/16.
 */
public class ListCommand extends CommandRequiringManager {
    private static final int LIST_SIZE = 10;

    public ListCommand(NCommandSignsManager manager) {
        super(manager, "list", new String[]{"l"});
        this.basePermission = "commandsign.admin.list";
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) throws CommandSignsCommandException {
        if (!(sender instanceof Player)) {
            throw new CommandSignsCommandException(commandMessages.get("error.command.player_requirement"));
        }

        int index = 1;
        if (!args.isEmpty()) {
            try {
                index = Integer.parseInt(args.get(0));
            } catch (NumberFormatException ignored) {
            }
        }

        int max = (index * LIST_SIZE) - 1;
        int min = (max + 1) - LIST_SIZE;

        String m = commandMessages.get("list.summary");
        m = m.replace("{MIN}", String.valueOf(min));
        m = m.replace("{MAX}", String.valueOf(max));
        m = m.replace("{CMD_AMOUNT}", String.valueOf(CommandBlock.getBiggerUsedId()));
        sender.sendMessage(m);

        manager.getCommandBlockIDs()
                .filter(id -> id >= min && id <= max)
                .sorted((o1, o2) -> (int) (o1 - o2))
                .map(manager::getCommandBlock)
                .forEach(cmd -> sender.sendMessage(formatCommandBlock(cmd)));

        return true;
    }

    private String formatCommandBlock(CommandBlock cmd) {
        String msg = commandMessages.get("list.format");
        msg = CommandSignUtils.formatLocation(msg, cmd.getLocation());

        if (cmd.getName() != null) {
            msg = msg.replace("{NAME}", cmd.getName());
        } else {
            msg = msg.replace("{NAME}", commandMessages.get("list.no_name"));
        }
        msg = msg.replace("{ID}", String.valueOf(cmd.getId()));

        return msg;
    }

    @Override
    public void printUsage(CommandSender sender) {
        sender.sendMessage("/commandsign list [page]");
    }
}
