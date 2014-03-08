package me.planetguy.hwh.handler;

import java.util.logging.Level;

import me.planetguy.hwh.HigherWorldsHack;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.google.common.collect.BiMap;

public class HWHPlayerMoveHandler implements Listener{

	private final BiMap<World, World> worldStack;
	public HWHPlayerMoveHandler(BiMap<World, World> worldStack, HigherWorldsHack hwh) {
		this.worldStack=worldStack;
	}

	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerMove(PlayerMoveEvent event) {

		//BAD BAD BAD test code
		//	hwh.randomlyRegisterWorld(event.getPlayer().getWorld());

		Location to=event.getTo();
		int newY=to.getBlockY();
		if(newY<HigherWorldsHack.SHARE_HEIGHT){
			Player p=event.getPlayer();
			World w=worldStack.get(event.getTo().getWorld());
			if(w==null)return;
			Location toTeleportTo=new Location(w, to.getX(), HigherWorldsHack.transformPosUp(newY), to.getZ());
			HigherWorldsHack.HWH_LOGGER.log(Level.WARNING, "Player moved from "+to+" to "+toTeleportTo);
			p.teleport(toTeleportTo);
		}else if(newY>HigherWorldsHack.WORLD_HEIGHT-HigherWorldsHack.SHARE_HEIGHT){
			Player p=event.getPlayer();
			World w=worldStack.inverse().get(event.getTo().getWorld());
			if(w==null)return;
			Location toTeleportTo=new Location(w, to.getX(), HigherWorldsHack.transformPosDown(newY), to.getZ());
			HigherWorldsHack.HWH_LOGGER.log(Level.WARNING, "Player moved from "+to+" to "+toTeleportTo);
			p.teleport(toTeleportTo);
		}
	}
}
