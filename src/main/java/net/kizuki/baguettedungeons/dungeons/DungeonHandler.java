package net.kizuki.baguettedungeons.dungeons;

import lombok.Getter;
import lombok.Setter;
import net.kizuki.baguettedungeons.BaguetteDungeons;
import net.kizuki.baguettedungeons.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DungeonHandler {

    private static DungeonHandler handler;
    private DungeonTimerTask task;
    private DungeonEnemyRespawnTask respawnTask;
    @Getter @Setter private boolean isDungeonActive;
    @Getter @Setter private long lastDungeon;
    @Getter @Setter private long dungeonPeriod;
    @Getter @Setter private long dungeonCooldown;
    @Getter private List<Location> entranceLocations;
    @Getter private HashMap<Location, DungeonEnemy> enemySpawnLocations;
    @Getter private List<Location> bossSpawnLocations;

    private DungeonHandler() {
        isDungeonActive = true;
        lastDungeon = -1L;
        dungeonPeriod = 10000L;
        dungeonCooldown = 10000L;
        entranceLocations = new ArrayList<>();
        enemySpawnLocations = new HashMap<>();
        bossSpawnLocations = new ArrayList<>();
        task = new DungeonTimerTask(this);
        respawnTask = new DungeonEnemyRespawnTask(this);
        Bukkit.getScheduler().runTaskTimer(JavaPlugin.getPlugin(BaguetteDungeons.class), task, 0L, 20L);
        Bukkit.getScheduler().runTaskTimer(JavaPlugin.getPlugin(BaguetteDungeons.class), respawnTask, 0L, 20L);
    }

    public static DungeonHandler getInstance() {
        if (handler == null) {
            handler = new DungeonHandler();
        }

        return handler;
    }

    public void addEntranceLocation(Location location) {
        entranceLocations.add(location);
    }

    public void addEnemySpawnLocation(Location location) {
        enemySpawnLocations.put(location, new DungeonEnemy(EntityType.ZOMBIE));
    }

    public void addBossSpawnLocation(Location location) {
        bossSpawnLocations.add(location);
    }

    public String getFormattedCooldown() {
        long timeRemaining = (lastDungeon + dungeonPeriod + dungeonCooldown) - System.currentTimeMillis();

        if (timeRemaining <= 0) {
            isDungeonActive = true;
            return ColorUtils.colorize("&f1&7ms");
        }

        //Format Long into (n)d (n)h (n)m (n)s
        long days = timeRemaining / 86400000;
        timeRemaining -= days * 86400000;
        long hours = timeRemaining / 3600000;
        timeRemaining -= hours * 3600000;
        long minutes = timeRemaining / 60000;
        timeRemaining -= minutes * 60000;
        long seconds = timeRemaining / 1000;
        timeRemaining -= seconds * 1000;

        String formattedCooldown = "";
        if (days > 0) {
            formattedCooldown += ColorUtils.colorize("&f" + days + "&7d ");
        }
        if (hours > 0) {
            formattedCooldown += ColorUtils.colorize("&f" + hours + "&7h ");
        }
        if (minutes > 0) {
            formattedCooldown += ColorUtils.colorize("&f" + minutes + "&7m ");
        }
        if (seconds > 0) {
            formattedCooldown += ColorUtils.colorize("&f" + seconds + "&7s ");
        }
        if (timeRemaining > 0 && seconds == 0) {
            formattedCooldown += ColorUtils.colorize("&f" + timeRemaining + "&7ms");
        }

        //If formattedcooldown ends in " " remove the space.
        if (formattedCooldown.endsWith(" ")) {
            formattedCooldown = formattedCooldown.substring(0, formattedCooldown.length() - 1);
        }

        return formattedCooldown;
    }

    public void teleportToDungeon(Player player) {
        if (entranceLocations.size() == 0) {
            player.sendMessage(ColorUtils.colorize("&cNo dungeon entrance locations found!"));
            return;
        }
        int randomLocationIndex = (int) (Math.random() * entranceLocations.size());
        player.teleport(entranceLocations.get(randomLocationIndex));
    }

}
