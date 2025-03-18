/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project;

/**
 * show stats FIle i/o
 * @author trist
 */
public class Player extends GameObjects{
    public String name;
    
    public Player(String name) {
        super(100, 10, 5);
        this.name = name;
    }
    public String getName(){
        return name;
    }
    
    @Override
    public int getHealth(){
        return health;
    }
    
    @Override
    public void draw() {
        System.out.println("Player: " + name + " has: " + health + " HP, " + defense + " Def, " + attack + " ATK.");
    }
    
    @Override
    public void takeDamage(int damage){
        health = Math.max(health - damage, 0);
        if(health <= 0){
            System.out.println("YOU DIED!!!");
            System.out.println("GAME OVER!!!");
        } else {
            System.out.println(getName() + " took " + damage + " damage!. Remaining HP: " + health);
        }
    }
    
    public void attack(Enemy target){
        int damageDealt = Math.max(attack - target.defense, 1);
        System.out.println(getName() + " attacks for " + damageDealt + " damage!");
        target.takeDamage(damageDealt);
    }
    
 }

