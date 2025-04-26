/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.java_game_project;
import java.io.*;
/**
 * abstract classes exits to be extended, they cannot be instantiated
 * where all shared behaviors is stored
 * @author trist
 */
public abstract class GameObjects implements Serializable{
    private static final long serialVersionUID = 1L;
    //protected int x, y; //position
    protected int health;
    protected int attack;
    protected int defense;
    
    public GameObjects(int health, int defense, int attack ){
        this.health = health;
        this.defense = defense;
        this.attack = attack;
        
        
    }
    
    public abstract void takeDamage(int damage);

    public int getHealth() {
        return health;
    }
    
    public int getAttack() {
            return attack;
        }
    
    public int getDefense() {
        return defense;
    }

    
    
    public abstract void attack(GameObjects  target); //
    
    public abstract String draw(); //overrite to print stats

}
    
    
