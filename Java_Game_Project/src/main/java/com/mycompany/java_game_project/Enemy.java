/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project;
import java.io.*;
/**
 * This class 
 * @author trist
 */

public class Enemy extends GameObjects implements Serializable{
    private static final long serialVersionUID = 1L;
    
    public EnemyType type; 
    private final GameUI ui;
    
    public Enemy(EnemyType type, GameUI ui) {
        super( type.getHealth(), type.getDefense(), type.getAttack());
        this.type = type;
        this.health = type.getHealth();
        this.ui = ui;
    }
    
    @Override
    public void takeDamage(int damage){
        health = Math.max(health - damage, 0);
        if(health <= 0){
            ui.enemyKilled(type);
        } else {
            ui.enemyTakeDamage(type, damage, health);
        }
    }
    
    @Override
    public String draw() {
        return ("| Enemy: " + type + "\n| HP: " + health + "| DEF: " + defense + "| ATTACK: " + attack);
    }

    public EnemyType getType() {
        return type;
    }
    
    public int getCurrentHp(){
        return health;
    }
    
    @Override
    public void attack(GameObjects target){
        int damageDealt = Math.max(attack - target.defense, 1);
        System.out.println("> " + type + " attacks for " + damageDealt + " damage!");
        target.takeDamage(damageDealt);
    }
}
