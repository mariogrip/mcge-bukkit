package com.mariogrip.mcge;

import com.mariogrip.mcge.quarry.quarryUtils;

import com.turt2live.hurtle.uuid.UUIDUtils;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class mcge extends JavaPlugin {

  public quarryUtils quarry;
    public static mcge thi;

    @Override
    public void onEnable() {
    	getLogger().info("MCGE is starting...");
    	quarry = new quarryUtils();
    	thi = this;
        //saveDefaultConfig(); TODO THERE IS NO CONFIG
        //reloadPlugin(); TODO THERE IS NO CONFIG
    	getLogger().info("Connecting to the server...");
    	if (quarry.checkCon()==true){
        	getLogger().info("Connected!");
    	}else{
        	getLogger().severe("Cannot connect, Please check your conection....");
            getLogger().severe("MCGE IS DISABLED");
            getServer().getPluginManager().disablePlugin(this);
            return;
    	}
    	
        if (!getServer().getOnlineMode()) {
            getLogger().severe("Your server is in offline mode, This plugin require online mode");
            getLogger().severe("MCGE IS DISABLED");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        try {
            if (OfflinePlayer.class.getMethod("getUniqueId") == null) {
                throw new NoSuchMethodException();
            }
        } catch (NoSuchMethodException e) {
            getLogger().severe("Your server version is not supported! (error: 03)");
            getLogger().severe("MCGE IS DISABLED");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
     
    	getLogger().info("MCGE is ready.");
    }

    @Override
    public void onDisable() {
        thi = null;
    }

    @Override
    public boolean onCommand(final CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("money")) {
            if (args.length < 1) {
                if (sender.hasPermission("mcge.balance")) {
                    sendMessage(sender, "You have " + quarry.getBalance(UUIDUtils.getUUID(sender).toString()) + " M$");
                } else sendMessage(sender, ChatColor.RED + "No permission.");
            } else {
                String argName = null;
                double argAmount = 0;
                if (args.length >= 3) {
                    argName = args[1];
                    Player online = getServer().getPlayer(argName);
                    if (online != null) {
                        argName = online.getName();
                    }
                    try {
                        argAmount = Double.parseDouble(args[2]);
                    } catch (NumberFormatException e) {
                    }
                }
                if (args[0].equalsIgnoreCase("send") || args[0].equalsIgnoreCase("pay")) {
                    if (!sender.hasPermission("mcge.send")) sendMessage(sender, ChatColor.RED + "No permission");
                    else {
                        if (argName == null)
                            sendMessage(sender, ChatColor.RED + "Incorrect syntax. Try " + ChatColor.YELLOW + "/money pay <player> <amount> <password>");
                        else {
                            if (argAmount > 0) {
                                if (quarry.getBalance_double(UUIDUtils.getUUID(sender).toString()) >= argAmount) {
                                	String argAmountString = String.valueOf(argAmount);
                                    quarry.sendCoins(UUIDUtils.getUUID(sender).toString(), args[3], argAmountString, UUIDUtils.getUUID(argName).toString());
                                    sendMessage(sender, ChatColor.GREEN + "You have send " + argName + " " + argAmount + " M$");
                                    Player payee = getServer().getPlayerExact(argName);
                                    if (payee != null) {
                                        sendMessage(payee, ChatColor.GREEN + sender.getName() + " has send " + argAmount + " M$");
                                    }
                                } else sendMessage(sender, ChatColor.RED + "You do not have enough!");
                            } else
                                sendMessage(sender, ChatColor.RED + "You must provide a positive, non-zero, number!");
                        }
                    }
                } else if (args[0].equalsIgnoreCase("help")) {
                    sendMessage(sender, ChatColor.AQUA + "/money - Shows your current balance");
                    sendMessage(sender, ChatColor.AQUA + "/money <playername> - Shows <playername>'s balance");
                    sendMessage(sender, ChatColor.AQUA + "/money send <player> <amount> <password> - sends <player> <amount> money");
                    sendMessage(sender, ChatColor.AQUA + "/money reload - Reloads the configuration");
                    sendMessage(sender, ChatColor.AQUA + "/money check - Check if the server is talking with MCGE");
                    sendMessage(sender, ChatColor.AQUA + "/money getcoins - get some coins to test with (only if the balance if smaller then 0)");
               
                } else if (args[0].equalsIgnoreCase("check")) {
                	if (quarry.checkCon()){
                		sender.sendMessage("You're connected to MCGE servers (All good ;)");
                	}else{
                		sender.sendMessage(ChatColor.RED+"You're not connected to MCGE server! (Not OK!)");
                	}
                } else if (args[0].equalsIgnoreCase("getcoins")) {
                	sender.sendMessage("http://mariogrip.com/mcge?user="+UUIDUtils.getUUID(sender).toString());
                } else if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
                    if (sender.hasPermission("mcge.reload")) {
                        reloadPlugin();
                        sendMessage(sender, ChatColor.GREEN + "Reloaded!");
                    } else sendMessage(sender, ChatColor.RED + "No permission.");

                    } else {
                    // Assume player lookup
                    if (sender.hasPermission("mcge.balance.others")) {
                        sendMessage(sender, args[0] + " has " + quarry.getBalance(UUIDUtils.getUUID(args[0]).toString()));
                    } else sendMessage(sender, ChatColor.RED + "No permission.");
                }
            }
        } else {
            sendMessage(sender, ChatColor.RED + "Something broke.");
        }
        return true;
    }

    private void reloadPlugin() {
        reloadConfig();
    }


    public void sendMessage(CommandSender sender, String message) {
        sender.sendMessage((ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix", ChatColor.GRAY + "[MCGE]")) + " " + ChatColor.WHITE + message).trim());
    }

    public int getDecimalPlaces() {
        int v = getConfig().getInt("advanced.decimal-places", 2);
        if (v < 0) return 0;
        return v;
    }

    public double round(double v, int n) {
        return new BigDecimal(v).setScale(n, RoundingMode.HALF_EVEN).doubleValue();
    }
}
