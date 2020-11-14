package me.MuchDan.Vanilla_EXP_Multiplier.Commands;

import me.MuchDan.Vanilla_EXP_Multiplier.Util.ConfigIO;
import me.MuchDan.Vanilla_EXP_Multiplier.Vanilla_EXP_Multiplier;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveExperienceMultiplier implements CommandExecutor {
    private Vanilla_EXP_Multiplier plugin;
    private ConfigIO ConfigFile;
    private ConfigIO PlayerData;

    public RemoveExperienceMultiplier(Vanilla_EXP_Multiplier plugin) {
        this.plugin = plugin;
        this.ConfigFile = plugin.getConfigFile();
        this.PlayerData = plugin.getBoostFile();
    }

    @Override
    public boolean onCommand(CommandSender Sender, Command cmd, String Label, String[] args) {
        if (Label.equalsIgnoreCase("removeMultiplier")) {
            String Permission = ConfigFile.getConfig().getString("Permission");
            if (!Sender.hasPermission(Permission)) {
                String Deny = ConfigFile.getConfig().getString("DenyMessage");
                Sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Deny));
                return false;
            }
            if (args.length < 1) {
                Sender.sendMessage(ChatColor.GREEN + "Command usage: /removeMultiplier [Username]");
                return false;
            }

            Player player = Bukkit.getPlayerExact(args[0]);

            if(CheckPlayerData(player)){
                Sender.sendMessage(ChatColor.GREEN + "Successfully removed " + player.getName() + "'s Multiplier.");
            }
            else{
                Sender.sendMessage(ChatColor.RED + "Failed to remove " + player.getName() + "'s Multiplier... You sure they have an active Multiplier?");
            }
        }
        return true;
    }

    public boolean CheckPlayerData(Player player) {
        String UUID = player.getUniqueId().toString();
        if (PlayerData.getConfig().contains("Players." + UUID)) {
            PlayerData.getConfig().set("Players." + UUID + ".Multiplier", null);
            PlayerData.getConfig().set("Players." + UUID + ".Name", null);
            PlayerData.getConfig().set("Players." + UUID, null);
            PlayerData.saveConfig();
            return true;
        }
        return false;
    }
}
