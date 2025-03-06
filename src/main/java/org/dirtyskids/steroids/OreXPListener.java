package org.dirtyskids.steroids;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class OreXPListener implements Listener{
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Material blockType = event.getBlock().getType();
        if (blockType == Material.COAL_ORE || blockType == Material.DIAMOND_ORE || blockType == Material.IRON_ORE) {
            int currentXP = event.getExpToDrop();
            int increasedXP = currentXP * 3;
            event.setExpToDrop(increasedXP);
        }
    }
}
