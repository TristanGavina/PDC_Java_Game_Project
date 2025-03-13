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
    public String name;
    
    public GameObjects(int health, int defense, int attack){
        //this.x = x;
        //this.y = y;
        this.health = health;
        this.defense = defense;
        this.attack = attack;
        
    }
    public void takeDamage(int damage){
        health = Math.max(health - damage, 0);
        if(health <= 0){
            System.out.println(this.getClass().getSimpleName() + " Felled");
        } else {
            System.out.println(this.getClass().getSimpleName() + " took " + damage + " damage!. Remaining HP: " + health);
        }
    }
    
    public abstract void draw();

    
    /**
    public void move(int dx, int dy){
        x += dx;
        y += dy;
        System.out.println("Moved to (" + x + ", " + y + ")");
    }
    */
}
    
    
