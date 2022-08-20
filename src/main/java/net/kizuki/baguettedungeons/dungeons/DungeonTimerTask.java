package net.kizuki.baguettedungeons.dungeons;

public class DungeonTimerTask implements Runnable {
    private DungeonHandler dungeonHandler;

    public DungeonTimerTask(DungeonHandler dungeonHandler) {
        this.dungeonHandler = dungeonHandler;
    }

    @Override
    public void run() {
        long cooldownPeriod = dungeonHandler.getDungeonCooldown();
        long openPeriod = dungeonHandler.getDungeonPeriod();
        long lastOpened = dungeonHandler.getLastDungeon();

        if (lastOpened == -1L) {
            dungeonHandler.setLastDungeon(System.currentTimeMillis());
            return;
        }

        if ((lastOpened + openPeriod) <= System.currentTimeMillis()) {
            dungeonHandler.setDungeonActive(false);
            if ((lastOpened + openPeriod + cooldownPeriod) <= System.currentTimeMillis()) {
                dungeonHandler.setLastDungeon(System.currentTimeMillis());
                dungeonHandler.setDungeonActive(true);
            }
        }
    }
}
