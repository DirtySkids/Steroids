package org.dirtyskids.steroids;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

public class OrePopulator extends BlockPopulator {
    @Override
    public void populate(World world, Random random, Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int y = random.nextInt(world.getMaxHeight());
                Block block = chunk.getBlock(x, y, z);

                if (block.getType() == Material.STONE && random.nextDouble() < 0.10) { //10% Chance
                    block.setType(Material.DIAMOND_ORE);
                }
            }
        }
    }
}
