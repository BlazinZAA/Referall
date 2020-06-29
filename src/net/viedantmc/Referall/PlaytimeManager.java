package net.viedantmc.Referall;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.viedantmc.Referall.Main;

public class PlaytimeManager {
	private Main plugin;
	private FileConfiguration dataConfig = null;
	private File configFile = null;
	
	public PlaytimeManager(Main plugin) {
		this.plugin = plugin;
		saveDefaultConfig();
	}
	public void reloadConfig() {
		if(this.configFile == null)
			this.configFile = new File(this.plugin.getDataFolder(), "rewards.yml");
		
		this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);
		
		InputStream defaultStream = this.plugin.getResource("rewards.yml");
		
		if (defaultStream != null) {
			YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
			this.dataConfig.setDefaults(defaultConfig);
		}
		
		}
		
		

	public FileConfiguration getConfig() {
		if (dataConfig != null) 
			reloadConfig();
		return this.dataConfig;
	
	}
	public void saveConfig() {
		if(this.configFile == null || this.dataConfig == null) {
			return;
		}
		try {
		this.getConfig().save(this.configFile); 
		} catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE, "Could not save config" + this.configFile, e);
		}
	}
	
	public void saveDefaultConfig() {
		if (this.configFile == null) {
			this.configFile = new File(this.plugin.getDataFolder(), "rewards.yml");
			
		}
		if (!this.configFile.exists()) 
			this.plugin.saveResource("rewards.yml", false);
	}
}