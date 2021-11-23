package com.andrei1058.bedwars.teamselector;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.teamselector.api.TeamSelector;
import com.andrei1058.bedwars.teamselector.api.TeamSelectorAPI;
import com.andrei1058.bedwars.teamselector.configuration.Config;
import com.andrei1058.bedwars.teamselector.configuration.Messages;
import com.andrei1058.bedwars.teamselector.listeners.ArenaListener;
import com.andrei1058.bedwars.teamselector.listeners.InventoryListener;
import com.andrei1058.bedwars.teamselector.listeners.PlayerInteractListener;
import com.andrei1058.bedwars.teamselector.listeners.SelectorGuiUpdateListener;
import com.andrei1058.spigot.updater.SpigotUpdater;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static BedWars bw;
    public static Main plugin;

    /**
     * Register listeners
     */
    private static void registerListeners(Listener... listeners) {
        PluginManager pm = Bukkit.getPluginManager();
        for (Listener l : listeners) {
            pm.registerEvents(l, plugin);
        }
    }

    @Override
    public void onEnable() {
        plugin = this;

        //Disable if pl not found
        if (Bukkit.getPluginManager().getPlugin("BedWars1058") == null) {
            getLogger().severe("BedWars1058 was not found. Disabling...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        bw = Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();

        if (bw == null) {
            getLogger().severe("Can't hook into BedWars1058.");
            Bukkit.getPluginManager().disablePlugin(plugin);
            return;
        }

        Bukkit.getServicesManager().register(TeamSelectorAPI.class, new TeamSelector(), this, ServicePriority.Normal);

        getLogger().info("Hook into BedWars1058!");

        //Create configuration
        Config.addDefaultConfig();

        //Save default messages
        Messages.setupMessages();

        //Register listeners
        registerListeners(new ArenaListener(), new InventoryListener(), new PlayerInteractListener(), new SelectorGuiUpdateListener());

        new SpigotUpdater(this, 60438, true).checkUpdate();
    }
}
