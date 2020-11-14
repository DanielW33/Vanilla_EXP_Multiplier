package me.MuchDan.Vanilla_EXP_Multiplier.Commands;

import me.MuchDan.Vanilla_EXP_Multiplier.Util.ConfigIO;
import me.MuchDan.Vanilla_EXP_Multiplier.Vanilla_EXP_Multiplier;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VerifyPlaceholder implements CommandExecutor {
    private Vanilla_EXP_Multiplier plugin;
    private ConfigIO PlayerFile;

    public VerifyPlaceholder(Vanilla_EXP_Multiplier plugin){
        this.plugin = plugin;
        this.PlayerFile = this.plugin.getBoostFile();
    }
    @Override
    public boolean onCommand(CommandSender Sender, Command cmd, String label, String[] args){
        if(label.equalsIgnoreCase("XPMultiplier")){
            Player player = (Player) Sender;
            String UUID = player.getUniqueId().toString();
            ConfigIO ConfigFile = plugin.getConfigFile();

            if(!PlayerFile.getConfig().contains("Players." + UUID)){
                player.sendMessage(ChatColor.RED + "You don't have any Xp Multiplier activated.");
            }
            else{
                String message = ConfigFile.getConfig().getString("xpmult_msg");
                message = ChatColor.translateAlternateColorCodes('&', message);

                String SendMessage = PlaceholderAPI.setPlaceholders(player, message);
                Sender.sendMessage(SendMessage);
            }
        }
        return true;
    }
}
