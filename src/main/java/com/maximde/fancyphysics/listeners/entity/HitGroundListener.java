package com.maximde.fancyphysics.listeners.entity;

import com.maximde.fancyphysics.FancyPhysics;
import com.maximde.fancyphysics.utils.ParticleDisplay;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class HitGroundListener implements Listener {

    private FancyPhysics fancyPhysics;

    public HitGroundListener(FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
    }

    @EventHandler
    public void onBlockFall(EntityChangeBlockEvent event) {
        if(!this.fancyPhysics.config.isBlockParticles()) return;
        if(event.getBlock().getType() != Material.AIR) return;
        if (event.getEntityType() == EntityType.FALLING_BLOCK) {
            FallingBlock fallingBlock = (FallingBlock) event.getEntity();
            event.setCancelled(true);
            Material material = fallingBlock.getMaterial();
            final var loc = event.getBlock().getLocation();
            loc.getWorld().playSound(loc, Sound.ENTITY_ITEM_PICKUP, 1.0f, 1.0f);
            simulate3DParticles(loc, material);
        }
    }

    private void simulate3DParticles(Location location, Material material) {
        for(float y = 0.333F; y <= 0.999F; y = y + 0.333F) {
            for(float x = 0.333F; x <= 0.999F; x = x + 0.333F) {
                for(float z = 0.333F; z <= 0.999F; z = z + 0.333F) {
                    new ParticleDisplay(location, material, x - 0.25F, y - 0.25F, z - 0.25F, this.fancyPhysics, 10.0F / 30);
                }
            }
        }
    }
}