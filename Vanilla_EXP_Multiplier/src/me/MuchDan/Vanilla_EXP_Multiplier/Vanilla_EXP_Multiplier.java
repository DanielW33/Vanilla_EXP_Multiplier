package me.MuchDan.Vanilla_EXP_Multiplier;

import me.MuchDan.Vanilla_EXP_Multiplier.Commands.*;
import me.MuchDan.Vanilla_EXP_Multiplier.Util.ConfigIO;
import me.MuchDan.Vanilla_EXP_Multiplier.EventListeners.PlayerGainExpEvent;
import me.MuchDan.Vanilla_EXP_Multiplier.Util.PapiImplementation;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Vanilla_EXP_Multiplier extends JavaPlugin {

    private ConfigIO BoostFile;
    private ConfigIO ConfigFile;

    @Override
    public void onEnable(){

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null){
            this.getServer().getPluginManager().disablePlugin(this);
            this.getLogger().log(Level.SEVERE, "Depend PlaceholderAPI was not found.");
        }
        else {
            if(new PapiImplementation(this).register()){
                this.getLogger().log(Level.INFO, "Successfully registered placeholder %vem_expm%");
            }

            this.getLogger().log(Level.INFO, "Depend PlaceholderAPI was found.");

            this.BoostFile = new ConfigIO(this, "ActiveExpBoosts.yml");
            this.BoostFile.getConfig().options().copyDefaults(false);
            this.BoostFile.saveDefaultConfig();

            this.ConfigFile = new ConfigIO(this, "MultiplierConfig.yml");
            this.ConfigFile.getConfig().options().copyDefaults(false);
            this.ConfigFile.saveDefaultConfig();

            CommandTabCompleter TabCompleter = new CommandTabCompleter(this);
            this.getCommand("SetMultiplier").setExecutor(new GiveExperienceMultiplier(this));
            this.getCommand("RemoveMultiplier").setExecutor(new RemoveExperienceMultiplier(this));
            this.getCommand("MultiplierReload").setExecutor(new ReloadConfig(this));
            this.getCommand("XPMultiplier").setExecutor(new VerifyPlaceholder(this));
            this.getCommand("SetMultiplier").setTabCompleter(TabCompleter);

            this.getServer().getPluginManager().registerEvents(new PlayerGainExpEvent(this), this);
            this.getLogger().log(Level.INFO, "Successfully Enabled Vanilla EXP Booster");
        }
    }
    @Override
    public void onDisable(){

    }

    public void setBoostFile(ConfigIO File){
        BoostFile = File;
    }
    public ConfigIO getBoostFile(){
        return BoostFile;
    }
    public void setConfigFile(ConfigIO File){
        this.ConfigFile = File;
    }
    public ConfigIO getConfigFile(){
        return this.ConfigFile;
    }
}
