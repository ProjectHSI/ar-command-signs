package project.hsi.commandsigns;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class CommandSignsPlugin extends JavaPlugin {

    private static CommandSignsPlugin plugin;

    private NCommandSignsManager manager;
    private NCommandSignsAddonRegister addonRegister;

    public static CommandSignsPlugin getPlugin() {
        return plugin;
    }

    public static String version = "V1.1.1-2";

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
        this.getLogger().info(ChatColor.AQUA + "CommandSign version : " + version +  "developed by Nokorbis.");
        this.getLogger().info(ChatColor.GOLD + (ChatColor.MAGIC + "Modded by [Project HSI]"));

        Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), () -> {

            try {

                String LatestVersion = getLatestVersion();

                if (!LatestVersion.equals(version)) {
                    getServer().getLogger().warning(ChatColor.RED + ( ChatColor.MAGIC + "There is a newer version of CommandSigns published!" ));
                    getServer().getLogger().warning(ChatColor.RED + ( ChatColor.MAGIC + "Please update to " + LatestVersion + " if possible!" ));
                }

            } catch (Exception e) {
                getServer().getLogger().warning(ChatColor.RED + ( ChatColor.MAGIC + "Something happened while trying to reach Github, please check if the server is connected to the internet." ));
                getServer().getLogger().warning(ChatColor.RED + ( ChatColor.MAGIC + e.getMessage() ));
            }

        });

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



    public String getLatestVersion() throws IOException {

        URL address = new URL("https://api.github.com/repos/ProjectHSI/ar-command-signs/releases/latest");

        InputStreamReader pageInput = new InputStreamReader(address.openStream());

        BufferedReader source = new BufferedReader(pageInput);

        String sourceLine = source.readLine();

        JsonObject json = new Gson().fromJson(sourceLine, JsonObject.class);

        if (!json.has("name")) {
            throw new IOException("Github servers did not return with a 'name' value.");
        }

        return json.get("name").toString();

    }
}