/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.java_game_project;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    
    //store enemy type by stage
    private final Map<Integer, ArrayList<EnemyType>> stageEnemies = new HashMap<>();
        
    public Encounter(Player player, UserInputs input, GameUI ui) {
        this.player = player;
        this.input = input;
        this.stage = 1;
        this.ui = ui;
        this.enemies = 0;
        
        enemyStages();
    }
    
    //initializing enemy types per stages
    private void enemyStages() {
        //enemy type and what stage they belong to using map and list
        //new ArrayList<>() for future if want to expand/remove types
        stageEnemies.put(1, new ArrayList<>(List.of(EnemyType.SLIME)));
        stageEnemies.put(2, new ArrayList<>(List.of(EnemyType.SLIME, EnemyType.GOBLIN)));
        stageEnemies.put(3, new ArrayList<>(List.of(EnemyType.SLIME, EnemyType.GOBLIN, EnemyType.ZOMBIE)));
        stageEnemies.put(4, new ArrayList<>(List.of(EnemyType.SLIME, EnemyType.GOBLIN, EnemyType.ZOMBIE, EnemyType.MONKEY)));
        stageEnemies.put(5, new ArrayList<>(List.of(EnemyType.SLIME, EnemyType.GOBLIN, EnemyType.ZOMBIE, EnemyType.MONKEY, EnemyType.LIZARDMAN)));
        stageEnemies.put(6, new ArrayList<>(List.of(EnemyType.DEMON, EnemyType.DRAGON)));
    }
    
    // converting EnemyTypes to actual Enemy to fight in the stage
    private ArrayList<Enemy> stageEnemy(int stage) {
        ArrayList<EnemyType> types = stageEnemies.get(stage);
        if (types == null) {
            ui.invalidInput("Invalid stage!");
            return new ArrayList<>();
        }
        
        ArrayList<Enemy> enemy = new ArrayList<>();
        //looping through enemy type from map and creating new enemy to list
        for (EnemyType type : types) {
            enemy.add(new Enemy(type));
        }
        return enemy;
    }
    
    //encounter flow method
    public void encountered(){
        while(stage <= 6){
            //list of enemies for current stage
            ArrayList<Enemy> setEnemy = stageEnemy(stage);
            for (int i = enemies; i < setEnemy.size(); i++) {
                enemies = i;
                Enemy enemy = setEnemy.get(i);
                //show current encounter
                ui.encounterMessage(player.getName(), enemy.getType());
                //storing last defeated for when resuming game
                setDefeatedLast(enemy.getType());
                //getting into a fight
                combat = new Combat(player, enemy, input, ui);
                combat.startCombat();
                ui.playerContinue(); //show continue menu
                boolean valid = false;
                while(!valid){
                    try {
                        int choice = Integer.parseInt(input.getInput());
                        switch (choice) {
                            case 1 -> {
                                System.out.println("Continuing stage " + stage + "...");
                                ui.clearCombatLog();
                                valid = true;
                            }
                            
                            case 2 -> {
                                //rest
                                ui.restingMenu();
                                playerResting();
                                valid = true;
                            }

                            case 3 -> {
                                //save and quit
                                enemies++;
                                Java_Game_Project.game = SaveHandler.game;
                                SaveHandler.saveGame();
                                ui.savePlayerRecord(this, player);
                                ui.quitGame();
                                return;
                            }
                            default -> {
                                ui.invalidInput("Only choose 1-3!");
                            }
                        }
                    } catch (NumberFormatException e) {
                        ui.invalidInput(e.getMessage() + " Only choose 1-3!");
                    }
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
        boolean valid = false; //makes menu show again if invalid input
        while(!valid){
            try {
                int choice = Integer.parseInt(input.getInput());
                switch (choice) {
                    case 1 -> {
                        ui.savePlayerRecord(this, player);
                        System.out.println("Healed to full HP");
                        player.healToFull();
                        System.out.println("Continuing stage " + stage + "...");
                        ui.clearCombatLog();
                        valid = true;
                    }
                    case 2 -> {
                        //show player record
                        player.healToFull();
                        ui.savePlayerRecord(this, player);
                        ui.loadPlayerRecord();
                        System.out.print("Would you like to see the battle log (y/n)? ");
                        String showLog = input.getInput();
                        if (showLog.equalsIgnoreCase("y") || showLog.equalsIgnoreCase("yes")) {
                            ui.displayLog();
                            System.out.println("Press ENTER to continue.");
                            input.getInput();
                        }
                        System.out.println("Continuing stage " + stage + "...");
                        ui.clearCombatLog();
                        valid = true;
                    }
                    case 3 -> {
                        //save and quit
                        ui.savePlayerRecord(this, player);
                        enemies++;
                        player.healToFull();
                        Java_Game_Project.game = SaveHandler.game;
                        SaveHandler.saveGame();
                        ui.quitGame();
                        return;
                    }
                    default -> ui.invalidInput("Only chose 1-3!");
                }
            } catch (NumberFormatException e) {
                ui.invalidInput(e.getMessage() + " Only chose 1-3!");
            }
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
        return stageEnemy(stage).size();
    }
    public int getRemainingEnemies() {
        return getTotalEnemiesInStage() - enemies;
    }
}