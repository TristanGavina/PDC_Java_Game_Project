/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project.GameMenu;
import com.mycompany.java_game_project.Stages.Stage1;
import com.mycompany.java_game_project.Enemy;
import com.mycompany.java_game_project.EnemyType;
import com.mycompany.java_game_project.GameObjects;
import com.mycompany.java_game_project.Player;

import java.util.Scanner;

/**
 * FILE I/O will be text file for tutorial/help, inventory system, load/save(?), logging(debug)
 * MainGame contains Stages contains Treasures and Enemies
 * @author trist
 */
public class MainGame {
    
    private Scanner scan;
    public MainGame(Scanner scan) {
        this.scan = scan;
    }
    
    
    public void startGame(){
        System.out.println("-------------------------");
        System.out.println("Welcome to Tristan PDC project 1!");
        System.out.println("A turn-based game with the goal to defeat the final boss");
        System.out.println("-------------------------");
        
        System.out.println("\nPlease enter name: ");
        String name = scan.nextLine();
        Player player = new Player(name);
        System.out.println("Welcome to the first stage " + player.name + ".");
        System.out.println("KAWASAKI\nKRICO\nESTRIPER");
        System.out.println("");
        }

}
//        GameObjects zombie = new Enemy(EnemyType.ZOMBIE);
//        GameObjects slime = new Enemy(EnemyType.SLIME);
//        System.out.println("You encountered a ");
//        zombie.draw();
//        slime.draw();