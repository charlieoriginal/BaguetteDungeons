package net.kizuki.baguettedungeons;

import net.kizuki.baguettedungeons.commands.DungeonAdminCommand;
import net.kizuki.baguettedungeons.commands.DungeonCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class BaguetteDungeons extends JavaPlugin {

    private DungeonCommand command;
    private DungeonAdminCommand adminCommand;

    @Override
    public void onEnable() {
        // Plugin startup logic
        registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCommands() {
        this.command = new DungeonCommand();
        getCommand("dungeon").setExecutor(command);
        getCommand("dungeon").setTabCompleter(command);
        this.adminCommand = new DungeonAdminCommand();
        getCommand("dungeonadmin").setExecutor(adminCommand);
        getCommand("dungeonadmin").setTabCompleter(adminCommand);
    }
}
