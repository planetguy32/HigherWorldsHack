package me.planetguy.hwh.handler;

import java.util.logging.Level;

import me.planetguy.hwh.HigherWorldsHack;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.google.common.collect.BiMap;

public class HWHBlockPlaceHandler implements Listener{
	
	private final BiMap<World, World> worldStack;
	public HWHBlockPlaceHandler(BiMap<World, World> worldStack, HigherWorldsHack hwh) {
		this.worldStack=worldStack;
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onBlockPlace(BlockPlaceEvent evt){
		//TODO fix
		Block b=evt.getBlock();
		Location to=b.getLocation();
		int newY=to.getBlockY();
		if(newY<HigherWorldsHack.SHARE_HEIGHT*3){
			World w=worldStack.get(b.getWorld());
			if(w==null)return;
			Location toTeleportTo=new Location(w, to.getX(), HigherWorldsHack.transformPosUp(newY), to.getZ());
			w.getBlockAt(toTeleportTo).setType(b.getType());
			HigherWorldsHack.HWH_LOGGER.log(Level.WARNING, "Placed "+w.getBlockAt(toTeleportTo)+" at newY "+newY);
		}else if(newY>HigherWorldsHack.WORLD_HEIGHT-HigherWorldsHack.SHARE_HEIGHT*3){
			World w=worldStack.inverse().get(b.getWorld());
			if(w==null)return;
			Location toTeleportTo=new Location(w, to.getX(), HigherWorldsHack.transformPosDown(newY), to.getZ());
			w.getBlockAt(toTeleportTo).setType(b.getType());
			HigherWorldsHack.HWH_LOGGER.log(Level.WARNING, "Placed "+w.getBlockAt(toTeleportTo)+" at newY "+newY);
		}
	}

}
