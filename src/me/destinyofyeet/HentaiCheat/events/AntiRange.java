package me.destinyofyeet.HentaiCheat.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AntiRange implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
        if (!(event.getDamager() instanceof Player)){
            return;
        }

        Player player = (Player) event.getDamager();
        Entity target = event.getEntity();

        System.out.println(player.getLocation().distanceSquared(target.getLocation()));
    }
}
