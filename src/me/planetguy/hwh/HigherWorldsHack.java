package me.planetguy.hwh;

import java.util.logging.Logger;

import me.planetguy.hwh.handler.HWHBlockBreakHandler;
import me.planetguy.hwh.handler.HWHBlockPlaceHandler;
import me.planetguy.hwh.handler.HWHPlayerMoveHandler;
import me.planetguy.hwh.handler.HWHWgenHandler;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public final class HigherWorldsHack extends JavaPlugin{

	private BiMap<World, World> worldStack=HashBiMap.create();
	
	public static Logger HWH_LOGGER;
	
	private HWHWgenHandler wh;
	
	public static final int SHARE_HEIGHT=32;
	public static final int WORLD_HEIGHT=256;
	
	private World top, bottom;
	
	public static int transformPosUp(int posOld){
		return posOld+5*SHARE_HEIGHT;
	}

	public static int transformPosDown(int posOld){
		return posOld-5*SHARE_HEIGHT;
	}

	public void onEnable() {
		getServer().getPluginManager().registerEvents(new HWHPlayerMoveHandler(worldStack, this), this);
		getServer().getPluginManager().registerEvents(new HWHBlockBreakHandler(worldStack, this), this);
		getServer().getPluginManager().registerEvents(new HWHBlockPlaceHandler(worldStack, this), this);
		wh=new HWHWgenHandler(worldStack, this);
		getServer().getPluginManager().registerEvents(wh, this);
		HWH_LOGGER=this.getLogger();
		loadConfigs();
		//HWH_LOGGER.log(Level.WARNING, "Running HWH");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("hwh")){
			if(args.length!=1&&args.length!=2){
				sender.sendMessage("Command takes 1 argument");
				return false;
			}else if(args[0].equalsIgnoreCase("reload")){
					loadConfigs();
					sender.sendMessage("Reloaded HWH config");
					return true;
			}else if(!(sender instanceof Player)){
				//Only let players declare world as top or bottom
				sender.sendMessage("Only reload may be run from console");
				return false;
			}else{
				Player player=(Player) sender;
				if(args[0].equalsIgnoreCase("top")){
					top=player.getWorld();
					sender.sendMessage("Top of to-create link: "+player.getWorld().getName());
					tryRegisterWorlds();
					return true;
				}else if(args[0].equalsIgnoreCase("bottom")){
					bottom=player.getWorld();
					sender.sendMessage("Bottom of to-create link: "+player.getWorld().getName());
					tryRegisterWorlds();
					return true;
				}else if(args[0].equalsIgnoreCase("sync")){
					return CommandHandler.onCommandSync(args, player, worldStack);
				}else if(args[0].equalsIgnoreCase("border")){
					return CommandHandler.onCommandBorder(args, player, worldStack);
				}else{
					sender.sendMessage("Legal args: top, bottom, reload, sync");
					return false;
				}
			}
		}
		return false;
	}
	
	public void loadConfigs(){
		HWH_LOGGER.warning("Loading the config");
		saveDefaultConfig();
		FileConfiguration fc=getConfig();
		for(World w:getServer().getWorlds()){
			if(fc.contains("worldAbove."+w.getName())){
				String s=fc.getString("worldAbove."+w.getName());
				if(s==null)continue;
				World w2=getServer().getWorld(s);
				if(w2==null)continue;
				else{
					worldStack.put(w2, w);
				}
			}
		}
		String s=fc.getString("options.wgenSync");
		if(s!=null){
			wh.active=Boolean.parseBoolean(s);
		}
	}
	
	public void tryRegisterWorlds(){
		if(top!=null&&bottom!=null){
			getServer().broadcastMessage("Linking worlds "+top.getName()+" and "+bottom.getName()+"!");
			worldStack.put(top, bottom);
			top=null;
			bottom=null;
		}
	}

}
