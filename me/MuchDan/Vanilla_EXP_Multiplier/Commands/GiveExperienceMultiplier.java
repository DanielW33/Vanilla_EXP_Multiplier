package me.MuchDan.Vanilla_EXP_Multiplier.Commands;

import me.MuchDan.Vanilla_EXP_Multiplier.ConfigManagers.PlayerEXPMultiplierConfigManager;
import me.MuchDan.Vanilla_EXP_Multiplier.ConfigManagers.PluginConfigurationManagerVEM;
import me.MuchDan.Vanilla_EXP_Multiplier.Vanilla_EXP_Multiplier;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class GiveExperienceMultiplier implements CommandExecutor {
    private Vanilla_EXP_Multiplier plugin;
    private PluginConfigurationManagerVEM ConfigFile;
    private PlayerEXPMultiplierConfigManager PlayerData;

    public GiveExperienceMultiplier(Vanilla_EXP_Multiplier plugin){
        this.plugin = plugin;
        ConfigFile = plugin.getConfigFile();
        PlayerData = plugin.getBoostFile();
    }

    @Override
    public boolean onCommand(CommandSender Sender, Command cmd, String Label, String[] args){
        if(Label.equalsIgnoreCase("setMultiplier")){
            String Permission = ConfigFile.getConfig().getString("Permission");

            if(!Sender.hasPermission(Permission)){
                String Deny = ConfigFile.getConfig().getString("DenyMessage");
                Sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Deny));
                return false;
            }
            if(args.length < 2){
                Sender.sendMessage(ChatColor.GREEN + "Command usage: /setMultiplier [Username] [Multiplier]");
                return false;
            }

            Player player = Bukkit.getPlayerExact(args[0]);

            if(!CheckOnline(player)){
                Sender.sendMessage(ChatColor.RED + "The player in question is not currently online, but the EXP booster has been given anyways");
            }

            if(CheckPlayerData(player, args[1])){
                setData(player, args[1]);
            }
        }
        return false;
    }
    public boolean CheckOnline(Player player){
        for(Player pl : Bukkit.getOnlinePlayers()){
            if(player == pl){
                return true;
            }
        }
        return false;
    }

    public boolean CheckPlayerData(Player player, String newMult){
        String UUID = player.getUniqueId().toString();
        if(PlayerData.getConfig().contains("Players." + UUID)){

            double Multiplier = Double.parseDouble(PlayerData.getConfig().getString("Players." + UUID + ".Multiplier"));
            double configMult = ConfigFile.getConfig().getDouble("Multipliers." + newMult + ".Multiplier");

            if(Multiplier >= configMult){
                plugin.getLogger().log(Level.INFO, player.getName() + " Multiplier is already greater than the one selected.");
                return false;
            }
        }
        return true;
    }
    public void setData(Player player, String newMult){
        String UUID = player.getUniqueId().toString();
        String Multiplier = ConfigFile.getConfig().getString("Multipliers." + newMult + ".Multiplier");

        PlayerData.getConfig().set("Players." + UUID + ".Multiplier", Multiplier);
        PlayerData.getConfig().set("Players." + UUID + ".Name", player.getName());
        PlayerData.saveConfig();

        String Approved = ConfigFile.getConfig().getString("Applied-Message");
        if(Approved.contains("<Multiplier>")){
            Approved = Approved.replace("<Multiplier>", Multiplier);
        }
        Approved = ChatColor.translateAlternateColorCodes('&', Approved);
        player.sendMessage(Approved);
    }
}
