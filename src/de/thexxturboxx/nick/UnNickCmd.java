package de.thexxturboxx.nick;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UnNickCmd extends Command {
	
	Nick plugin;
	CommandExecutor exe = null;
	
	public UnNickCmd(Nick plugin) {
		super("xunnick", "Sich unnicken", "/xunnick", Arrays.asList(new String[]{}));
		this.plugin = plugin;
	}
	
	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if(exe != null) {
			return exe.onCommand(sender, this, commandLabel, args);
        }
        return false;
	}
	
	public void setExecutor(CommandExecutor exe){
        this.exe = exe;
    }
	
}