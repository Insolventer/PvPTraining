package net.javamio.utility;

import net.javamio.Training;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ItemBuilder {

    public static ItemStack createArmorItem(Material material, Enchantment enchantment, int level) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.addEnchant(enchantment, level, true);
            meta.setUnbreakable(true);
            item.setItemMeta(meta);
        }
        return item;
    }
    public ItemStack createCustomItem(String configPath) {
        String materialName = Training.getInstance().getConfig().getString(configPath + ".material");
        Material material = Material.getMaterial(materialName != null ? materialName : "DIAMOND");
        assert material != null;
        ItemStack item = new ItemStack(material);

        ItemMeta meta = item.getItemMeta();
        if (meta != null) {

            boolean unbreakable = Training.getInstance().getConfig().getBoolean(configPath + ".unbreakable", true);
            meta.setUnbreakable(unbreakable);

            Map<String, Object> enchantments = Objects.requireNonNull(Training.getInstance().getConfig().getConfigurationSection(configPath + ".enchantments")).getValues(false);
            for (Map.Entry<String, Object> entry : enchantments.entrySet()) {
                Enchantment enchantment = Enchantment.getByName(entry.getKey());
                if (enchantment != null) {
                    meta.addEnchant(enchantment, (int) entry.getValue(), true);
                }
            }

            List<String> flags = Training.getInstance().getConfig().getStringList(configPath + ".flags");
            for (String flag : flags) {
                try {
                    meta.addItemFlags(ItemFlag.valueOf(flag));
                } catch (IllegalArgumentException e) {
                    Training.getInstance().getLogger().warning("Invalid item flag: " + flag);
                }
            }

            item.setItemMeta(meta);
        }

        return item;
    }
}
