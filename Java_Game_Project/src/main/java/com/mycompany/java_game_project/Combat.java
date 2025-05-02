/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project;
import com.mycompany.java_game_project.GameUI.CombatLog;
import com.mycompany.java_game_project.GameUI.GameUI;
import com.mycompany.java_game_project.GameUI.GuideAndDetails;
import com.mycompany.java_game_project.GameUI.InvalidHandler;
import java.io.*;
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
    private final Turns turn;
    private final GameUI ui;
    private final GuideAndDetails gd;
    private final InvalidHandler ih;
    

    public Combat(Player player, Enemy currentEnemy, UserInputs input, GameUI ui, GuideAndDetails gd, InvalidHandler ih, CombatLog log) {
        this.input = input;
        this.player = player;
        this.currentEnemy = currentEnemy;
        this.inCombat = true;
        this.ui = ui;
        this.gd = gd;
        this.ih = ih;
        this.turn = new Turns(log);
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
                        turn.playerAttack(player.getName(), player.getAttack());
                        turn.enemyTakeDamage(currentEnemy.getType(), damageDealt, currentEnemy.getHealth());
                    }
                    case 2 -> {
                        //defend
                        if (!player.isDefending()) {
                            player.defend();
                            ui.combatMenu(player, currentEnemy);
                            turn.playerDefend(player.getName());
                        } else {
                            ih.invalidInput(player.getName() + " is already defending!");
                        }
                    }

                    case 3 -> {
                        //heal
                        if(player.getHealth() == player.getMaxHP()){
                            ih.invalidInput(player.getName() + " is full HP.");
                        } else {
                            player.heal();
                            turn.playerHeal(player.getName());
                        }
                    }

                    case 4 -> {
                        gd.showGameDetails();
                        input.getInput();
                        continue; //makes enemies not attack / skip turn
                    }
                    default -> ih.invalidInput("Choose only 1 - 4!  Enemy gets a free move.");
                }
            } catch (NumberFormatException e) {
                ih.invalidInput(e.getMessage() + ": Enemy gets a free move.");
            }

            //enemy attack player
            if (currentEnemy.getHealth() > 0) {
                int enemyAttack = currentEnemy.attack(player);
                turn.enemyAttack(currentEnemy.getType(), enemyAttack);
                turn.playerTakeDamage(player.getName(), enemyAttack, player.getHealth());
                
            }
            player.defendEnd();
            checkContinue();
        }
    }

    private void checkContinue() {
        if (currentEnemy.getHealth() <= 0) {
            inCombat = false;
            ui.combatMenu(player, currentEnemy);
        }
        if (player.getHealth() <= 0){
            ui.gameOver();
        }
    }
}
