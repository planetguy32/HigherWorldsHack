package me.planetguy.hwh;

import java.util.Map;

import me.planetguy.util.RandomBlock;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class HWHBorderManager {
	
	static RandomBlock boundary=new RandomBlock();
	
	static RandomBlock movingstuff=new RandomBlock();
	
	static{
		boundary.addMaterial(Material.COAL_BLOCK,4);
		boundary.addMaterial(Material.DIAMOND_ORE);
		boundary.addMaterial(Material.IRON_BLOCK);
		boundary.addMaterial(Material.GOLD_BLOCK);
		boundary.addMaterial(Material.REDSTONE_ORE);
		boundary.addMaterial(Material.OBSIDIAN, 100);
		
		movingstuff.addMaterial(Material.LAVA,3);
		movingstuff.addMaterial(Material.OBSIDIAN,2);
		movingstuff.addMaterial(Material.STONE);
		
	}
	
	public static void sync(Chunk topChunk, Map<World, World> worldBelow){
		World wBelow=worldBelow.get(topChunk.getWorld());
		if(wBelow==null)return;
	
		wBelow.loadChunk(topChunk.getX(), topChunk.getZ(), true);
		
		int wXOffset=topChunk.getX()*16;
		int wZOffset=topChunk.getZ()*16;
		
		for(int x=0; x<16; x++){
			for(int y=0; y<3*HigherWorldsHack.SHARE_HEIGHT; y++){
				for(int z=0; z<16; z++){
					Block b=topChunk.getBlock(x, y, z);
					wBelow.getBlockAt(x+wXOffset, y+HigherWorldsHack.WORLD_HEIGHT-HigherWorldsHack.SHARE_HEIGHT*3, z+wZOffset).setType(b.getType());
				}
			}
		}
	}
	
	public static void fillNetherOverworldBorder(Chunk topChunk, Map<World, World> worldBelow){
		World wBelow=worldBelow.get(topChunk.getWorld());
		if(wBelow==null)return;
	
		wBelow.loadChunk(topChunk.getX(), topChunk.getZ(), true);
		Chunk bottomChunk=wBelow.getChunkAt(topChunk.getX(), topChunk.getZ());
		int wXOffset=topChunk.getX();
		int wZOffset=topChunk.getZ();
		
		for(int x=0; x<16; x++){
			for(int y=HigherWorldsHack.WORLD_HEIGHT/2-5; y<HigherWorldsHack.WORLD_HEIGHT-3*HigherWorldsHack.SHARE_HEIGHT+5; y++){
				for(int z=0; z<16; z++){
					Block b=bottomChunk.getBlock(x, y, z);
					if(b.getType().equals(Material.BEDROCK)
							||b.getType().equals(Material.AIR)){
						Material m=boundary.next();
						HigherWorldsHack.HWH_LOGGER.warning("border is setting "+b);
						b.setType(m);
					}
				}
			}
		}
	}

}
