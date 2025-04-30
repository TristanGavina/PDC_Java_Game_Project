/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.java_game_project;
import java.io.*;


/**
 * This class handles and stores encounters for each stages
 * Have increasing enemies each stages
 * Asks the player if they want to continue, rest and quit after each fight
 * Keeping track of the last enemy defeated and remaining enemies to show when game is loaded again
 * @author trist
 * @param
 */
public class Encounter implements Serializable {
    private static final long serialVersionUID = 1L; //compatibility for other classes
    private final Player player;
    private Combat combat;
    private int stage;
    private int enemies;
    private final UserInputs input;
    private final GameUI ui;
    private EnemyType defeatedLast;
    
    private Enemy[] stageEnemy(int stage) {
        // Array of enemies per stage
        return switch (stage) {
            case 1 -> new Enemy[]{new Enemy(EnemyType.SLIME)};
            case 2 -> new Enemy[]{new Enemy(EnemyType.SLIME), new Enemy(EnemyType.GOBLIN)};
            case 3 -> new Enemy[]{new Enemy(EnemyType.SLIME), new Enemy(EnemyType.GOBLIN), new Enemy(EnemyType.ZOMBIE)};
            case 4 -> new Enemy[]{new Enemy(EnemyType.SLIME), new Enemy(EnemyType.GOBLIN), new Enemy(EnemyType.ZOMBIE), new Enemy(EnemyType.MONKEY)};
            case 5 -> new Enemy[]{new Enemy(EnemyType.SLIME), new Enemy(EnemyType.GOBLIN), new Enemy(EnemyType.ZOMBIE), new Enemy(EnemyType.MONKEY), new Enemy(EnemyType.LIZARDMAN), new Enemy(EnemyType.DEMON)};
            case 6 -> new Enemy[]{new Enemy(EnemyType.DRAGON)};
            default -> {
                System.out.println("INVALID STAGE!");
                yield new Enemy[0];  // Returns empty array if stage is invalid
            }
        };
    }
    
    public Encounter(Player player, UserInputs input, GameUI ui) {
        this.player = player;
        this.input = input;
        this.stage = 1;
        this.ui = ui;
        this.enemies = 0;
    }
    
    //encounter flow method
    public void encountered(){

        while(stage <= 6){
            //list of enemies for current stage
            Enemy[] setEnemy = stageEnemy(stage);
            //looping through remaining enemies to show when game resumed
            for (int i = enemies; i < setEnemy.length; i++) {
                enemies = i;
                Enemy enemy = setEnemy[i];
                //show current encounter
                ui.encounterMessage(player.getName(), enemy.getType());
                
                //storing last defeated for when resuming game
                setDefeatedLast(enemy.getType());

                //getting into a fight
                combat = new Combat(player, enemy, input, ui);
                combat.startCombat();
                ui.playerContinue(); //show continue menu
                
                try {
                    int choice = Integer.parseInt(input.getInput());
                    switch (choice) {
                        case 1 -> System.out.println("Continuing stage " + stage + "...");

                        case 2 -> {
                            //rest
                            ui.restingMenu();
                            playerResting();
                        }

                        case 3 -> {
                            //save and quit
                            enemies++;
                            Java_Game_Project.game = SaveHandler.game; // Make sure current game instance is used
                            SaveHandler.saveGame();
                            ui.savePlayerRecord(this, player);
                            ui.quitGame();
                            return;
                        }

                        default -> ui.invalidInput("Only chose 1-3");
                    }
                } catch (NumberFormatException e) {
                    ui.invalidInput(e.getMessage());
                }
            }
            
            System.out.println("All enemies in stage " + stage + " have been defeated!");
            System.out.println("Moving to next stage...");
            stage++;
            enemies = 0;
            if(stage <= 6){
                System.out.println("Welcome to stage: " + stage);
            }
        }
        ui.gameFinish();
    }
    
    public void playerResting(){
        try {
                    int choice = Integer.parseInt(input.getInput());
                    switch (choice) {
                        case 1 -> {
                            ui.savePlayerRecord(this, player);
                            System.out.println("Healed to full HP");
                            player.healToFull();
                            System.out.println("Continuing stage " + stage + "...");
                        }

                        case 2 -> {
                            //show player record
                            player.healToFull();
                            ui.savePlayerRecord(this, player);
                            ui.loadPlayerRecord();
                            
                        }

                        case 3 -> {
                            //save and quit
                            ui.savePlayerRecord(this, player);
                            enemies++;
                            Java_Game_Project.game = SaveHandler.game;
                            SaveHandler.saveGame();
                            ui.quitGame();
                            return;
                        }

                        default -> ui.invalidInput("Only chose 1-3");
                    }
                } catch (NumberFormatException e) {
                    ui.invalidInput(e.getMessage());
                }
        
    }
    
    //getters and setters
    public int getStage(){
        return stage;
    }
    public int getEnemies() {
        return enemies;
    }
    //set last defeated enemy
    public void setDefeatedLast(EnemyType type){
        this.defeatedLast = type;
    }
    //get last defeated enemy
    public EnemyType getDefeatedLast(){
        return defeatedLast;
    }
    public int getTotalEnemiesInStage() {
        return stageEnemy(stage).length;
    }
    public int getRemainingEnemies() {
        return getTotalEnemiesInStage() - enemies;
    }

}