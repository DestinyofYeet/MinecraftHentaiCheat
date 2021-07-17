package me.destinyofyeet.HentaiCheat.utils;

import me.destinyofyeet.HentaiCheat.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class PlayerUtils {

    public static Block getBlockPlayerIsIn(Player player){
        return player.getWorld().getBlockAt(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
    }

    public static void kickPlayer(Player player, String message){
        player.kickPlayer("§cKicked by the HentaiCheat for:§6\n" + message);
        Main.getPlugin().getServer().getConsoleSender().sendMessage(ChatColor.RED + "[HentaiCheat]" + ChatColor.YELLOW + " Kicked " + player.getName() + " for: " + message);
    }

    public static void sendMessage(Player player, String message){
        player.sendMessage(ChatColor.RED + "[HentaiCheat] " + ChatColor.YELLOW + message);
    }
}
