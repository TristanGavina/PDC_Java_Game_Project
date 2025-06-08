/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.java_game_project;
import com.mycompany.java_game_project.Interfaces.ICombatLog;
import com.mycompany.java_game_project.Interfaces.ICombatMenu;
import com.mycompany.java_game_project.Interfaces.IEncounterUI;
import com.mycompany.java_game_project.Interfaces.IEndGame;
import com.mycompany.java_game_project.Interfaces.IGameDetails;
import com.mycompany.java_game_project.Interfaces.IInvalidHandler;
import com.mycompany.java_game_project.Interfaces.IUserInputs;
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
    private EnemyType defeatedLast;    
    private final ICombatLog log;
    private final ICombatMenu cm;
    private final IEncounterUI eui;
    private final IEndGame eg;
    private final IGameDetails gd;
    final IInvalidHandler ih;
    final IUserInputs input;
    
    //store enemy type by stage
    private final Map<Integer, ArrayList<EnemyType>> stageEnemies = new HashMap<>();
        
    public Encounter(Player player,
            IUserInputs input,
            IEndGame eg, 
            IGameDetails gd, 
            IInvalidHandler ih, 
            IEncounterUI eui,
            ICombatLog log, 
            ICombatMenu cm) {
        this.player = player;
        this.input = input;
        this.stage = 1;
        this.eg = eg;
        this.enemies = 0;
        this.gd = gd;
        this.ih = ih;
        this.eui = eui;
        this.log = log;
        this.cm = cm;
        
        enemyStages();
    }
    
    //initializing enemy types per stages
    private void enemyStages() {
        //enemy type and what stage they belong to using map and list
        stageEnemies.put(1, new ArrayList<>(List.of(EnemyType.SLIME))); //(new ArrayList<>()) allows to be mutable list
        stageEnemies.put(2, new ArrayList<>(List.of(EnemyType.SLIME, EnemyType.GOBLIN)));
        stageEnemies.put(3, new ArrayList<>(List.of(EnemyType.SLIME, EnemyType.GOBLIN, EnemyType.ZOMBIE)));
        stageEnemies.put(4, new ArrayList<>(List.of(EnemyType.SLIME, EnemyType.GOBLIN, EnemyType.ZOMBIE, EnemyType.MONKEY)));
        stageEnemies.put(5, new ArrayList<>(List.of(EnemyType.SLIME, EnemyType.GOBLIN, EnemyType.ZOMBIE, EnemyType.MONKEY, EnemyType.LIZARDMAN)));
        stageEnemies.put(6, new ArrayList<>(List.of(EnemyType.DEMON, EnemyType.BOSSBABY)));
    }
    
    // converting EnemyTypes to Enemy objects to fight in the stage
    private ArrayList<Enemy> stageEnemy(int stage) {
        ArrayList<EnemyType> types = stageEnemies.get(stage);
        if (types == null) {
            ih.invalidInput("Invalid stage!");
            return new ArrayList<>();
        }
        ArrayList<Enemy> enemy = new ArrayList<>();
        //looping through enemy type from map and creating new enemy object to list
        for (EnemyType type : types) {
            enemy.add(new Enemy(type));
        }
        return enemy;
    }
    
    //encounter flow method
    public void encountered(){
        while(stage <= 6){
            // get list of enemies for current stage
            ArrayList<Enemy> setEnemy = stageEnemy(stage);
            
            for (int i = enemies; i < setEnemy.size(); i++) {
                Enemy enemy = setEnemy.get(i);
                //show current encounter
                eui.displayEncounterMessage(player.getName(), enemy.getType());
                //storing last defeated for when resuming game
                setDefeatedLast(enemy.getType());
                // starts the combat sequence
                combat = new Combat(player, enemy, input, eg, gd, ih, log, cm);
                combat.startCombat();
                //save player's record after combat
                SaveHandler.savePlayerRecord(this, player);
                eui.displayPlayerContinue();//show continue menu after combat
                
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
                                eui.displayPlayerResting();
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
                                eg.quitGame();
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
            }
            System.out.println("All enemies in stage " + getStage() + " have been defeated!");
            System.out.println("Moving to next stage...");
            stage++;
            enemies = 0;
            if(stage <= 6){
                System.out.println("Welcome to stage: " + getStage());
            }
        }
        eg.displayGameFinish();
    }
    
    public void playerResting(){
        boolean valid = false; //makes menu show again if invalid input
        while(!valid){
            try {
                int choice = Integer.parseInt(input.getInput());
                switch (choice) {
                    case 1 -> { //continue
                        System.out.println("Healed to full HP");
                        System.out.println("Continuing stage " + stage + "...");
                        log.clearCombatLog();
                        valid = true;
                    }
                    case 2 -> {
                        //show player record
                        eui.loadPlayerRecord();
                        
                        while(true){
                            System.out.println("Would you like to see the battle log for stage " + getStage() + " (y/n)? ");
                            String showLog = input.getInput();
                            if (showLog.equalsIgnoreCase("y") || showLog.equalsIgnoreCase("yes")) {
                                log.displayLog();
                                System.out.println("\n === Press ENTER to continue. === ");
                                input.getInput();
                                break;
                            } else if (showLog.equalsIgnoreCase("n") || showLog.equalsIgnoreCase("No")){
                                break;
                            } else {
                                ih.invalidInput("Please only yes no");
                            }
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
                        eg.quitGame();
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
    //set last defeated enemy type
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