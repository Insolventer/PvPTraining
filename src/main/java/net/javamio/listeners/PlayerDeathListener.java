package net.javamio.listeners;

import net.javamio.Training;
import net.javamio.module.ZombieModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    public PlayerDeathListener() {
        Training.getInstance().getServer().getPluginManager().registerEvents(this, Training.getInstance());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        ZombieModule.despawnZombie(player);
    }
}