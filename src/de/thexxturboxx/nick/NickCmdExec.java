package de.thexxturboxx.nick;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.inventivetalent.nicknamer.NickNamerPlugin;
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
			if(p.hasPermission("nick.cmd.nick") || p.isOp()) {
				if(args.length == 0) {
					String randomName;
					NickNamerAPI.getNickManager().setNick(p.getUniqueId(), randomName = getRandomName(plugin, p.getName()));
					NickNamerAPI.getNickManager().setSkin(p.getUniqueId(), randomName);
					p.sendMessage(Nick.getPrefix() + ChatColor.DARK_RED + "Du spielst nun als" + ChatColor.GRAY + ": " + ChatColor.GOLD + randomName);
				} else if(args.length == 1) {
					if(args[0].equals(p.getName())) {
						p.sendMessage(Nick.getPrefix() + ChatColor.DARK_RED + "Was genau willst du damit erreichen? o_O");
					} else if(plugin.getServer().getOnlinePlayers().contains(plugin.getServer().getPlayer(args[0]))) {
						p.sendMessage(Nick.getPrefix() + ChatColor.DARK_RED + "Dieser Spieler ist leider gerade online.");
					} else if(!NickNamerAPI.getNickManager().getPlayersWithNick(args[0]).isEmpty()) {
						p.sendMessage(Nick.getPrefix() + ChatColor.DARK_RED + "Dieser Nick wurde bereits vergeben.");
					} else {
						NickNamerAPI.getNickManager().setNick(p.getUniqueId(), args[0]);
						NickNamerAPI.getNickManager().setSkin(p.getUniqueId(), args[0]);
						p.sendMessage(Nick.getPrefix() + ChatColor.DARK_RED + "Du spielst nun als" + ChatColor.GRAY + ": " + ChatColor.GOLD + args[0]);
					}
				} else {
					p.sendMessage(Nick.getPrefix() + ChatColor.DARK_RED + "Nutze /xnick [Name]");
				}
			} else {
				p.sendMessage(Nick.getPrefix() + ChatColor.DARK_RED + "Dazu hast du keine Erlaubnis!");
			}
		} else {
			plugin.getServer().getLogger().info("Das kann nur ein Spieler machen, du Schlingel ;)");
		}
		return true;
	}
	
	public static String getRandomName(Nick plugin, String notTake) {
		String randomName = NickNamerAPI.getRandomNick(NickNamerPlugin.instance.randomNicks.get("__default__"));
		if(NickNamerPlugin.instance.randomSkins.get("__default__").contains(randomName) &&
				!randomName.equals(notTake) &&
				!plugin.getServer().getOnlinePlayers().contains(plugin.getServer().getPlayer(randomName)) &&
				NickNamerAPI.getNickManager().getPlayersWithNick(randomName).isEmpty())
			return randomName;
		return getRandomName(plugin, notTake);
	}
	
}