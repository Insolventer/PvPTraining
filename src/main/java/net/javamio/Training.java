package net.javamio;

import net.javamio.command.TrainingCommand;
import net.javamio.listeners.PlayerDeathListener;
import net.javamio.module.ZombieModule;
import net.javamio.utility.MessageUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class Training extends JavaPlugin {

    private static Training instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        MessageUtils.logger("<gradient:#7808FB:#ADE3FD>Fork by Insolventer</gradient>");
        new TrainingCommand("training");
        new PlayerDeathListener();
    }

    @Override
    public void onDisable() {
        ZombieModule.despawnAllZombies();
    }

    public static Training getInstance() {
        return instance;
    }
}
