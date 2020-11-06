package me.MuchDan.Vanilla_EXP_Multiplier;

import me.MuchDan.Vanilla_EXP_Multiplier.Commands.CommandTabCompleter;
import me.MuchDan.Vanilla_EXP_Multiplier.Commands.GiveExperienceMultiplier;
import me.MuchDan.Vanilla_EXP_Multiplier.Commands.ReloadConfig;
import me.MuchDan.Vanilla_EXP_Multiplier.Commands.RemoveExperienceMultiplier;
import me.MuchDan.Vanilla_EXP_Multiplier.ConfigManagers.PlayerEXPMultiplierConfigManager;
import me.MuchDan.Vanilla_EXP_Multiplier.ConfigManagers.PluginConfigurationManagerVEM;
import me.MuchDan.Vanilla_EXP_Multiplier.EventListeners.PlayerGainExpEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Vanilla_EXP_Multiplier extends JavaPlugin {

    private PlayerEXPMultiplierConfigManager BoostFile;
    private PluginConfigurationManagerVEM ConfigFile;

    @Override
    public void onEnable(){

        this.BoostFile = new PlayerEXPMultiplierConfigManager(this);
        this.BoostFile.getConfig().options().copyDefaults(false);
        this.BoostFile.saveDefaultConfig();

        this.ConfigFile = new PluginConfigurationManagerVEM(this);
        this.ConfigFile.getConfig().options().copyDefaults(false);
        this.ConfigFile.saveDefaultConfig();

        CommandTabCompleter TabCompleter = new CommandTabCompleter(this);
        this.getCommand("SetMultiplier").setExecutor(new GiveExperienceMultiplier(this));
        this.getCommand("RemoveMultiplier").setExecutor(new RemoveExperienceMultiplier(this));
        this.getCommand("MultiplierReload").setExecutor(new ReloadConfig(this));

        this.getCommand("SetMultiplier").setTabCompleter(TabCompleter);

        this.getServer().getPluginManager().registerEvents(new PlayerGainExpEvent(this),this);
        this.getLogger().log(Level.INFO, "Successfully Enabled Vanilla EXP Booster");
    }
    @Override
    public void onDisable(){

    }

    public void setBoostFile(PlayerEXPMultiplierConfigManager File){
        BoostFile = File;
    }
    public PlayerEXPMultiplierConfigManager getBoostFile(){
        return BoostFile;
    }
    public void setConfigFile(PluginConfigurationManagerVEM File){
        this.ConfigFile = File;
    }
    public PluginConfigurationManagerVEM getConfigFile(){
        return this.ConfigFile;
    }
}
