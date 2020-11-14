package me.MuchDan.Vanilla_EXP_Multiplier.Commands;

import me.MuchDan.Vanilla_EXP_Multiplier.Util.ConfigIO;
import me.MuchDan.Vanilla_EXP_Multiplier.Vanilla_EXP_Multiplier;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadConfig implements CommandExecutor {
    private Vanilla_EXP_Multiplier plugin;
    private ConfigIO ConfigFile;
    private ConfigIO PlayerData;

    public ReloadConfig(Vanilla_EXP_Multiplier plugin) {
        this.plugin = plugin;
        ConfigFile = this.plugin.getConfigFile();
        PlayerData = this.plugin.getBoostFile();
    }

    @Override
    public boolean onCommand(CommandSender Sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("MultiplierReload")) {

            String Permission = ConfigFile.getConfig().getString("Permission");

            if (!Sender.hasPermission(Permission)) {

                String Deny = ConfigFile.getConfig().getString("DenyMessage");
                Sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Deny));
                return false;

            }
            ConfigFile.reloadConfig();
            PlayerData.reloadConfig();
            Sender.sendMessage(ChatColor.GREEN + "Successfully reloaded Config files!");
        }
        return true;
    }
}
