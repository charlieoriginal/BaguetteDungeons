package net.kizuki.baguettedungeons.commands;

import net.kizuki.baguettedungeons.dungeons.DungeonHandler;
import net.kizuki.baguettedungeons.utils.ColorUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DungeonAdminCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            if (args.length > 0) {
                String first = args[0];
                Player player = (Player) sender;
                if (first.equalsIgnoreCase("addentrance")) {
                    if (player.hasPermission("dungeons.admin")) {
                        DungeonHandler.getInstance().addEntranceLocation(player.getLocation());
                        player.sendMessage(ColorUtils.colorize("&aYou have added a dungeon entrance at: " + player.getLocation().getX() + ", " + player.getLocation().getY() + ", " + player.getLocation().getZ() + "!"));
                    } else {
                        player.sendMessage(ColorUtils.colorize("&cYou do not have permission to run this command!"));
                    }
                } else if (first.equalsIgnoreCase("addenemylocation")) {
                    if (player.hasPermission("dungeons.admin")) {
                        DungeonHandler.getInstance().addEnemySpawnLocation(player.getLocation());
                        player.sendMessage(ColorUtils.colorize("&aYou have added a dungeon enemy spawn at: " + player.getLocation().getX() + ", " + player.getLocation().getY() + ", " + player.getLocation().getZ() + "!"));
                    } else {
                        player.sendMessage(ColorUtils.colorize("&cYou do not have permission to run this command!"));
                    }
                }
            }
        } else {
            sender.sendMessage("Consoles cannot run this command. All admin commands require a player location.");
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> auto = new ArrayList<>();
            auto.add("addentrance");
            auto.add("addenemylocation");
            return auto;
        }

        return null;
    }
}
