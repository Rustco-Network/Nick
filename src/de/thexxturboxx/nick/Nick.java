package de.thexxturboxx.nick;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.inventivetalent.nicknamer.api.NickNamerAPI;

public class Nick extends JavaPlugin implements Listener {
	
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
				cmap = (CommandMap) f.get(Bukkit.getServer());
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		NickCmd cmd_nick = new NickCmd(this);
		cmap.register("", cmd_nick);
		cmd_nick.setExecutor(new NickCmdExec(this));
		UnNickCmd cmd_unnick = new UnNickCmd(this);
		cmap.register("", cmd_unnick);
		cmd_unnick.setExecutor(new UnNickCmdExec(this));
		getServer().getPluginManager().registerEvents(this, this);
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
	
	public static String getPrefix() {
		return ChatColor.GRAY + "[" + ChatColor.DARK_PURPLE + "NICK" + ChatColor.GRAY + "] ";
	}
	
	public static double round(double value, int decimal) {
	    return (double) Math.round(value * Math.pow(10d, decimal)) / Math.pow(10d, decimal);
	}
	
	@EventHandler
	public void adminTabComplete(PlayerChatTabCompleteEvent e) {
		if(e.getPlayer().hasPermission("nick.chat.admintab") || e.getPlayer().isOp()) {
			String message = e.getChatMessage();
			Collection<String> nickedPlayers = NickNamerAPI.getNickManager().getUsedNicks();
			List<String> newMessages = new ArrayList<String>();
			for(String nickName : nickedPlayers) {
				if(startsWithIgnoreCase(nickName, message))
					newMessages.add(nickName);
			}
			for(Player p : getServer().getOnlinePlayers()) {
				String name = p.getName();
				if(!newMessages.contains(name) && !inCollectionStartsWithIgnoreCase(nickedPlayers, message))
					newMessages.add(name);
			}
			e.getTabCompletions().clear();
			for(String nickName : newMessages) {
				e.getTabCompletions().add(nickName);
			}
		}
	}
	
	private boolean inCollectionStartsWithIgnoreCase(Collection<String> c, String toSearch) {
		for(String s : c) {
			if(startsWithIgnoreCase(s, toSearch))
				return true;
		}
		return false;
	}
	
	private boolean startsWithIgnoreCase(String s1, String s2) {
		return s1.length() >= s2.length() &&
				s1.substring(0, s2.length()).equalsIgnoreCase(s2);
	}
	
}