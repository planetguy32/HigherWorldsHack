package me.planetguy.hwh.handler;

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
	
	public HWHWgenHandler(BiMap<World, World> worldStack, HigherWorldsHack hwh) {
		this.worldBelow=worldStack;
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onChunkGenerate(ChunkPopulateEvent evt){
		World w=evt.getWorld();
		World wBelow=worldBelow.get(w);
		Chunk newChunk=evt.getChunk();
		Chunk wBelowChunk=wBelow.getChunkAt(newChunk.getBlock(0,0,0));
		wBelowChunk.load(true);
		
		int wXOffset=newChunk.getX()*16;
		int wZOffset=newChunk.getZ()*16;
		
		for(int x=0; x<16; x++){
			for(int y=0; y<3*HigherWorldsHack.SHARE_HEIGHT*3; y++){
				for(int z=0; z<16; z++){
					Block b=newChunk.getBlock(x, y, z);
					wBelow.getBlockAt(x+wXOffset, y+HigherWorldsHack.WORLD_HEIGHT-HigherWorldsHack.SHARE_HEIGHT*3, z+wZOffset).setType(b.getType());
				}
			}
		}
	}

}
