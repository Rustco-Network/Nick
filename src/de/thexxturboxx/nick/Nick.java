package de.thexxturboxx.nick;

import java.io.File;
import java.lang.reflect.Field;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

public class Nick extends JavaPlugin {
	
	public static Nick instance;
	public static File path = new File("plugins/Nick"), dataPath;
    private static CommandMap cmap;
	
	public static Nick getInstance() {
		return instance;
	}
	
	@Override
	public void onEnable() {
		try {
			if(Bukkit.getServer() instanceof CraftServer) {
				final Field f = CraftServer.class.getDeclaredField("commandMap");
				f.setAccessible(true);
				cmap = (CommandMap)f.get(Bukkit.getServer());
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		NickCmd cmd_nick = new NickCmd(this);
		cmap.register("", cmd_nick);
		cmd_nick.setExecutor(new NickCmdExec(this));
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public void loadConfiguration() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	public void simplePardon(String name) {
		getServer().getBanList(Type.NAME).pardon(name);
	}
	
	public void simpleBan(String name, String reason) {
		getServer().getBanList(Type.NAME).addBan(name, reason, null, null);
	}
	
	public static File getPluginPath() {
		return path;
	}
	
	public static File getDataPath() {
		return dataPath;
	}
	
	public static double round(double value, int decimal) {
	    return (double) Math.round(value * Math.pow(10d, decimal)) / Math.pow(10d, decimal);
	}
}