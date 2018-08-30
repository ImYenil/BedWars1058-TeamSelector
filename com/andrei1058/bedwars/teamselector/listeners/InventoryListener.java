package com.andrei1058.bedwars.teamselector.listeners;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.teamselector.configuration.Config;
import com.andrei1058.bedwars.teamselector.teamselector.TeamSelectorGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

    @EventHandler
    //Prevent inventory move
    public void onInventoryClick(InventoryClickEvent e){
        ItemStack i = e.getCurrentItem();
        if (i == null) return;
        if (i.getType() == Material.AIR) return;
        if (!Main.nms.isCustomBedWarsItem(i)) return;
        String identifier = Main.nms.getCustomData(i);

        if (identifier.equals(TeamSelectorGUI.TEAM_SELECTOR_IDENTIFIER)){
            e.setCancelled(Config.config.getBoolean(Config.ALLOW_MOVE_TROUGH_INVENTORY));
        } else if (identifier.startsWith(TeamSelectorGUI.TEAM_JOIN_IDENTIFIER)){
            String[] s = identifier.split("_");
            if (s.length == 2){
                TeamSelectorGUI.joinTeam((Player) e.getWhoClicked(), s[1]);
                e.getWhoClicked().closeInventory();
            }
        }
    }

    @EventHandler
    public void onInvClose(InventoryCloseEvent e){
        if (TeamSelectorGUI.openGUIs.contains(e.getPlayer().getUniqueId())){
            TeamSelectorGUI.openGUIs.remove(e.getPlayer().getUniqueId());
        }
    }
}
