package net.javamio.command;

import net.javamio.Training;
import net.javamio.module.ZombieModule;
import net.javamio.utility.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TrainingCommand implements CommandExecutor, TabCompleter {
    public TrainingCommand(String name) {
        Objects.requireNonNull(Training.getInstance().getCommand(name)).setExecutor(this);
        Objects.requireNonNull(Training.getInstance().getCommand(name)).setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendRichMessage(MessageUtils.getString("messages.error.player-only"));
            return true;
        }

        if (args.length == 0) {
            player.sendRichMessage(MessageUtils.getString("messages.error.invalid-command"));
            return false;
        }

        if (!player.hasPermission("training.use")) {
            player.sendRichMessage(MessageUtils.getString("messages.error.no-permission"));
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "spawn" -> {
                ZombieModule.spawnZombie(player);
                return true;
            }
            case "despawn" -> {
                ZombieModule.despawnZombie(player);
                return true;
            }
            case "reload" -> {
                if (player.hasPermission("training.reload")) {
                    Training.getInstance().reloadConfig();
                    player.sendRichMessage(MessageUtils.getString("messages.reload.success"));
                } else {
                    player.sendRichMessage(MessageUtils.getString("messages.error.no-permission"));
                }
                return true;
            }
            default -> {
                player.sendRichMessage(MessageUtils.getString("messages.error.invalid-command"));
                return false;
            }
        }
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player) || !player.hasPermission("training.use")) {
            return new ArrayList<>();
        }

        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.addAll(Arrays.asList("spawn", "despawn", "reload"));
        }

        return completions.stream()
                .filter(s -> s.startsWith(args[args.length - 1].toLowerCase()))
                .sorted()
                .toList();
    }
}
