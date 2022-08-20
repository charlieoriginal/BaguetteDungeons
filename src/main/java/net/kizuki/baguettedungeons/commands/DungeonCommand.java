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

import java.util.List;

public class DungeonCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (DungeonHandler.getInstance().isDungeonActive()) {
                DungeonHandler.getInstance().teleportToDungeon(player);
            } else {
                player.sendMessage(ColorUtils.colorize("&aThe dungeon opens again in " + DungeonHandler.getInstance().getFormattedCooldown() + "&a!"));
            }
        } else {
            sender.sendMessage("Consoles cannot be teleported, duh.");
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}
