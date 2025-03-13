/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project;

/**
 *
 * @author trist
 */
public enum EnemyType {
    ZOMBIE(100, 10, 5),
    SLIME(68, 5, 1),
    DEMON(150, 20, 3);
    
    private final int health;
    private final int attack;
    private final int defense;

    private EnemyType(int health, int attack, int defense) {
        this.health = health;
        this.defense = defense;
        this.attack = attack;
    }
    
    public int getHealth() { 
        return health; 
    }
    
    public int getDefense(){
        return defense;
    }
    
    public int getAttack() { 
        return attack; 
    }
    
}
