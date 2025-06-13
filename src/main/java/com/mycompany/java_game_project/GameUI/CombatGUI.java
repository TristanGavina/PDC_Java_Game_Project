/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project.GameUI;

import com.mycompany.java_game_project.Java_Game_Project;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * This class is gui for both combat/encounter and resting
 * @author trist
 */
public class CombatGUI extends JPanel {
    private Image image;
    
    private Java_Game_Project frame;
    
    public CombatGUI(Java_Game_Project frame){
        this.frame = frame;
        this.image = new ImageIcon("./Images/combat.jpg").getImage();
        showUI();
    }
    
    public void showUI(){
        setLayout(new BorderLayout());
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, getWidth(), getHeight(), this);
    }
}
