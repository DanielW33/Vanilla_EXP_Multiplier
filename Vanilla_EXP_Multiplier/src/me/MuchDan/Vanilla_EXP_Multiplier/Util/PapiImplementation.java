package me.MuchDan.Vanilla_EXP_Multiplier.Util;

import me.MuchDan.Vanilla_EXP_Multiplier.Vanilla_EXP_Multiplier;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PapiImplementation extends PlaceholderExpansion {
    private Vanilla_EXP_Multiplier plugin;

    public  PapiImplementation(Vanilla_EXP_Multiplier plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean persist(){
        return true;
    }
    @Override
    public String getIdentifier() {
        return "vem";
    }

    @Override
    public String getAuthor() {
        return "MuchDan";
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }
    @Override
    public boolean canRegister(){
        return true;
    }


    @Override
    public String onPlaceholderRequest(Player player, String identifier){
        String UUID = player.getUniqueId().toString();
        ConfigIO ConfigFile = plugin.getConfigFile();
        ConfigIO PlayerFile = plugin.getBoostFile();
        List<Double> Multipliers = new ArrayList<>();

        if(player == null){
            return "";
        }

        // %vem_expm%
        if(identifier.equals("expm")){
            ConfigFile.getConfig().getConfigurationSection("Multipliers").getKeys(false).forEach(var ->{
                double Mult = ConfigFile.getConfig().getDouble("Multipliers." + var + ".Multiplier");
                Multipliers.add(Mult);
            });

            if(!PlayerFile.getConfig().contains("Players." + UUID)){
                return "0";
            }
            else {
                String playerMult = PlayerFile.getConfig().getString("Players." + UUID + ".Multiplier");
                int Position = Multipliers.indexOf(Double.valueOf(playerMult)) + 1;
                return String.valueOf(Position);
            }
        }   //%vem_expmult%
        else if(identifier.equals("expmult")){
            if(!PlayerFile.getConfig().contains("Players." + UUID)){
                return "0";
            }
            else{
                return PlayerFile.getConfig().getString("Players." + UUID + ".Multiplier");
            }
        }

        return null;
    }
}
