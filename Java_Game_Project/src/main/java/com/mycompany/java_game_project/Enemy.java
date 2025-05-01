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
    
    public Enemy(EnemyType type) {
        super( type.getHealth(), type.getDefense(), type.getAttack());
        this.type = type;
        this.health = type.getHealth();
    }
    
    @Override
    public void takeDamage(int damage){
        health = Math.max(health - damage, 0);
    }
    
    @Override
    public String draw() {
        return ("| Enemy: " + type + "\n| HP: " + health + "| DEF: " + defense + "| ATTACK: " + attack);
    }

    public EnemyType getType() {
        return type;
    }
    
    @Override
    public int getHealth(){
        return health;
    }
    
    @Override
    public int attack(GameObjects target){
        int damageDealt = Math.max(attack - target.getDefense(), 1);
        target.takeDamage(damageDealt);
        return damageDealt;
    }
}
