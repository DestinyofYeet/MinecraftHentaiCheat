package me.destinyofyeet.HentaiCheat.main;

import me.destinyofyeet.HentaiCheat.events.*;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getPluginManager;

public class Main extends JavaPlugin {
    public static Main hentaiCheat;

    @Override
    public void onEnable(){
        System.out.println("[HentaiCheat] Started loading!");
        hentaiCheat = this;

        PluginManager manager = getPluginManager();
        manager.registerEvents(new AntiJesus(), this);
        manager.registerEvents(new AntiBoatFly(), this);
        manager.registerEvents(new BoatRegistering(), this);
        manager.registerEvents(new AntiSpeed(), this);
        manager.registerEvents(new AntiAutoClicker(), this);
        manager.registerEvents(new AntiRange(), this);

        System.out.println("[HentaiCheat] Finished loading!");

    }

    public static Main getPlugin(){return hentaiCheat;}
}
