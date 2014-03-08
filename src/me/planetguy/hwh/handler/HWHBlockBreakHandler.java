package me.planetguy.hwh.handler;

import me.planetguy.hwh.HigherWorldsHack;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.google.common.collect.BiMap;

public class HWHBlockBreakHandler implements Listener{
	
	private final BiMap<World, World> worldBelow;
	public HWHBlockBreakHandler(BiMap<World, World> worldStack, HigherWorldsHack hwh) {
		this.worldBelow=worldStack;
	}


	@EventHandler(priority=EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent evt){
		Block b=evt.getBlock();
		Location to=b.getLocation();
		int newY=to.getBlockY();
		if(newY<HigherWorldsHack.SHARE_HEIGHT*3){
			World w=worldBelow.get(b.getWorld());
			if(w==null)return;
			Location toTeleportTo=new Location(w, to.getX(), HigherWorldsHack.transformPosUp(newY), to.getZ());
			w.getBlockAt(toTeleportTo).setType(Material.AIR);
			//HigherWorldsHack.HWH_LOGGER.log(Level.WARNING, "Broke "+newY);
		}else if(newY>HigherWorldsHack.WORLD_HEIGHT-HigherWorldsHack.SHARE_HEIGHT*3){
			World w=worldBelow.inverse().get(b.getWorld());
			if(w==null)return;
			Location toTeleportTo=new Location(w, to.getX(), HigherWorldsHack.transformPosDown(newY), to.getZ());
			w.getBlockAt(toTeleportTo).setType(Material.AIR);
			//HigherWorldsHack.HWH_LOGGER.log(Level.WARNING, "Broke "+newY);
		}
	}

}
