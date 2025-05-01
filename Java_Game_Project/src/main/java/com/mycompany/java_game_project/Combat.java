/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * can do file i/o battle log
 * This class handles combat logic
 * @author trist
 */

public class Combat implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean inCombat = false;
    private final UserInputs input;
    private final Player player;
    private final Enemy currentEnemy;
    private final GameUI ui;
    

    public Combat(Player player, Enemy currentEnemy, UserInputs input, GameUI ui) {
        this.input = input;
        this.player = player;
        this.currentEnemy = currentEnemy;
        this.inCombat = true;
        this.ui = ui;

    }

    public void startCombat() {

        while (inCombat) {
            
            ui.combatMenu(player, currentEnemy);
            try {
                int choice = Integer.parseInt(input.getInput());
                switch (choice) {
                    case 1 -> {
                        //attack
                        int damageDealt = player.attack(currentEnemy);
                        ui.playerAttack(player.getName(), player.getAttack());
                        ui.enemyTakeDamage(currentEnemy.getType(), damageDealt, currentEnemy.getHealth());
                    }
                    case 2 -> {
                        //defend
                        if (!player.isDefending()) {
                            player.defend();
                            ui.combatMenu(player, currentEnemy);
                            ui.playerDefend(player.getName());
                        } else {
                            ui.invalidInput(player.getName() + " is already defending!");
                        }
                    }

                    case 3 -> {
                        //heal
                        if(player.getHealth() == player.getMaxHP()){
                            ui.invalidInput(player.getName() + " is full HP.");
                        } else {
                            player.heal();
                            ui.playerHeal(player.getName());
                        }
                    }

                    case 4 -> {
                        ui.helpFile();
                        input.getInput();
                        continue; //makes enemies not attack / skip turn
                    }
                    default -> ui.invalidInput("Choose only 1 - 4!  Enemy gets a free move.");
                }
            } catch (NumberFormatException e) {
                ui.invalidInput(e.getMessage() + " Enemy gets a free move.");
            }

            //enemy attack player
            if (currentEnemy.getHealth() > 0) {
                int enemyAttack = currentEnemy.attack(player);
                ui.enemyAttack(currentEnemy.getType(), enemyAttack);
                ui.playerTakeDamage(player.getName(), enemyAttack, player.getHealth());
                
            }
            //ui.savePlayerRecord(encounter, player);
            player.defendEnd();
            checkContinue();
        }
    }

    private void checkContinue() {
        if (currentEnemy.getHealth() <= 0) {
            inCombat = false;
        }
    }
}
