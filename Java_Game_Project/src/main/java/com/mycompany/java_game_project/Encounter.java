/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.java_game_project;
import com.mycompany.java_game_project.GameUI.CombatLog;
import com.mycompany.java_game_project.GameUI.EncounterUI;
import com.mycompany.java_game_project.GameUI.GameUI;
import com.mycompany.java_game_project.GameUI.GuideAndDetails;
import com.mycompany.java_game_project.GameUI.InvalidHandler;
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
    private final GuideAndDetails gd;
    private final InvalidHandler ih;
    private final EncounterUI encounterUI;
    private final CombatLog log;
    Turns turn;
    
    //store enemy type by stage
    private final Map<Integer, ArrayList<EnemyType>> stageEnemies = new HashMap<>();
        
    public Encounter(Player player, UserInputs input, GameUI ui, GuideAndDetails gd, InvalidHandler ih, EncounterUI encounterUI, CombatLog log) {
        this.player = player;
        this.input = input;
        this.stage = 1;
        this.ui = ui;
        this.enemies = 0;
        this.gd = gd;
        this.ih = ih;
        this.encounterUI = encounterUI;
        this.log = log;
        this.turn = new Turns(log);
        
        enemyStages();
    }
    
    //initializing enemy types per stages
    private void enemyStages() {
        //enemy type and what stage they belong to using map and list
        stageEnemies.put(1, new ArrayList<>(List.of(EnemyType.SLIME))); //(new ArrayList<>()) for future expansion/remove
        stageEnemies.put(2, new ArrayList<>(List.of(EnemyType.SLIME, EnemyType.GOBLIN)));
        stageEnemies.put(3, new ArrayList<>(List.of(EnemyType.SLIME, EnemyType.GOBLIN, EnemyType.ZOMBIE)));
        stageEnemies.put(4, new ArrayList<>(List.of(EnemyType.SLIME, EnemyType.GOBLIN, EnemyType.ZOMBIE, EnemyType.MONKEY)));
        stageEnemies.put(5, new ArrayList<>(List.of(EnemyType.SLIME, EnemyType.GOBLIN, EnemyType.ZOMBIE, EnemyType.MONKEY, EnemyType.LIZARDMAN)));
        stageEnemies.put(6, new ArrayList<>(List.of(EnemyType.DEMON, EnemyType.SMOLLDRAGON)));
    }
    
    // converting EnemyTypes to actual Enemy to fight in the stage
    private ArrayList<Enemy> stageEnemy(int stage) {
        ArrayList<EnemyType> types = stageEnemies.get(stage);
        if (types == null) {
            ih.invalidInput("Invalid stage!");
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
                Enemy enemy = setEnemy.get(i);
                //show current encounter
                encounterUI.encounterMessage(player.getName(), enemy.getType());
                //storing last defeated for when resuming game
                setDefeatedLast(enemy.getType());
                //getting into a fight
                combat = new Combat(player, enemy, input, ui, gd, ih, log);
                combat.startCombat();
                encounterUI.playerContinue(); //show continue menu
                boolean valid = false;
                while(!valid){
                    try {
                        int choice = Integer.parseInt(input.getInput());
                        switch (choice) {
                            case 1 -> {
                                System.out.println("Continuing stage " + getStage() + "...");
                                log.clearCombatLog();
                                valid = true;
                            }
                            
                            case 2 -> {
                                //rest
                                encounterUI.restingMenu();
                                player.healToFull();
                                playerResting();
                                valid = true;
                            }

                            case 3 -> {
                                //save and quit
                                enemies++;
                                Java_Game_Project.game = SaveHandler.game;
                                SaveHandler.saveGame();
                                SaveHandler.savePlayerRecord(this, player);
                                SaveHandler.savePlayerProgress(this, player);
                                ui.quitGame();
                                return;
                            }
                            default -> {
                                ih.invalidInput("Only choose 1-3!");
                            }


                        }
                    } catch (NumberFormatException e) {
                        ih.invalidInput(e.getMessage() + " Only choose 1-3!");
                    }
                }
                //enemies = i + 1;
            }
            System.out.println("All enemies in stage " + getStage() + " have been defeated!");
            System.out.println("Moving to next stage...");
            stage++;
            enemies = 0;
            if(stage <= 6){
                System.out.println("Welcome to stage: " + getStage());
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
                        System.out.println("Healed to full HP");
                        System.out.println("Continuing stage " + stage + "...");
                        log.clearCombatLog();
                        valid = true;
                    }
                    case 2 -> {
                        //show player record
                        encounterUI.loadPlayerRecord();
                        System.out.print("Would you like to see the battle log for stage " + getStage() + " (y/n)?");
                        String showLog = input.getInput();
                        if (showLog.equalsIgnoreCase("y") || showLog.equalsIgnoreCase("yes")) {
                            log.displayLog();
                            System.out.println("Press ENTER to continue.");
                            input.getInput();
                        }
                        System.out.println("Continuing stage " + getStage() + "...");
                        log.clearCombatLog();
                        valid = true;
                    }
                    case 3 -> {
                        //save and quit
                        enemies++;
                        Java_Game_Project.game = SaveHandler.game;
                        SaveHandler.saveGame();
                        SaveHandler.savePlayerRecord(this, player);
                        SaveHandler.savePlayerProgress(this, player);
                        ui.quitGame();
                        return;
                    }
                    default -> ih.invalidInput("Only chose 1-3!");
                }
            } catch (NumberFormatException e) {
                ih.invalidInput(e.getMessage() + " Only chose 1-3!");
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
        return getTotalEnemiesInStage() - getEnemies();
    }
}