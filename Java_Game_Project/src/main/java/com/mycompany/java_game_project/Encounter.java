/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project;

import java.util.Scanner;


/**
 *
 * @author trist
 */
public class Encounter {
    private final Player player;
    private Combat combat;
    private int stage;
    private final Scanner cont;
    
    private final Enemy[] stage1Enemies = new Enemy[]{
        new Enemy(EnemyType.SLIME),
    };
    
    private final Enemy[] stage2Enemies = new Enemy[]{
        new Enemy(EnemyType.SLIME),
        new Enemy(EnemyType.GOBLIN),
    };
    
    private final Enemy[] stage3Enemies = new Enemy[]{
        new Enemy(EnemyType.SLIME),
        new Enemy(EnemyType.GOBLIN),
        new Enemy(EnemyType.ZOMBIE)
    };
    
    private final Enemy[] stage4Enemies = new Enemy[]{
        new Enemy(EnemyType.SLIME),
        new Enemy(EnemyType.GOBLIN),
        new Enemy(EnemyType.ZOMBIE),
        new Enemy(EnemyType.MONKEY)
    };
    
    private final Enemy[] stage5Enemies = new Enemy[]{
        new Enemy(EnemyType.SLIME),
        new Enemy(EnemyType.GOBLIN),
        new Enemy(EnemyType.ZOMBIE),
        new Enemy(EnemyType.MONKEY),
        new Enemy(EnemyType.LIZARDMAN),
        new Enemy(EnemyType.DEMON)
        
    };
    
    private final Enemy[] bossStage = new Enemy[]{
        new Enemy(EnemyType.DRAGON)
    };
    
    public Encounter(Player player, Scanner cont) {
        this.player = player;
        this.cont = cont;
        this.stage = 1;
    }
    
    public void encountered(){

        while(stage <= 6){
            Enemy[] setEnemy = stageEnemy(stage);
        
            for (Enemy enemy : setEnemy){
                encounterMessage(enemy);
                combat = new Combat(player, enemy);
                combat.startCombat();
                
            //Print after defeating enemy
                System.out.println("""
                                   Would you like to:
                                      [1] CONTINUE
                                      [2] REST
                                      [3] QUIT AND SAVE
                                   """);
            
            try {
                int choice = Integer.parseInt(cont.nextLine().trim());
                switch(choice){
                    case 1:
                        System.out.println("Continuing stage...");
                        break;

                    case 2:
                        System.out.println("Healing to full HP...");
                        System.out.println("implement full heal");
                        break;

                    case 3:
                        System.out.println("SAVING DONT CLOSE...");
                        Java_Game_Project.quitGame();

                    default:
                        System.out.println("Invalid input");
                    }
                }catch(NumberFormatException e){
                System.out.println("There was an error " +e.getMessage());
                }
            }
            
            System.out.println("All enemies in stage " + stage + " have been defeated!");
            System.out.println("Moving to next stage...");
            stage++;
            if(stage <= 6){
                System.out.println("Welcome to stage: " + stage);
            }
            
        }
    }
    
    
    private Enemy[] stageEnemy(int stage) {
        switch (stage) {
            case 1 -> {
                return stage1Enemies;
            }
            case 2 -> {
                return stage2Enemies;
            }
            case 3 -> {
                return stage3Enemies;
            }
            case 4 -> {
                return stage4Enemies;
            }
            case 5 -> {
                return stage5Enemies;
            }
            case 6 -> {
                return bossStage;
            }
            default -> {
                System.out.println("INVALID STAGE!");
                return new Enemy[0];
            }
        }
    }
    
    private void encounterMessage(Enemy enemy){ // shows 
        String encounter = player.getName() + " has encountered an " + enemy.getType() + "!";

        int boxWidth = 45;
        int totalPadding = boxWidth - encounter.length();
        int leftPadding = totalPadding / 2;
        int rightPadding = totalPadding - leftPadding;

        System.out.println("\n|=============================================|");
        System.out.println("|                                             |");
        System.out.println("|" + " ".repeat(leftPadding) + encounter + " ".repeat(rightPadding) + "|");
    }
    
    
}