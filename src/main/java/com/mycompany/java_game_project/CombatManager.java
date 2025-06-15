/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project;

import com.mycompany.java_game_project.Interfaces.CombatActions;
import com.mycompany.java_game_project.Interfaces.CombatListeners;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author trist
 */
public class CombatManager implements CombatActions {
    private Player player;
    private Enemy currentEnemy;
    private boolean inCombat = false;
    private List<CombatListeners> listeners = new ArrayList<>();

    public CombatManager(Player player){
        this.player = player;
    }
    
    public void addCombatListeners(CombatListeners listener){
        listeners.add(listener);
    }
    
    public void startCombat(Enemy enemy){
        this.currentEnemy = enemy;
        this.inCombat = true;
    }
    
    private boolean canMove(){
        return inCombat && currentEnemy != null;
    }
    
    private void notifyListeners(java.util.function.Consumer<CombatListeners> action) {
        listeners.forEach(action);
    }
    
    private void enemyTurn(){
        if(!inCombat || currentEnemy == null || currentEnemy.getHealth() <= 0){
            return;
        }
        
        int damage = currentEnemy.attack(player);
        notifyListeners(l -> l.onAction(currentEnemy.getType() + " attacks " + player.getName() + " for " + damage + " damage!"));
        player.defendEnd();
        
        if(player.getHealth() <= 0){
            notifyListeners(l -> l.playerDefeated());
        }
    }
    
    private void finishCombat(){
        inCombat = false;
        notifyListeners(l -> l.enemyDefeated(currentEnemy));
    }
    
    public Enemy getCurrentEnemy() {
        return currentEnemy;
    }
    
    public boolean isInCombat(){
        return inCombat;
    }
    
    @Override
    public void doAttack() {
        if(!canMove()){
            return;
        }
        
        int damage = player.attack(currentEnemy);
        notifyListeners(l -> l.onAction(player.getName() + " attacks " + currentEnemy.getType() + " for " + damage + " damage!"));
        
        if(currentEnemy.getHealth() <= 0){
            notifyListeners(l -> l.enemyDefeated(currentEnemy));
            finishCombat();
            return;
        }
        
        enemyTurn();
    }

    @Override
    public void doDefend() {
        if(!canMove()){
            return;
        }
        
        if(!player.isDefending()){
            player.defend();
            notifyListeners(l -> l.onAction(player.getName() + " takes a defensive stance!"));
            enemyTurn();
        }
    }

    @Override
    public void doHeal() {
        if(!canMove()){
            return;
        }
        
        if(player.getHealth() < player.getMaxHP()){
            int currentHealth = player.getHealth();
            player.heal();
            int heal = player.getHealth() - currentHealth;
            notifyListeners(l -> l.onAction(player.getName() + " heals for " + heal + " HP!"));
            enemyTurn();
        }
    }
}
