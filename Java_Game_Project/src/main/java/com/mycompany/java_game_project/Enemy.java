/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project;

/**
 *
 * @author trist
 */

public class Enemy extends GameObjects{
    public EnemyType type; 
    private int currentHp;
    
    public Enemy(EnemyType type) {
        super( type.getHealth(), type.getDefense(), type.getAttack());
        this.type = type;
        this.currentHp = currentHp;
    }
    
    @Override
    public void takeDamage(int damage){
        health = Math.max(health - damage, 0);
        if(health <= 0){
            System.out.println(type + " has been killed!");
        } else {
            System.out.println(type + " took " + damage + " damage! \nRemaining HP: " + health);
        }
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing Enemy: " + type + " with " + health + " HP, " + defense + " Def, " + attack + " ATK.");
    }

    public EnemyType getType() {
        return type;
    }
    
    public int getCurrentHp(){
        return currentHp;
    }

}
