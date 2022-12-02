package fr.delaria.core.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityListener implements Listener {

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        if(event.getEntityType() == EntityType.CREEPER) event.setCancelled(true);
    }

    @EventHandler
    public void onPickupBlock(EntityChangeBlockEvent event) {
        if(event.getEntityType() == EntityType.ENDERMAN) event.setCancelled(true);
    }
}