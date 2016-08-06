package de.thexxturboxx.nick;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.inventivetalent.nicknamer.api.NickNamerAPI;

public class UnNickCmdExec implements CommandExecutor {
	
	Nick plugin;
	
	public UnNickCmdExec(Nick plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("nick.cmd.unnick")) {
				if(NickNamerAPI.getNickManager().isNicked(p.getUniqueId())) {
					if(args.length == 0) {
						NickNamerAPI.getNickManager().removeNick(p.getUniqueId());
						NickNamerAPI.getNickManager().removeSkin(p.getUniqueId());
					} else {
						p.sendMessage(ChatColor.RED + "Nutze /unnick");
					}
				} else {
					p.sendMessage(ChatColor.RED + "Du bist nicht genickt!");
				}
			} else {
				p.sendMessage(ChatColor.RED + "Dazu hast du keine Erlaubnis!");
			}
		} else {
			plugin.getServer().getLogger().info("Das kann nur ein Spieler machen, du Schlingel ;)");
		}
		return true;
	}
	
}