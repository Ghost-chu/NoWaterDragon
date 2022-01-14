package com.bilicraft.nowaterdragon;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ComplexEntityPart;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class NoWaterDragon extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(this,this);

        Bukkit.getScheduler().runTaskTimer(this,()-> Bukkit.getWorlds().forEach(world->{
            if(world.getEnvironment() != World.Environment.THE_END)
                return;
            world.getEntitiesByClass(EnderDragon.class).forEach(enderDragon->{
                boolean boom = false;
                for (ComplexEntityPart part : enderDragon.getParts()) {
                    Location loc = part.getLocation();
                    int blockX = loc.getBlockX();
                    int blockY = loc.getBlockY();
                    int blockZ = loc.getBlockZ();
                    for(int blockx=blockX-5;blockx<blockX+6;blockx++){
                        for(int blocky=blockY-5;blocky<blockY+6;blocky++){
                            for(int blockz=blockZ-5;blockz<blockZ+6;blockz++){
                                Block block = new Location(enderDragon.getWorld(),blockx,blocky,blockz).getBlock();
                                if(block.getType() == Material.WATER){
                                    // BUG USAGE PUNISH
                                    block.setType(Material.AIR);
                                    if(!boom) {
                                        block.getLocation().getWorld().createExplosion(block.getLocation(), 1.0f, true, false);
                                        boom = true;
                                    }
                                    enderDragon.setHealth(enderDragon.getMaxHealth());
                                }
                            }
                        }
                    }
                }

            });
        }),200,60);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
