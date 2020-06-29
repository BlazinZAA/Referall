package net.viedantmc.Referall;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;

public class Main extends JavaPlugin {
	  public DataManager data;
	  public RewardsManager rewards;
	  public PlaytimeManager playtime;
	  public static LuckPerms api = null;
	  public static HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();

	  
	  @Override
	  public void onEnable() {
		  saveDefaultConfig();
		    this.data = new DataManager(this);
		    this.rewards = new RewardsManager(this);
		    this.playtime = new PlaytimeManager(this);
//		    BukkitScheduler scheduler = getServer().getScheduler();
//		    scheduler.scheduleSyncRepeatingTask((Plugin)this, new Runnable() {
//		          public void run() {
//		            Main.this.trackPlaytime();
//		          }
//		        },  0L, 20L);
		    
	  }
	  
	  @Override
	  public void onDisable() {}
	  

	  
	  
	 
	  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		  Player player = (Player) sender;
		  PermissionAttachment attachment = player.addAttachment(this);
		  perms.put(player.getUniqueId(), attachment);
		  PermissionAttachment pperms = perms.get(player.getUniqueId());
		  
		  int secondsPlayed = player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20;
			if(label.equalsIgnoreCase("invited") && sender.hasPermission("referal.invited")){
				if(sender instanceof Player) {
					if(secondsPlayed > 2700 && secondsPlayed < 10800) {
					if(args.length != 0) {
						if (!this.getConfig().contains(args[0] + ".referals")) {
							this.getConfig().createSection(args[0]+ ".referals");
							this.getConfig().set(args[0] + ".referals", 0);
							
						}
						this.getConfig().set(args[0] + ".referals", this.getConfig().getInt(args[0] + ".referals") + 1);
						saveConfig();
						sender.sendMessage("Passed 1");
						player.isPermissionSet("referal.invited");
						perms.get(player.getUniqueId()).unsetPermission("referal.invited");
						return true;	
					}
					sender.sendMessage("You must put in a username!");
					sender.sendMessage("Passed 3");
					return true; 
				}
				}
				sender.sendMessage("Console cannot run this command");
				return true;	
			}
			sender.sendMessage("Failed");
			return false;
	  }	  
	  public void onJoin(PlayerJoinEvent p) {
		  Player player = (Player) p;
		  String playername = player.getName();
		  PermissionAttachment attachment = player.addAttachment(this);
		  perms.put(player.getUniqueId(), attachment);
		  PermissionAttachment pperms = perms.get(player.getUniqueId());
		  Set<String> keys = this.rewards.getConfig().getKeys(false);
		  for (String key : keys) {
		  if (this.data.getConfig().getInt(playername + ".referals") == this.rewards.getConfig().getInt(key + ".referals")){
			  pperms.setPermission(this.rewards.getConfig().getString(key + "permission"), true);
		  }
		  }
		  
	  } 
}

