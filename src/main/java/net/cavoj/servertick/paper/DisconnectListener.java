package net.cavoj.servertick.paper;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class DisconnectListener implements Listener {
    private final ServerTick plugin;

    public DisconnectListener(ServerTick plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        plugin.onDisconnected(event.getPlayer());
    }
}
