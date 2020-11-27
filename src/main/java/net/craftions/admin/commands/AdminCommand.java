/*
 * Copyright (c) 2020 Ben Siebert. All rights reserved.
 * File null.java Created on 26.11.2020
 */

package net.craftions.admin.commands;

import net.craftions.admin.GUI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("You need to be a player!");
            return true;
        }
        if(args.length == 0){
            ((Player) sender).openInventory(GUI.getMainMenu());
        }else if(args.length == 1){
            ((Player) sender).openInventory(GUI.getPlayerMenu(Bukkit.getPlayer(args[0])));
        }
        return true;
    }

}
