package me.planetguy.hwh;

import java.util.Map;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class CommandHandler {
	
	public static boolean onCommandSync(String[] args,Player player, Map<World, World> worldStack){
		int radius=1;
		if(args.length==2){
			try{
				radius=Integer.parseInt(args[1]);
			}catch(Exception e){
				player.sendMessage("Invalid second argument!");
				return false;
			}
		}
		int baseX=(int) player.getLocation().getChunk().getX();
		int baseZ=(int) player.getLocation().getChunk().getZ();
		World playerWorld=player.getWorld();
		for(int i=0; i<radius; i++){
			for(int j=0; j<radius; j++){
				HWHBorderManager.sync(playerWorld.getChunkAt(baseX+i, baseZ+j), worldStack);
			}
		}
		
		player.sendMessage("Synchronized current chunk");
		return true;
	}
	
	public static boolean onCommandBorder(String[] args,Player player, Map<World, World> worldStack){
		int radius=1;
		if(args.length==2){
			try{
				radius=Integer.parseInt(args[1]);
			}catch(Exception e){
				player.sendMessage("Invalid second argument!");
				return false;
			}
		}
		int baseX=(int) player.getLocation().getX();
		int baseZ=(int) player.getLocation().getZ();
		World playerWorld=player.getWorld();
		for(int i=1-radius; i<radius; i++){
			for(int j=1-radius; j<radius; j++){
				HWHBorderManager.fillNetherOverworldBorder(playerWorld.getChunkAt(baseX+16*i, baseZ+16*j), worldStack);
			}
		}
		
		player.sendMessage("Corrected border between nether and overworld terrain");
		return true;
	}

}
