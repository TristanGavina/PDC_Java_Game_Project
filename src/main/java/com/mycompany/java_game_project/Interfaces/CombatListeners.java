/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.java_game_project.Interfaces;

import com.mycompany.java_game_project.Enemy;

/**
 *
 * @author trist
 */
public interface CombatListeners {
    void enemyDefeated(Enemy enemy);
    void playerDefeated();
    void stageComplete(int stage);
    
    void onAttack(int damage);
    void onDefend();
    void onHeal(int heal);
    void onEnemyAction(int damage);
    void onAction(String message);
}
