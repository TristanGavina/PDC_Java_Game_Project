/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project;

import com.mycompany.java_game_project.Interfaces.ICombatLog;
import com.mycompany.java_game_project.GameUI.CombatLog;
import com.mycompany.java_game_project.GameUI.EndGameUI;
import java.io.Serializable;

/**
 *
 * @author trist
 */
public class Turns implements Serializable{
    private static final long serialVersionUID = 1L;
    ICombatLog log;
    
    public Turns(ICombatLog log) {
            this.log = log;
        }
    
    public void playerDefend(String name) {
        String turn = "> " + name + " braces for an attack.";
        System.out.println(turn);
        log.logTurn(turn);
    }

    public void playerTakeDamage(String name, int damage, int health){
        String turn = name + " took " + damage + " damage!";
        if (health <= 0) {
            System.out.println(name + " has been killed.\nGAME OVER!!!");
        } else {
            System.out.println(name + " remaining HP: " + health);
        }
        System.out.println(turn);
        log.logTurn(turn);
    }

    public void enemyTakeDamage(EnemyType type, int damage, int health) {
        String turn = type + " took " + damage + " damage!";
        if (health <= 0) {
            System.out.println(type + " FELLED");
        } else {
            System.out.println(type + " remaining HP: " + health);
        }
        System.out.println(turn);
        log.logTurn(turn);
    }

    public void enemyAttack(EnemyType type, int damage) {
        String turn = "> " + type + " attacks for " + damage + " damage!";
        System.out.println(turn);
        log.logTurn(turn);
    }

    //Enemy and Player damage taken
    public void playerAttack(String name, int damage) {
        String turn = "> " + name + " attacks for " + damage + " damage!";
        System.out.println(turn);
        log.logTurn(turn);
    }

    public void playerHeal(String name) {
        String turn = "> " + name + " heals for 10HP.";
        System.out.println(turn);
        log.logTurn(turn);
    }
}
