/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.java_game_project.Interfaces;

import com.mycompany.java_game_project.Enemy;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * this class is only a test helper class for capturing combat events
 * @author trist
 */
public class CombatListenersTest implements CombatListeners {
    
    ArrayList<String> action = new ArrayList<>();
    boolean playerDefeated = false;
    Enemy enemyDefeated = null;
    
    @Override
    public void enemyDefeated(Enemy enemy) {
        enemyDefeated = enemy;
    }

    @Override
    public void playerDefeated() {
        playerDefeated = true;
    }
    
    public ArrayList<String> getAction(){
        return action;
    }
    
    public boolean isPlayerDead(){
        return playerDefeated;
    }
    
    public Enemy getEnemyDefeated(){
        return enemyDefeated;
    }
    
    @Override
    public void onAction(String message) {
        action.add(message);
    }
    
    //not used by combatManager so not tested
    @Override
    public void stageComplete(int stage) {}
    @Override
    public void onAttack(int damage) {}
    @Override
    public void onDefend() {}
    @Override
    public void onHeal(int heal) {}
    @Override
    public void onEnemyAction(int damage) {}
    
}
