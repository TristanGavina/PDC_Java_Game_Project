/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project.GameUI;
import com.mycompany.java_game_project.Enemy;
import com.mycompany.java_game_project.Player;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author trist
 */
public class StatsPanel extends JPanel{
    private JLabel nameLabel = new JLabel();
    private JLabel healthLabel = new JLabel();
    private JLabel attackLabel = new JLabel();
    private JLabel defenceLabel = new JLabel();        
    
    public StatsPanel(){
        setLayout(new GridLayout(4,1));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(nameLabel);
        add(healthLabel);
        add(attackLabel);
        add(defenceLabel);
    }
    
    public void playerStat(Player player){
        nameLabel.setText(player.getName());
        healthLabel.setText("HP: " + player.getHealth() + " / " + player.getMaxHP());
        attackLabel.setText("ATK: " + player.getAttack());
        defenceLabel.setText("DEF: " + player.getDefense() + (player.isDefending() ? " (DEFENDING)" : ""));
    }
    
    public void enemyStat(Enemy enemy){
        nameLabel.setText(enemy.getType().toString());
        healthLabel.setText("HP: " + enemy.getHealth());
        attackLabel.setText("ATK: " + enemy.getAttack());
        defenceLabel.setText("DEF: " + enemy.getDefense());
    }
    
    //getters
    public JLabel getNameLabel(){
        return nameLabel;
    }
    
    public JLabel getHealthLabel(){
        return healthLabel;
    }
    
    public JLabel getAttackLabel(){
        return attackLabel;
    }
    
    public JLabel getDefenceLabel(){
        return defenceLabel;
    }
}
