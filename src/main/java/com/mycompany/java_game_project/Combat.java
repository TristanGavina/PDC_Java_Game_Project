/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project;
import com.mycompany.java_game_project.Interfaces.ICombatLog;
import com.mycompany.java_game_project.Interfaces.ICombatMenu;
import com.mycompany.java_game_project.Interfaces.IEndGame;
import com.mycompany.java_game_project.Interfaces.IGameDetails;
import com.mycompany.java_game_project.Interfaces.IInvalidHandler;
import com.mycompany.java_game_project.Interfaces.IUserInputs;
import java.io.*;
/**
 * 
 * This class handles combat logic
 * @author trist
 */

public class Combat implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final Enemy currentEnemy;
    private final Player player;
    private boolean inCombat = false;
    private final ICombatMenu cm;
    private final IEndGame eg;
    private final IGameDetails gd;
    final IInvalidHandler ih;
    final IUserInputs input;
    private final Turns turn;
    
    
    public Combat(Player player,
            Enemy currentEnemy, 
            IUserInputs input,
            IEndGame eg,
            IGameDetails gd,
            IInvalidHandler ih, 
            ICombatLog log, 
            ICombatMenu cm) {
        
        this.input = input;
        this.player = player;
        this.currentEnemy = currentEnemy;
        this.inCombat = true;
        this.eg = eg;
        this.gd = gd;
        this.ih = ih;
        this.turn = new Turns(log);
        this.cm = cm;
    }

    public void startCombat() {
        while (inCombat) {
            cm.displayCombatMenu(player, currentEnemy);
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
                            cm.displayCombatMenu(player, currentEnemy);
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
                        gd.displayGameDetails();
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
            cm.displayCombatMenu(player, currentEnemy);
        }
        if (player.getHealth() <= 0){
            eg.displayGameOver();
            inCombat = false;
        }
    }
}
