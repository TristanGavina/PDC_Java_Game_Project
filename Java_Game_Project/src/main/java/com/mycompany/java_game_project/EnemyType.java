/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.java_game_project;
import java.io.*;
/**
 *
 * @author trist
 */
public enum EnemyType implements Serializable { //HP , DEF, ATK
    ZOMBIE(50, 5, 5),
    SLIME(10, 0, 2),
    GOBLIN(20, 3, 5),
    MONKEY(30, 3, 10),
    LIZARDMAN(50, 5, 15),
    DEMON(150, 3, 20),
    SMOLLDRAGON(200, 5, 20);
    
    private final int health;
    private final int attack;
    private final int defense;

    private EnemyType(int health, int defense, int attack) {
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        
    }
    
    public int getHealth() { 
        return health; 
    }
    
    public int getAttack() { 
        return attack; 
    }
    
    public int getDefense(){
        return defense;
    }
}
