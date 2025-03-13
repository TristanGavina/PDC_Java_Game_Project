/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.java_game_project;
/**
 * need to work on implementing FILE I/O
 * @author trist
 */

import com.mycompany.java_game_project.GameMenu.MainGame;
import com.mycompany.java_game_project.GameMenu.MainGame;
import java.util.Scanner;
    
public class Java_Game_Project {
    
    private Scanner scan;

    public Java_Game_Project() {
        
        scan = new Scanner(System.in);
        
        System.out.println("-------------------------");
        System.out.println("PDC CUI RPG game.");
        System.out.println("(1) Start New Game.");
        System.out.println("(2) Load Game");
        System.out.println("(3) Quit Game.");
        System.out.println("-------------------------");
        String s = scan.nextLine();
        
        try{
            int option = Integer.parseInt(s.trim());
            switch(option){
            case 1 -> new MainGame(scan).startGame();
            
            case 2 -> System.out.println("Invalid input!"); //new LoadGame().loadGame();
                
            case 3 -> quitGame();
            
            default -> System.out.println("Invalid input!");
            }   
        } catch(NumberFormatException e){
            System.out.println("Invalid input. Not an integer");
        }
    }
        
    private void quitGame(){
            System.out.println("Closing Game...!");
            System.exit(0);
        }
    
    public static void main(String[] args) {
        new Java_Game_Project();
    }
}

        
      

    
        
        
        
        //Enemy enemy = new Enemy(EnemyType.ZOMBIE);
        
         // Game loop for moving the player
            
        /**
        enemy.draw();
        
        player.attack(enemy);
        enemy.attack(player);
        
        /**
        System.out.println("Welcome Traveller to the CUI maze game");
        System.out.println("Please select:");
        System.out.println("(1) Attack");
        System.out.println("(2) Defend");
        System.out.println("(3) Heal");
        System.out.println("(4) Run");
        
        String s = scan.nextLine();
        */
