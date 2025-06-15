/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project;

import com.mycompany.java_game_project.Interfaces.GameStateManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author trist
 */
public class StageManager implements GameStateManager{

    public static int MAX_STAGES = 6;
    private Map<Integer, ArrayList<EnemyType>> stageEnemies;
    private int currentStage = 1;
    private int enemiesDefeated = 0;
    
    public StageManager(){
        this.stageEnemies = initStage();
    }
    
    private Map<Integer, ArrayList<EnemyType>> initStage(){
        Map<Integer, ArrayList<EnemyType>> stageEnemy = new HashMap<>();
        stageEnemy.put(1, new ArrayList<>(List.of(EnemyType.SLIME))); //(new ArrayList<>()) allows to be mutable list
        stageEnemy.put(2, new ArrayList<>(List.of(EnemyType.SLIME, EnemyType.GOBLIN)));
        stageEnemy.put(3, new ArrayList<>(List.of(EnemyType.SLIME, EnemyType.GOBLIN, EnemyType.ZOMBIE)));
        stageEnemy.put(4, new ArrayList<>(List.of(EnemyType.SLIME, EnemyType.GOBLIN, EnemyType.ZOMBIE, EnemyType.MONKEY)));
        stageEnemy.put(5, new ArrayList<>(List.of(EnemyType.SLIME, EnemyType.GOBLIN, EnemyType.ZOMBIE, EnemyType.MONKEY, EnemyType.LIZARDMAN)));
        stageEnemy.put(6, new ArrayList<>(List.of(EnemyType.DEMON, EnemyType.BOSSBABY)));
        return stageEnemy;
    }
    
    @Override
    public void nextStage() {
        if(stageCompleted()){
            currentStage++;
            enemiesDefeated = 0;
        }
    }

    @Override
    public void nextFight() {
        enemiesDefeated++;
    }

    @Override
    public void finishGame() {
        
    }

    @Override
    public boolean gameCompleted() {
        return currentStage > MAX_STAGES;
    }
    
    public boolean stageCompleted(){
        ArrayList<EnemyType> currentStageEnemy = stageEnemies.get(currentStage);
        return currentStageEnemy != null && enemiesDefeated >= currentStageEnemy.size();
    }
    
    public EnemyType getNextEnemy(){
        ArrayList<EnemyType> currentStageEnemy = stageEnemies.get(currentStage);
        if(currentStageEnemy != null && enemiesDefeated < currentStageEnemy.size()){
            return currentStageEnemy.get(enemiesDefeated);
        }
        return null;
    }
    
    public int getCurrentStage(){
        return currentStage;
    }
    
    public int getEnemiesDefeated(){
        return enemiesDefeated;
    }
    
}
