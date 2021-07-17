package me.destinyofyeet.HentaiCheat.events;

import me.destinyofyeet.HentaiCheat.main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;

import java.util.List;

public class BoatRegistering implements Listener {
    private final FileConfiguration config = Main.getPlugin().getConfig();

    @EventHandler
    public void onEntityPlace(EntityPlaceEvent event){
        if (!event.getEntityType().equals(EntityType.BOAT)){
            return;
        }

        List<String> boatList = config.getStringList("Boats.allBoats");
        boatList.add(event.getEntity().getUniqueId().toString());
        config.set("Boats.allBoats", boatList);
        Main.getPlugin().saveConfig();
    }
}
