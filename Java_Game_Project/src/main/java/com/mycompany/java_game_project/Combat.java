/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project;
import java.io.*;
/**
 * can do file i/o battle log
 * This class handles combat logic
 * @author trist
 */

public final class Combat implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean inCombat = false;
    private transient UserInputs input;
    private final Player player;
    private final Enemy currentEnemy;
    private transient GameUI ui;

    public Combat(Player player, Enemy currentEnemy, UserInputs input, GameUI ui) {
        this.input = input;
        this.player = player;
        this.currentEnemy = currentEnemy;
        this.inCombat = true;
        this.ui = ui;
        startCombat();

    }

    void startCombat() {

        while (inCombat) {

            ui.combatMenu(player, currentEnemy);
            try {
                int choice = Integer.parseInt(input.getInput());
                switch (choice) {
                    case 1: //attack
                        player.attack(currentEnemy);
                        break;
                    case 2: //defend
                        break;

                    case 3: //heal
                        break;

                    case 4: //run

                    default:
                        System.out.println("Invalid input!");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error" + e.getMessage());
            }

            //enemy attack player
            if (currentEnemy.getHealth() > 0) {
                currentEnemy.attack(player);
            }

            checkContinue();
        }
    }

    private void checkContinue() {
        if (currentEnemy.getHealth() <= 0) {
            inCombat = false;
            ui.combatMenu(player, currentEnemy);
        }
    }
    
    //Method for re-injecting transient dependencies after loading
    public void injectDependencies(UserInputs input, GameUI ui){
        this.input = input;
        this.ui = ui;
    }
}
