/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project;

import com.mycompany.java_game_project.EnemyType;

/**
 *
 * @author trist
 */

public class Enemy extends GameObjects{
    public EnemyType type; 
    
    public Enemy(EnemyType type) {
        super( type.getHealth(), type.getDefense(), type.getAttack());
        this.type = type;
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing Enemy: " + type + " with " + health + " HP, " + defense + " Def, " + attack + " ATK.");
    }
    
    public void attack(Player player){
        int damageDealt = Math.max(attack - player.defense, 1);
        System.out.println(type + " attacks for " + damageDealt + " damage!");
        player.takeDamage(damageDealt);
    }
}
