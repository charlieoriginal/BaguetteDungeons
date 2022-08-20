package net.kizuki.baguettedungeons.dungeons;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.HashMap;

public class DungeonEnemyRespawnTask implements Runnable {

    private DungeonHandler dungeonHandler;
    private HashMap<Location, DungeonEnemy> enemySpawnLocations;
    private int timeElapsed = 0;

    public DungeonEnemyRespawnTask(DungeonHandler handler) {
        this.dungeonHandler = handler;
        this.enemySpawnLocations = dungeonHandler.getEnemySpawnLocations();
    }

    @Override
    public void run() {
        timeElapsed++;
        for (Location location : enemySpawnLocations.keySet()) {
            DungeonEnemy enemy = enemySpawnLocations.get(location);
            Entity entity = enemy.getSpawnedEntity();
            if (entity == null || entity.isDead()) {
                int respawnTime = enemy.getRespawnTimeSeconds();
                if (timeElapsed >= respawnTime) {
                    if (enemy.getSpawnedEntity() != null) {
                        enemy.getSpawnedEntity().remove();
                    }
                    enemy.respawn(location);
                    timeElapsed = 0;
                    break;
                }
            }
        }
    }
}
