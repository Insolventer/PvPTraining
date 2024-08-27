package net.javamio.utility;

import net.javamio.Training;
import org.bukkit.Bukkit;

public class MessageUtils {

    public static String getString(String key) {
        return Training.getInstance().getConfig().getString(key, "");
    }

    public static int getInt(String key) {
        return Training.getInstance().getConfig().getInt(key, 0);
    }

    public static void logger(String message) {
        Bukkit.getConsoleSender().sendRichMessage(message);
    }

}