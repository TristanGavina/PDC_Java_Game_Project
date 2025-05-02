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
    private final String name;
    
    public Player(String name) {
        super(100, 10, 20); //HP , DEF, ATK
        this.name = name;
    }
    public String getName(){
        return name;
    }

    @Override
    public String draw() {
        return "| Player: " + name + "\n| HP: " + health + " | DEF: " + defense + " | ATTACK: " + attack;
    }
    
    @Override
    public void takeDamage(int damage){
        health = Math.max(health - damage, 0); // ensures that hp will not go down 0
    }
    
    @Override
    public int attack(GameObjects target){ // method for player attack enemy 
        int damageDealt = Math.max(attack - target.getDefense(), 1); // ensures that player will always do 1 damage
        target.takeDamage(damageDealt);
        return damageDealt;
    }
    
    private boolean defending = false;
    private final int increaseDef = 5;
    
    public void defend() { // method for player defends incoming attack (+defense)
        if(!isDefending()){
            defense += increaseDef;
            defending = true;
        } 
    }
    
    public boolean isDefending(){
        return defending;
    }
    //makes sure only defends 1 turn
    public void defendEnd(){
        if(defending){
            defense -= increaseDef;
            defending = false;
        }
    }
    

    public void heal() { // method for player healing (+hp)
        health = Math.min(health + 10, maxHP); // healing can not go over max hp
    }
    
    public void healToFull(){
        this.health = this.maxHP;
    }
 }
