/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project.GameUI;

import com.mycompany.java_game_project.Interfaces.ICombatMenu;
import com.mycompany.java_game_project.Encounter;
import com.mycompany.java_game_project.Enemy;
import com.mycompany.java_game_project.Player;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author trist
 */
public class CombatMenu extends JPanel implements Serializable, ICombatMenu {
    private static final long serialVersionUID = 1L;
    private static final String COMBATMENU_PATH = "GameUI_text_files/combatMenu.txt";
    
    //Combat menu
    @Override
    public void displayCombatMenu(Player player, Enemy currentEnemy) {
        try (BufferedReader br = new BufferedReader(new FileReader(COMBATMENU_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading playerContinue menu: " + e.getMessage());
        }
        if (player.isDefending()) {
            String defDisplay = player.getDefense() + (player.isDefending() ? "*" : "");
            System.out.println("| Player: " + player.getName() + "\n| HP: " + player.getHealth() + " | DEF: " + defDisplay + " | ATTACK: " + player.getAttack());
            System.out.println("|_____________________________________________|");
        } else {
            System.out.println(player.draw());
            System.out.println("|_____________________________________________|");
        }
        if (currentEnemy.getHealth() <= 0) {
            System.out.println("|   ENEMY: " + currentEnemy.type + " DEFEATED!");
        } else {
            System.out.println(currentEnemy.draw());
        }
        System.out.println("|=============================================|\n|_____________________________________________|\n\n");
    }
    
    
}
