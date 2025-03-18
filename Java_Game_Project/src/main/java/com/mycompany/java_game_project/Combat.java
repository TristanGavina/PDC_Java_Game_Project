/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project;


/**
 *
 * @author trist
 */
import java.util.Scanner;

public final class Combat {
    private boolean fighting;
    private final Scanner scan;
    private final Player player;
    private final Enemy currentEnemy;
    
            
    public Combat(Player player, Enemy currentEnemy){
        scan = new Scanner(System.in);
        this.player = player;
        this.currentEnemy = currentEnemy;
        this.fighting = true;
        
    }
    
    void startCombat(){
        System.out.println(player.getName() + " has encountered an " + currentEnemy.getType());
        System.out.println("-------------------------");
        System.out.println("Choose!");
        System.out.println("(1) Attack");
        System.out.println("(2) Defend");
        System.out.println("(3) Heal");
        System.out.println("(4) Run");
        System.out.println("-------------------------");
        String s = scan.nextLine();
        while(fighting){
            try{
                int option = Integer.parseInt(s.trim());
                switch(option){
                case 1: //attack
                     player.attack(currentEnemy);
                            if (currentEnemy.getHealth() <= 0) {
                                System.out.println("-----You defeated the " + currentEnemy.getType() + "!-----");
                                fighting = false;
                                break;
                            }

                case 2: //defend
                    break;

                case 3: //heal
                    break;

                case 4: //run

                default:
                    System.out.println("Invalid input!");
                    break;
                }   
            } catch(NumberFormatException e){
                System.out.println("Invalid input. Not an integer");
            }
        }
        
        
        //enemy attack player
        currentEnemy.attack(player);
    }
    public static void main(String[] args) {
        
    }
}
