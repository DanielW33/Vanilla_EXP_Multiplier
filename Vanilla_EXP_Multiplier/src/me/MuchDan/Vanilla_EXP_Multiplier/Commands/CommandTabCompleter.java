package me.MuchDan.Vanilla_EXP_Multiplier.Commands;

import me.MuchDan.Vanilla_EXP_Multiplier.Util.ConfigIO;
import me.MuchDan.Vanilla_EXP_Multiplier.Vanilla_EXP_Multiplier;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandTabCompleter implements TabCompleter {
    private Vanilla_EXP_Multiplier plugin;
    private ConfigIO ConfigFile;

    public CommandTabCompleter(Vanilla_EXP_Multiplier plugin){
        this.plugin = plugin;
        ConfigFile = plugin.getConfigFile();
    }
    @Override
    public List<String> onTabComplete(CommandSender Sender, Command cmd, String label, String[] args){
        if(label.equalsIgnoreCase("setMultiplier")){
            List<String> Complete = new ArrayList<>();
            if(args.length == 1){
                for(Player player : Bukkit.getOnlinePlayers()){
                    Complete.add(player.getName());
                }
            }
            else if(args.length == 2){
                ConfigFile.getConfig().getConfigurationSection("Multipliers.").getKeys(false).forEach(var ->{
                    Complete.add(var);
                });
            }
            return Complete;
        }
        return null;
    }
}
