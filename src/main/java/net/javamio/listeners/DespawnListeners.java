package net.javamio.listeners;

import net.javamio.Training;
import net.javamio.module.ZombieModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class DespawnListeners implements Listener { // Rename this class
    public DespawnListeners() {
        Training.getInstance().getServer().getPluginManager().registerEvents(this, Training.getInstance());
    }

    @EventHandler
    public void handle(PlayerDeathEvent event) {
        Player player = event.getEntity();
        ZombieModule.despawnZombie(player);
    }

    @EventHandler
    public void handle(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ZombieModule.despawnZombie(player);
    }

    @EventHandler
    public void handle(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        ZombieModule.despawnZombie(player);
    }
}