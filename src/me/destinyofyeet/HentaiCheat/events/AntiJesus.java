package me.destinyofyeet.HentaiCheat.events;

import me.destinyofyeet.HentaiCheat.main.Main;
import me.destinyofyeet.HentaiCheat.utils.*;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.io.File;
import java.util.*;

public class AntiJesus implements Listener {
    private static final Map<UUID, Integer> jesusCheckMap = new HashMap<>();
    private final FileConfiguration config = Main.getPlugin().getConfig();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Block block = player.getWorld().getBlockAt(player.getLocation().getBlockX(), player.getLocation().getBlockY() - 1, player.getLocation().getBlockZ());
        if (block.getType().equals(Material.WATER) && !PlayerUtils.getBlockPlayerIsIn(player).getType().equals(Material.WATER)){
            if (player.isInsideVehicle() && player.getVehicle() instanceof Boat){
                // player is in a boat
                return;
            }
            // todo: fix to not give the player a hit if he is standing on a boat
            if (player.isFlying()){
                // don't want to flag them as jesusing while flying close above water surface in creative
                return;
            }
            List<String> allBoats = config.getStringList("Boats.allBoats");
            List<String> boatsToRemove = new ArrayList<>();
            for (String uuid: allBoats){
                Entity entity = Bukkit.getEntity(UUID.fromString(uuid));
                if (entity == null){
                    boatsToRemove.add(uuid);
                    continue;
                }
                if (entity.getType().equals(EntityType.BOAT)){
                    if (player.getLocation().distanceSquared(entity.getLocation()) <= 2*2){
                        return;
                    }
                }
            }
            allBoats.removeAll(boatsToRemove);
            config.set("Boats.allBoats", allBoats);
            Main.getPlugin().saveConfig();
            if (jesusCheckMap.containsKey(player.getUniqueId())){
                // check below is already running
                return;
            }
            Location oldLocation = player.getLocation();
            int taskID;
            taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
                int countdown = 5;

                @Override
                public void run() {
                    if (countdown <= 0) {
                        Block block = player.getWorld().getBlockAt(player.getLocation().getBlockX(), player.getLocation().getBlockY() - 1, player.getLocation().getBlockZ());
                        Block block2 = player.getWorld().getBlockAt(player.getLocation().getBlockX(), player.getLocation().getBlockY() - 1, player.getLocation().getBlockZ());
                        if ((block.getType().equals(Material.WATER) || block2.getType().equals(Material.WATER)) && !PlayerUtils.getBlockPlayerIsIn(player).getType().equals(Material.WATER)) {

                        player.teleport(oldLocation);
                        PlayerUtils.sendMessage(player, "Anti-Jesus!");
                        }
                        Bukkit.getScheduler().cancelTask(jesusCheckMap.get(player.getUniqueId()));
                        jesusCheckMap.remove(player.getUniqueId());
                    } else {

                        countdown --;
                    }

                }
            }, 0, 10);
            jesusCheckMap.put(player.getUniqueId(), taskID);
        }

    }
}
