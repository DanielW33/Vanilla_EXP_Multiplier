package me.MuchDan.Vanilla_EXP_Multiplier.EventListeners;


import me.MuchDan.Vanilla_EXP_Multiplier.ConfigManagers.PlayerEXPMultiplierConfigManager;
import me.MuchDan.Vanilla_EXP_Multiplier.ConfigManagers.PluginConfigurationManagerVEM;
import me.MuchDan.Vanilla_EXP_Multiplier.Vanilla_EXP_Multiplier;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

import java.util.logging.Level;

public class PlayerGainExpEvent implements Listener {
    private Vanilla_EXP_Multiplier plugin;
    private PlayerEXPMultiplierConfigManager PlayerFile;
    private PluginConfigurationManagerVEM ConfigFile;
    public PlayerGainExpEvent(Vanilla_EXP_Multiplier plugin){
        this.plugin = plugin;
        this.PlayerFile = plugin.getBoostFile();
        this.ConfigFile = plugin.getConfigFile();
    }
    @EventHandler
    public void OnMineCraftExpGain(PlayerExpChangeEvent Event) {
        String PlayerUUID = Event.getPlayer().getUniqueId().toString();
        if (PlayerFile.getConfig().contains("Players." + PlayerUUID)) {
            if (Event.getAmount() > 0) {        //If this code runs, the Player has a multiplier.

                double Multiplier = getMultiplier(PlayerUUID);  //Variables lifetime is to the end of this if statement.
                double NewVal = Multiplier * Event.getAmount();
                int Rounded = (int) Math.round(NewVal);

                if(ConfigFile.getConfig().getBoolean("Verbose")) {
                    this.plugin.getServer().getLogger().log(Level.INFO, "Player: " + Event.getPlayer().getName());
                    this.plugin.getServer().getLogger().log(Level.INFO,"Multiplier from File: " + Multiplier);
                    this.plugin.getServer().getLogger().log(Level.INFO,"Original: " + Event.getAmount());
                    this.plugin.getServer().getLogger().log(Level.INFO,"Rounded: " + Rounded);
                }

                Event.setAmount(Rounded);   //Rounds up or down depending on the compiler.
            }
        }
    }

    public double getMultiplier(String UUID){
        return Double.parseDouble(PlayerFile.getConfig().getString("Players." + UUID + ".Multiplier"));
    }
}
