package me.planetguy.hwh.handler;

import me.planetguy.hwh.HWHBorderManager;
import me.planetguy.hwh.HigherWorldsHack;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;

import com.google.common.collect.BiMap;

public class HWHWgenHandler implements Listener{
	
	private final BiMap<World, World> worldBelow;
	public boolean active=true;
	
	public HWHWgenHandler(BiMap<World, World> worldStack, HigherWorldsHack hwh) {
		this.worldBelow=worldStack;
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onChunkGenerate(ChunkPopulateEvent evt){
		if(!active)return;
		if(evt.getWorld().getName().equalsIgnoreCase("world")){
			HWHBorderManager.sync(evt.getChunk(), worldBelow);
			if(worldBelow.get(evt.getWorld()).getName().equals("world_nether")){
				HWHBorderManager.fillNetherOverworldBorder(evt.getChunk(), worldBelow);
			}
		}
		
	}

}
