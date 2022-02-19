package me.petterim1.reservedslots;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.*;
import cn.nukkit.plugin.PluginBase;

public class Plugin extends PluginBase implements Listener {

    private int freeSlots;
    private String kickMessageNoSlot;

    public void onEnable() {
        saveDefaultConfig();
        int reservedSlots = getConfig().getInt("reservedSlots");
        if (reservedSlots > getServer().getMaxPlayers()) {
            getLogger().warning("The amount of reserved slots can't be more than the server's maximum player count! Defaulting to the maximum player count");
            reservedSlots = getServer().getMaxPlayers();
        }
        freeSlots = getServer().getMaxPlayers() - reservedSlots;
        kickMessageNoSlot = getConfig().getString("kickMessageNoSlot");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPreLogin(PlayerPreLoginEvent e) {
        if (getServer().getOnlinePlayers().size() >= freeSlots && !e.getPlayer().hasPermission("reservedslots.slot")) {
            e.setKickMessage(kickMessageNoSlot);
            e.setCancelled(true);
        }
    }
}
