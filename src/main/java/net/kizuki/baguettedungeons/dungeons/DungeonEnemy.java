package net.kizuki.baguettedungeons.dungeons;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.ItemStack;

public class DungeonEnemy {

    // To support items in armor/hand slots. Must be an entity which allows it. (i.e. Zombie, Husk, Pigman)
    @Getter @Setter private EntityType entityType;
    // Must be greater than 1D.
    // Amount of hearts is customHealthValue / 2.
    @Getter private Location spawnLocation;
    @Getter @Setter private double customHealthValue = 20D;
    @Getter @Setter private ItemStack helmet = new ItemStack(Material.AIR);
    @Getter @Setter private ItemStack chestplate = new ItemStack(Material.AIR);
    @Getter @Setter private ItemStack leggings = new ItemStack(Material.AIR);
    @Getter @Setter private ItemStack boots = new ItemStack(Material.AIR);
    @Getter @Setter private ItemStack hand = new ItemStack(Material.AIR);
    @Getter private Entity spawnedEntity;
    @Getter private int respawnTimeSeconds = 5;

    public DungeonEnemy(EntityType type) {
        this.entityType = type;
    }

    public void respawn(Location location) {
        System.out.println("Respawning Entity");
        if (spawnedEntity == null || spawnedEntity.isDead()) {
            System.out.println("Is null or dead");
            spawnedEntity = location.getWorld().spawnEntity(location, entityType);
            if (spawnedEntity instanceof Mob) {
                System.out.println("Spawned entity & is mob");
                Mob mob = (Mob) spawnedEntity;
                mob.setMaxHealth(customHealthValue);
                mob.setAware(true);
                mob.setAI(true);
                mob.setHealth(customHealthValue);
                mob.getEquipment().setHelmet(helmet);
                mob.getEquipment().setChestplate(chestplate);
                mob.getEquipment().setLeggings(leggings);
                mob.getEquipment().setBoots(boots);
                mob.getEquipment().setItemInMainHand(hand);
            }
        }
    }

}
