/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.java_game_project;
import java.io.*;


/**
 * This class handles and stores encounters for each stages
 * @author trist
 */
public class Encounter implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Player player;
    private Combat combat;
    private int stage;
    private int enemies;
    private final UserInputs input;
    private final GameUI ui;
    private EnemyType defeatedLast;
    
    private Enemy[] stageEnemy(int stage) {
        // Dynamically create enemies for the current stage
        return switch (stage) {
            case 1 -> new Enemy[]{new Enemy(EnemyType.SLIME, ui)};
            case 2 -> new Enemy[]{new Enemy(EnemyType.SLIME, ui), new Enemy(EnemyType.GOBLIN, ui)};
            case 3 -> new Enemy[]{new Enemy(EnemyType.SLIME, ui), new Enemy(EnemyType.GOBLIN, ui), new Enemy(EnemyType.ZOMBIE, ui)};
            case 4 -> new Enemy[]{new Enemy(EnemyType.SLIME, ui), new Enemy(EnemyType.GOBLIN, ui), new Enemy(EnemyType.ZOMBIE, ui), new Enemy(EnemyType.MONKEY, ui)};
            case 5 -> new Enemy[]{new Enemy(EnemyType.SLIME, ui), new Enemy(EnemyType.GOBLIN, ui), new Enemy(EnemyType.ZOMBIE, ui), new Enemy(EnemyType.MONKEY, ui), new Enemy(EnemyType.LIZARDMAN, ui), new Enemy(EnemyType.DEMON, ui)};
            case 6 -> new Enemy[]{new Enemy(EnemyType.DRAGON, ui)};
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
    
    public void encountered(){

        while(stage <= 6){
            Enemy[] setEnemy = stageEnemy(stage);
        
            for (int i = enemies; i < setEnemy.length; i++) {
                enemies = i;
                Enemy enemy = setEnemy[i];
                ui.encounterMessage(player.getName(), enemy.getType());
                combat = new Combat(player, enemy, input, ui);
                combat.startCombat();
                setDefeatedLast(enemy.getType());

                
                ui.playerContinue();
                
                try {
                    int choice = Integer.parseInt(input.getInput());
                    switch (choice) {
                        case 1 -> System.out.println("Continuing stage " + stage + "...");

                        case 2 -> {
                            //rest
                            System.out.println("Healing to full HP...");
                            System.out.println("implement full heal");
                        }

                        case 3 -> {
                            //save and quit
                            enemies++;
                            Java_Game_Project.game = SaveHandler.game; // Make sure current game instance is used
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
            
            System.out.println("All enemies in stage " + stage + " have been defeated!");
            System.out.println("Moving to next stage...");
            stage++;
            enemies = 0;
            if(stage <= 6){
                System.out.println("Welcome to stage: " + stage);
            }
        }
    }
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