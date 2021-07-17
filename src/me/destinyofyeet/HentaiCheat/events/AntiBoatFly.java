package me.destinyofyeet.HentaiCheat.events;

import me.destinyofyeet.HentaiCheat.main.Main;
import me.destinyofyeet.HentaiCheat.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AntiBoatFly implements Listener {
    private static final Map<UUID, Integer> boatFlyCheckMap = new HashMap<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if (!player.isInsideVehicle())
            return;

        if (boatFlyCheckMap.containsKey(player.getUniqueId())){
            // check below is already running
            return;
        }
        Location originalLocation = player.getLocation();
        int taskID;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
            int countdown = 3;

            @Override
            public void run() {
                if (countdown <= 0) {
                    if (player.getLocation().getY() - originalLocation.getY() > 0){
                        player.teleport(originalLocation);
                        PlayerUtils.sendMessage(player, "Anti-BoatFly!");
                    }


                    Bukkit.getScheduler().cancelTask(boatFlyCheckMap.get(player.getUniqueId()));
                    boatFlyCheckMap.remove(player.getUniqueId());
                } else {

                    countdown --;
                }

            }
        }, 0, 10);
        boatFlyCheckMap.put(player.getUniqueId(), taskID);
    }
}
