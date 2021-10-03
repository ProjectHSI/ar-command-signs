package project.hsi.commandsigns.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import project.hsi.commandsigns.CommandSignsPlugin;
import project.hsi.commandsigns.api.exceptions.CommandSignsException;
import project.hsi.commandsigns.controller.NCommandBlockExecutor;
import project.hsi.commandsigns.model.CommandBlock;


public class ExecuteTask implements Runnable {

    private final NCommandBlockExecutor executor;
    private int taskId;
    private Location initialLocation;

    public ExecuteTask(NCommandBlockExecutor cmd) {
        this.executor = cmd;
    }

    @Override
    public void run() {
        final Player player = this.executor.getPlayer();
        if (player != null) {
            try {
                if (player.isOnline() && !player.isDead()) {
                    this.executor.execute();
                }
            } catch (CommandSignsException e) {
                Bukkit.getScheduler().runTask(CommandSignsPlugin.getPlugin(), () -> player.sendMessage(e.getMessage()));
            } finally {
                this.executor.stopPlayerTask(this);
            }
        }
    }

    public CommandBlock getCommandBlock() {
        return this.executor.getCommandBlock();
    }

    public int getTaskId() {
        return this.taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public Location getInitialLocation() {
        return this.initialLocation;
    }

    public void setInitialLocation(Location initialLocation) {
        this.initialLocation = initialLocation;
    }

    public Player getPlayer() {
        return this.executor.getPlayer();
    }

}
