/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.java_game_project;
import java.io.*;
/**
 * show stats FIle i/o
 * This class stores player stats, actions and information
 * @author trist
 */
public class Player extends GameObjects implements Serializable{
    private static final long serialVersionUID = 1L;
    public final String name;
    private final GameUI ui;
    
    public Player(String name) {
        super(100, 5, 10); //HP , DEF, ATK
        this.name = name;
        this.ui = new GameUI ();
    }
    public String getName(){
        return name;
    }
    
    @Override
    public int getHealth(){
        return health;
    }
    
    @Override
    public String draw() {
        return ("| Player: " + name + "\n| HP: " + health + " | DEF: " + defense + " | ATTACK: " + attack);
    }
    
    @Override
    public void takeDamage(int damage){
        health = Math.max(health - damage, 0); // ensures that hp will not go down 0
        if(health <= 0){
            ui.gameOver();
        } else {
            ui.playerTakeDamage(name, damage, health);
        }
    }
    
    @Override
    public void attack(GameObjects target){ // method for player attack enemy 
        int damageDealt = Math.max(attack - target.defense, 1); // ensures that player will always do 1 damage
        System.out.println("> " +getName() + " attacks for " + damageDealt + " damage!");
        target.takeDamage(damageDealt);
    }

    void defend() { // method for player defends incoming attack (+defense)
        throw new UnsupportedOperationException("Not supported yet.");
    }

    void heal() { // method for player healing (+hp)
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
 }
