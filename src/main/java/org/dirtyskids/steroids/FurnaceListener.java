package org.dirtyskids.steroids;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;

public class FurnaceListener implements Listener {
    @EventHandler
    public void onFunaceBurn(FurnaceBurnEvent event) {
        int defaultBurnTime = event.getBurnTime();
        int fasterBurnTime = defaultBurnTime / 2;
        event.setBurnTime(fasterBurnTime);
    }
}