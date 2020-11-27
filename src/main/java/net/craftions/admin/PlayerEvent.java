/*
 * Copyright (c) 2020 Ben Siebert. All rights reserved.
 * File null.java Created on 26.11.2020
 */

package net.craftions.admin;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PlayerEvent implements Listener {

    public static ArrayList<String> muted = new ArrayList<String>();
    public static ArrayList<String> freezed = new ArrayList<String>();

    @EventHandler
    public void onClick(InventoryClickEvent e){
        try {
            if(e.getWhoClicked().hasPermission("craftions.admin.tools")){
                if(e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)){
                    // Skull s = (Skull) e.getCurrentItem();
                    SkullMeta s = (SkullMeta) e.getCurrentItem().getItemMeta();
                    if(s.getOwningPlayer().getName().equals(e.getWhoClicked().getName())){
                        e.getWhoClicked().openInventory(GUI.getPlayerMenu(Bukkit.getPlayer(s.getOwningPlayer().getName())));
                    }else {
                        System.out.println(s.getOwningPlayer().getName() + " != " + e.getWhoClicked().getName());
                    }
                    e.setCancelled(true);
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§4Bannen")){
                    Player p = Bukkit.getPlayer(e.getView().getTitle());
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + e.getView().getTitle() + " Du wurdest von einem Moderator gebannt!");
                    e.setCancelled(true);
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§cKicken")){
                    Player p = Bukkit.getPlayer(e.getView().getTitle());
                    p.kickPlayer("Du wurdest von einem Moderator gekickt!");
                    e.setCancelled(true);
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§cAbbrechen")){
                    if(e.getCurrentItem().getItemMeta().getLore().get(0).equals("§6Zurück zum Hauptmenü")){
                        e.getWhoClicked().openInventory(GUI.getMainMenu());
                    }else if(e.getCurrentItem().getItemMeta().getLore().get(0).equals("§6Zurück zum Spielermenü")){
                        e.getWhoClicked().openInventory(GUI.getPlayerMenu(Bukkit.getPlayer(e.getView().getTitle())));
                    }
                    e.setCancelled(true);
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§aBeobachten")){
                    Player p = Bukkit.getPlayer(e.getView().getTitle());
                    e.getWhoClicked().closeInventory();
                    e.getWhoClicked().setGameMode(GameMode.SPECTATOR);
                    e.getWhoClicked().teleport(p.getLocation());
                    e.getWhoClicked().sendMessage(AdminTools.prefix + "Du beobachtest nun §e" + p.getName());
                    e.setCancelled(true);
                }
                if(e.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS_PANE) && e.getCurrentItem().getItemMeta().getDisplayName().equals(" ")){
                    e.setCancelled(true);
                }
                if(e.getCurrentItem().getType().equals(Material.CHEST) && e.getCurrentItem().getItemMeta().getDisplayName().equals("§6Optionen")){
                    e.getWhoClicked().openInventory(GUI.getPlayerActionsMenu(Bukkit.getPlayer(e.getView().getTitle())));
                }
                if(e.getCurrentItem().getType().equals(Material.BARRIER) && e.getCurrentItem().getItemMeta().getDisplayName().equals("§cMuten")){
                    if(!muted.contains(e.getView().getTitle())){
                        muted.add(e.getView().getTitle());
                        e.getWhoClicked().sendMessage(AdminTools.prefix + "Du hast den Spieler §e" + e.getView().getTitle() + "§r erfolgreich gemutet!");
                        e.getWhoClicked().closeInventory();
                    }else {
                        muted.remove(e.getView().getTitle());
                        e.getWhoClicked().closeInventory();
                        e.getWhoClicked().sendMessage(AdminTools.prefix + "Du hast den Spieler §e" + e.getView().getTitle() + "§r erfolgreich entmutet!");
                        e.getWhoClicked().closeInventory();
                    }
                    e.setCancelled(true);
                }
                if(e.getCurrentItem().getType().equals(Material.PACKED_ICE) && e.getCurrentItem().getItemMeta().getDisplayName().equals("§bEinfrieren")){
                    if(!freezed.contains(e.getView().getTitle())){
                        freezed.add(e.getView().getTitle());
                        e.getWhoClicked().sendMessage(AdminTools.prefix + "Du hast den Spieler §e" + e.getView().getTitle() + "§r erfolgreich eingefriert!");
                        e.getWhoClicked().closeInventory();
                    }else {
                        freezed.remove(e.getView().getTitle());
                        e.getWhoClicked().closeInventory();
                        e.getWhoClicked().sendMessage(AdminTools.prefix + "Du hast den Spieler §e" + e.getView().getTitle() + "§r erfolgreich freigelegt!");
                        e.getWhoClicked().closeInventory();
                    }
                    e.setCancelled(true);
                }
            }
        }catch(NullPointerException ex){

        }
    }

    @EventHandler
    public void onChat(PlayerChatEvent e){
        if(muted.contains(e.getPlayer().getName())){
            e.setCancelled(true);
            e.getPlayer().sendMessage(AdminTools.prefix + "Du wurdest von einem Moderator stummgeschaltet!");
        }
    }

    @EventHandler
    public void onCommandProcess(PlayerCommandPreprocessEvent e){
        if((e.getMessage().startsWith("/msg") || e.getMessage().startsWith("/whisper") || e.getMessage().startsWith("/tell") || e.getMessage().startsWith("/say") && muted.contains(e.getPlayer().getName()))){
            e.setCancelled(true);
            e.getPlayer().sendMessage(AdminTools.prefix + "Du wurdest von einem Moderator stummgeschaltet!");
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if(freezed.contains(e.getPlayer().getName())){
            e.setCancelled(true);
        }
    }

    public static void save() throws IOException {
        System.out.println("Saving players...");
        File m = new File("./.muted");
        if(!m.exists()){
            try {
                m.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File f = new File("./.freezed");
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileWriter w = new FileWriter(m);
        for(String s : muted){
            w.write(s + "\n");
            System.out.println(s);
        }
        w.close();
        FileWriter w1 = new FileWriter(m);
        for(String s : freezed){
            w1.write(s + "\n");
            System.out.println(s);
        }
        w1.close();
    }

    public static void load() throws FileNotFoundException {
        // Muted
        File m = new File("./.muted");
        if(m.exists()){
            Scanner s = new Scanner(m);
            while(s.hasNextLine()){
                muted.add(s.nextLine());
            }
            s.close();
        }else {
            try {
                m.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Freezed
        File f = new File("./.freezed");
        if(f.exists()){
            Scanner s = new Scanner(f);
            while(s.hasNextLine()){
                freezed.add(s.nextLine());
            }
            s.close();
        }else {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
