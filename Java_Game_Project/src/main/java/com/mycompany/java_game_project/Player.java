/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project;

/**
 *
 * @author trist
 */
public class Player extends GameObjects{
    public String name;
    
    public Player(String name) {
        super(100, 10, 5);
        this.name = name;
    }
    
    /**
    @Override
    public void move(int dx, int dy){
        x += dx;
        y += dy;
        System.out.println(name + "Move");
    }
    */
    
    @Override
    public void draw() {
        System.out.println("Player: " + name + " has: " + health + " HP, " + defense + " Def, " + attack + " ATK.");
    }
    
    
    public void attack(Enemy enemy){
        int damageDealt = Math.max(attack - enemy.defense, 1);
        System.out.println(name + " attacks for " + damageDealt + " damage!");
        enemy.takeDamage(damageDealt);
    }
    /**
    public void takeDamage(int damage){
        
    }
    */
 }

