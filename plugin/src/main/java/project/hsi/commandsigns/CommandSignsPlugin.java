package project.hsi.commandsigns;

import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import project.hsi.commandsigns.addons.confirmation.ConfirmationAddon;
import project.hsi.commandsigns.addons.cooldowns.CooldownAddon;
import project.hsi.commandsigns.addons.economy.EconomyAddon;
import project.hsi.commandsigns.addons.items.ItemsAddon;
import project.hsi.commandsigns.addons.requiredpermissions.RequiredPermissionsAddon;
import project.hsi.commandsigns.api.AddonRegister;
import project.hsi.commandsigns.controller.*;
import project.hsi.commandsigns.utils.Settings;

public class CommandSignsPlugin extends JavaPlugin {

    private static CommandSignsPlugin plugin;

    private NCommandSignsManager manager;
    private NCommandSignsAddonRegister addonRegister;

    public static CommandSignsPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void onLoad() {
        plugin = this;
        Settings.loadSettings(plugin);

        addonRegister = new NCommandSignsAddonRegister();
        getServer().getServicesManager().register(AddonRegister.class, addonRegister, this, ServicePriority.Normal);

        addonRegister.registerAddon(new RequiredPermissionsAddon(this));
        addonRegister.registerAddon(new ItemsAddon(this));
        addonRegister.registerAddon(new ConfirmationAddon(this));
        addonRegister.registerAddon(new CooldownAddon(this));
        EconomyAddon economyAddon = new EconomyAddon(this);
        addonRegister.registerAddon(economyAddon);
    }

    @Override
    public void onEnable() {
        this.manager = new NCommandSignsManager(this);

        addonRegister.triggerEnable();
        addonRegister.registerInManager(manager);

        manager.loadIdsPerLocations();
        manager.initializeMenus();
        manager.initializeSerializers();

        NCommandBlockExecutor.setManager(manager);

        CommandSignCommands commandExecutor = new CommandSignCommands(manager);

        PluginCommand mainCommand = this.getCommand("commandsign");
        if (mainCommand != null) {
            mainCommand.setExecutor(commandExecutor);
            mainCommand.setTabCompleter(commandExecutor);
        }

        this.getServer().getPluginManager().registerEvents(new CommandSignListener(manager), this);
        //this.getLogger().info("If this plugin is useful to you, you can make a donation at: https://www.paypal.me/nokorbis");
        this.getLogger().info(ChatColor.AQUA + "CommandSign version : " + CommandSignsPlugin.getPlugin().getDescription().getVersion() + " developed by Nokorbis.");
        this.getLogger().info(ChatColor.GOLD + (ChatColor.MAGIC + "Modded by [Project HSI]"));
    }

    @Override
    public void onDisable() {
        this.manager = null;

        PluginCommand mainCommand = this.getCommand("commandsign");
        if (mainCommand != null) {
            mainCommand.setExecutor(null);
            mainCommand.setTabCompleter(null);
        }
    }
}