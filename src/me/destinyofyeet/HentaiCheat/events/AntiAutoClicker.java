package me.destinyofyeet.HentaiCheat.events;

import me.destinyofyeet.HentaiCheat.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AntiAutoClicker implements Listener {
    Map<UUID, Integer> antiAutoClickerCheckMap = new HashMap<>();
    Map<UUID, Integer> antiAutoClickerHitsMap = new HashMap<>();

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
        if (!(event.getDamager() instanceof Player)){
            return;
        }

        Player player = (Player) event.getDamager();

        Integer hits = antiAutoClickerHitsMap.get(player.getUniqueId());
        if (hits == null){
            hits = 1;
        } else {
            hits += 1;
        }
        antiAutoClickerHitsMap.put(player.getUniqueId(), hits);

        if (antiAutoClickerCheckMap.containsKey(player.getUniqueId())){
            // check is already running
            return;
        }



        int taskID;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable(){
            int countdown = 1;

            @Override
            public void run(){
                if (countdown <= 0){
                    System.out.println(player.getName() + " has " + antiAutoClickerHitsMap.get(player.getUniqueId()) + " cps");

                    antiAutoClickerHitsMap.put(player.getUniqueId(), 0);


                    Bukkit.getScheduler().cancelTask(antiAutoClickerCheckMap.get(player.getUniqueId()));
                    antiAutoClickerCheckMap.remove(player.getUniqueId());
                } else {
                    countdown --;
                }
            }
        }, 0, 20);

        antiAutoClickerCheckMap.put(player.getUniqueId(), taskID);

    }
}
