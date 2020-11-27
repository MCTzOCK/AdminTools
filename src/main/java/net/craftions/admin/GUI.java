/*
 * Copyright (c) 2020 Ben Siebert. All rights reserved.
 * File null.java Created on 26.11.2020
 */

package net.craftions.admin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class GUI {

    public static Inventory getMainMenu(){
        Inventory inv = Bukkit.createInventory(null, 54, "§4§lADMIN | §2§lHAUPTMENÜ");
        drawEmpty(inv, 0, 9);
        // Integer bottom_to = Bukkit.getOnlinePlayers().size() > 30 ? 52 : 53;
        drawEmpty(inv, 45, 54);
        /*if(bottom_to == 53){
            ItemStack next = new ItemStack(Material.MAGENTA_GLAZED_TERRACOTTA);
            ItemMeta nM = next.getItemMeta();
            nM.setDisplayName("§cNächste Seite");
            next.setItemMeta(nM);
            inv.setItem(53, next);
        }*/
        Integer c = 0;
        for(Player p : Bukkit.getOnlinePlayers()){
            if(c != 30){
                ItemStack head = getHead(p);
                ItemMeta hM = head.getItemMeta();
                hM.setDisplayName(ChatColor.YELLOW + p.getName());
                head.setItemMeta(hM);
                inv.addItem(head);
            }else {
                break;
            }
            c++;
        }
        c = 0;
        return inv;
    }

    public static Inventory getPlayerMenu(Player player){
        Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST, player.getName());
        drawEmpty(inv, 0, 9);
        drawEmpty(inv, 18, 27);
        ItemStack ban = new ItemStack(Material.RED_CONCRETE);
        ItemStack kick = new ItemStack(Material.YELLOW_CONCRETE);
        // ItemStack spec = new ItemStack(Material.GREEN_CONCRETE);
        ItemStack cancel = new ItemStack(Material.MAGENTA_GLAZED_TERRACOTTA);
        ItemStack actions = new ItemStack(Material.CHEST);

        ItemMeta banM = ban.getItemMeta();
        ItemMeta kickM = kick.getItemMeta();
        // ItemMeta specM = spec.getItemMeta();
        ItemMeta cancelM = cancel.getItemMeta();
        ItemMeta actionsM = actions.getItemMeta();

        banM.setDisplayName("§4Bannen");
        kickM.setDisplayName("§cKicken");
        // specM.setDisplayName("§aBeobachten");
        cancelM.setDisplayName("§cAbbrechen");
        actionsM.setDisplayName("§6Optionen");
        List<String> cancelLore = new ArrayList<String>();
        cancelLore.add("§6Zurück zum Hauptmenü");
        cancelM.setLore(cancelLore);

        ban.setItemMeta(banM);
        kick.setItemMeta(kickM);
        // spec.setItemMeta(specM);
        cancel.setItemMeta(cancelM);
        actions.setItemMeta(actionsM);

        inv.setItem(10, ban);
        inv.setItem(12, kick);
        // inv.setItem(14, spec);
        inv.setItem(14, actions);
        inv.setItem(16, cancel);
        return inv;
    }

    public static Inventory getPlayerActionsMenu(Player player){
        Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST, player.getName());
        drawEmpty(inv, 0, 9);
        drawEmpty(inv, 18, 27);

        ItemStack spec = new ItemStack(Material.ENDER_EYE);
        ItemStack mute = new ItemStack(Material.BARRIER);
        ItemStack freeze = new ItemStack(Material.PACKED_ICE);
        ItemStack cancel = new ItemStack(Material.MAGENTA_GLAZED_TERRACOTTA);

        ItemMeta specM = spec.getItemMeta();
        ItemMeta muteM = mute.getItemMeta();
        ItemMeta freezeM = freeze.getItemMeta();
        ItemMeta cancelM = cancel.getItemMeta();
        cancelM.setDisplayName("§cAbbrechen");
        List<String> cancelLore = new ArrayList<String>();
        cancelLore.add("§6Zurück zum Spielermenü");
        cancelM.setLore(cancelLore);

        specM.setDisplayName("§aBeobachten");
        muteM.setDisplayName("§cMuten");
        freezeM.setDisplayName("§bEinfrieren");

        spec.setItemMeta(specM);
        mute.setItemMeta(muteM);
        freeze.setItemMeta(freezeM);
        cancel.setItemMeta(cancelM);

        inv.setItem(11, spec);
        inv.setItem(13, mute);
        inv.setItem(15, freeze);
        inv.setItem(26, cancel);
        return inv;
    }


    public static ItemStack getHead(Player player){
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1 , (short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(player.getName());
        item.setItemMeta(meta);
        return item;
    }

    public static void drawEmpty(Inventory inv, Integer min, Integer max){
        for(int i = min; i < max; i++){
            ItemStack pH = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            ItemMeta pM = pH.getItemMeta();
            pM.setDisplayName(" ");
            pH.setItemMeta(pM);
            inv.setItem(i, pH);
        }
    }
}
