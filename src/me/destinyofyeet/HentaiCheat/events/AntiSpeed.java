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
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class AntiSpeed implements Listener {
    private final static Map<UUID, Integer> speedCheckMap = new HashMap<>();
    private final boolean debug = false;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();


        if (speedCheckMap.containsKey(player.getUniqueId())){
            // check below is already running
            return;
        }

        if (player.isFlying()){ return; }
        if (player.isInsideVehicle()) {return; }
        Location oldLocation = player.getLocation();

        int taskID;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
            int countdown = 1;

            @Override
            public void run() {
                if (countdown <= 0) {

                    double maxSpeed = 23.4;
                    double speedMultiplier = 1;

                    double calculatedSpeed = player.getLocation().distanceSquared(oldLocation);
                    Block blockUnderPlayer = player.getWorld().getBlockAt(player.getLocation().getBlockX(), player.getLocation().getBlockY() - 1, player.getLocation().getBlockZ());
                    Block blockUnderPlayer2 = player.getWorld().getBlockAt(player.getLocation().getBlockX(), player.getLocation().getBlockY() - 2, player.getLocation().getBlockZ());

                    List<Material> materialsToCheckAgainst = Arrays.asList(Material.ICE, Material.BLUE_ICE, Material.FROSTED_ICE, Material.PACKED_ICE, Material.AIR);
                    if (materialsToCheckAgainst.contains(blockUnderPlayer.getType()) || materialsToCheckAgainst.contains(blockUnderPlayer2.getType())) {
                        if (!blockUnderPlayer.getType().equals(Material.AIR) || !blockUnderPlayer2.getType().equals(Material.AIR))
                            maxSpeed = 83.3;
                    }

                    if (player.hasPotionEffect(PotionEffectType.SPEED)){
                        int effectAmplifier = player.getPotionEffect(PotionEffectType.SPEED).getAmplifier();
                        if (effectAmplifier == 0){
                            speedMultiplier = 1.4375;

                        } else if (effectAmplifier == 1){
                            speedMultiplier = 1.95;

                        } else {
                            if (debug){
                                System.out.println(effectAmplifier);
                            }
                        }
                    }

                    if (debug){
                        System.out.println("Final calculated speed: " + maxSpeed * speedMultiplier + ", Calculated speed: " + calculatedSpeed + ", Max speed: " + maxSpeed + ", Speed multiplier: " + speedMultiplier + ", Block: " + blockUnderPlayer.getType() + ", Block 2: " + blockUnderPlayer2.getType());
                    }

                    if (calculatedSpeed > maxSpeed * speedMultiplier + 0.1){
                        if (!debug){
                            player.teleport(oldLocation);
                            PlayerUtils.sendMessage(player, "Anti-Speed!");
                        } else {
                            System.out.println("TRIGGERED: Final calculated speed: " + maxSpeed * speedMultiplier + ", Calculated speed: " + calculatedSpeed + ", Max speed: " + maxSpeed + ", Speed multiplier: " + speedMultiplier + ", Block: " + blockUnderPlayer.getType() + ", Block 2: " + blockUnderPlayer2.getType());
                        }

                    }



                    Bukkit.getScheduler().cancelTask(speedCheckMap.get(player.getUniqueId()));
                    speedCheckMap.remove(player.getUniqueId());
                } else {

                    countdown --;
                }

            }
        }, 0, 10);
        speedCheckMap.put(player.getUniqueId(), taskID);
    }
}
