/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project;

/**
 * file i/o battle log
 *
 * @author trist
 */
import java.util.Scanner;

public final class Combat {

    private boolean inCombat = false;
    private final Scanner scan;
    private final GameObjects player;
    private final Enemy currentEnemy;

    public Combat(GameObjects player, Enemy currentEnemy) {
        scan = new Scanner(System.in);
        this.player = player;
        this.currentEnemy = currentEnemy;
        this.inCombat = true;
        startCombat();

    }

    void startCombat() {

        while (inCombat) {

            menu();
            try {
                int choice = Integer.parseInt(scan.nextLine().trim());
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
            menu();
        }
    }

    public void menu() {
        System.out.print("""
            _____________________________________________
           |                                             |
           |              CHOOSE ACTION!!!               |
           |_____________________________________________|
           |                                             |
           |       Press [1] ATTACK                      |
           |                                             |
           |       Press [2] DEFEND                      |
           |                                             |
           |       Press [3] HEAL                        |
           |                                             |
           |       Press [4] RUN                         |
           |_____________________________________________|
           |=============================================|
           """);
        if (player.getHealth() <= 0) {
            System.out.println("|       YOU HAVE BEEN KILLED!           |");
        } else {
            System.out.println(player.draw());
        }

        System.out.println("|_____________________________________________|");

        if (currentEnemy.getHealth() <= 0) {
            System.out.println("|   ENEMY: " + currentEnemy.type + " DEFEATED!");
        } else {
            System.out.println(currentEnemy.draw());
        }

        System.out.println("""
                           |=============================================|
                           |_____________________________________________|\n
                           """);
    }
}
