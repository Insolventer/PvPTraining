package net.javamio.module;

import net.javamio.Training;
import net.javamio.utility.EffectUtil;
import net.javamio.utility.ItemBuilder;
import net.javamio.utility.MessageUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class ZombieModule {



    public static void spawnZombie(Player player) {
        player.SendRichMessage(MessageUtils.getString("messages.zombie.spawn");
        final ItemStack weapon, helmet, chestplate, leggings, boots;

        ItemBuilder itemBuilder = new ItemBuilder();

        Entity entity = player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE
        );
        if (entity instanceof Zombie zombie) {


            String displayName = MessageUtils.getString("zombie-totem.display-name").replace("%player%", "[" + player.getName() + "´s]");
            Component component = MiniMessage.miniMessage().deserialize(displayName);

            String effectTypeString = Training.getInstance().getConfig().getString("zombie.effect.type");
            int effectDuration = Training.getInstance().getConfig().getInt("zombie.effect.duration");
            int effectAmplifier = Training.getInstance().getConfig().getInt("zombie.effect.amplifier");

            assert effectTypeString != null;
            PotionEffectType effectType = PotionEffectType.getByName(effectTypeString);
            if (effectType != null) {
                EffectUtil.setZombieEffect(zombie, effectType, effectDuration , effectAmplifier);
            }

            entity.customName(component);
            entity.setCustomNameVisible(true);
            entity.setGlowing(true);
            zombie.setTarget(player);

            weapon = itemBuilder.createCustomItem("items.sword");
            helmet = itemBuilder.createCustomItem("items.helmet");
            chestplate = itemBuilder.createCustomItem("items.chestplate");
            leggings = itemBuilder.createCustomItem("items.leggings");
            boots = itemBuilder.createCustomItem("items.boots");

            ItemStack totems = new ItemStack(Material.TOTEM_OF_UNDYING, MessageUtils.getInt("zombie-totem.amount"));

            zombie.getEquipment().setHelmet(helmet);
            zombie.getEquipment().setChestplate(chestplate);
            zombie.getEquipment().setLeggings(leggings);
            zombie.getEquipment().setBoots(boots);

            zombie.getEquipment().setItemInOffHand(totems);
            zombie.getEquipment().setItemInMainHand(weapon);

            zombie.getEquipment().setHelmetDropChance(0.0F);
            zombie.getEquipment().setChestplateDropChance(0.0F);
            zombie.getEquipment().setLeggingsDropChance(0.0F);
            zombie.getEquipment().setBootsDropChance(0.0F);

            zombie.getEquipment().setItemInOffHandDropChance(0.0F);

            NamespacedKey key = new NamespacedKey(Training.getInstance(), "totem_zombie_owner");
            zombie.getPersistentDataContainer().set(key, PersistentDataType.STRING, player.getUniqueId().toString());

            MessageUtils.getString("messages.totem-zombie-spawn.success").replace("%player%", player.getName());
        }
    }

    public static void despawnZombie(Player player) {
        for (Entity entity : player.getLocation().getWorld().getEntities()) {
            if (entity.getType() == EntityType.ZOMBIE) {
                Zombie zombie = (Zombie) entity;
                NamespacedKey key = new NamespacedKey(Training.getPlugin(Training.class), "totem_zombie_owner");
                if (zombie.getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
                    String owner = zombie.getPersistentDataContainer().get(key, PersistentDataType.STRING);
                    assert owner != null;
                    if (owner.equals(player.getUniqueId().toString())) {
                        zombie.remove();
                        MessageUtils.getString("messages.totem-zombie-despawn.success").replace("%player%", player.getName());
                        if (!(player.isOnline())) return;
                        player.sendRichMessage(MessageUtils.getString("messages.totem-zombie-despawn.success"));
                        return;
                    }
                }
            }
        }
        MessageUtils.getString("messages.totem-zombie-despawn.fail").replace("%player%", player.getName());
    }
    public static void despawnAllZombies() {
        for (Entity entity : Training.getInstance().getServer().getWorlds().get(0).getEntities()) {
            if (entity.getType() == EntityType.ZOMBIE && entity.customName() != null && Objects.equals(entity.customName(), MessageUtils.getString("zombie-totem.display-name"))) {
                entity.remove();
            }
        }
    }
}
