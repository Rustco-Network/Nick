package de.thexxturboxx.nick;

import java.util.Random;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.inventivetalent.nicknamer.api.NickNamerAPI;

public class NickCmdExec implements CommandExecutor {
	
	Nick plugin;
	
	public NickCmdExec(Nick plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(args.length == 0) {
				int lengthArray = plugin.getServer().getOfflinePlayers().length;
				OfflinePlayer disguise = plugin.getServer().getOfflinePlayers()[new Random().nextInt(lengthArray)];
				NickNamerAPI.getNickManager().setNick(p.getUniqueId(), disguise.getName());
				NickNamerAPI.getNickManager().setSkin(p.getUniqueId(), disguise.getName());
			} else if(args.length == 1) {
				NickNamerAPI.getNickManager().setNick(p.getUniqueId(), args[0]);
				NickNamerAPI.getNickManager().setSkin(p.getUniqueId(), args[0]);
			}
		} else {
			plugin.getServer().getLogger().info("Das kann nur ein Spieler machen, du Schlingel ;)");
		}
		return true;
	}
	
}