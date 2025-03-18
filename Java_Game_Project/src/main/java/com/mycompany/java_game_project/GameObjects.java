/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project;

/**
 * abstract classes exits to be extended, they cannot be instantiated
 * where all shared behaviors is stored
 * @author trist
 */
public abstract class GameObjects {
    //protected int x, y; //position
    protected int health;
    protected int defense;
    protected int attack;
    
    public GameObjects(int health, int attack, int defense){
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        
        
    }
    
    public void takeDamage(int damage){
        health = Math.max(health - damage, 0);
        if(health <= 0){
            System.out.println(this.getClass().getSimpleName() + " Felled");
        } else {
            System.out.println(this.getClass().getSimpleName() + " took " + damage + " damage! \nRemaining HP: " + health);
        }
    }
    
    public int getHealth() {
        return health;
    }

    public int getDefense() {
        return defense;
    }

    public int getAttack() {
        return attack;
    }
    
    public void attack(GameObjects  target){
        int damageDealt = Math.max(attack - target.defense, 1);
        System.out.println(this.getClass().getSimpleName() + " attacks for " + damageDealt + " damage!");
        target.takeDamage(damageDealt);
    }
    
    public abstract void draw();
}
    
    
