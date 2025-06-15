/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project;

import com.mycompany.java_game_project.GameUI.StatsPanel;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author trist
 */
public class StyleCombatUI {
    private Font buttonFont = new Font("Times New Roman", Font.BOLD, 15);
    private Font statFont = new Font("Times New Roman", Font.BOLD, 25);
    private Font combatFont = new Font("Times New Roman", Font.BOLD, 15);
    private Dimension buttonSize = new Dimension(150, 40);
    
    public JButton styleButton(String txt){
        JButton button = new JButton(txt);
        button.setFont(buttonFont);
        button.setPreferredSize(buttonSize);
        button.setMaximumSize(buttonSize);
        button.setFocusPainted(false);
        return button;
    }
    
    public void styleStatPanel(StatsPanel panel){
        panel.getNameLabel().setForeground(Color.WHITE);
        panel.getNameLabel().setFont(statFont);
        
        panel.getHealthLabel().setForeground(Color.WHITE);
        panel.getHealthLabel().setFont(statFont);
        
        panel.getAttackLabel().setForeground(Color.WHITE);
        panel.getAttackLabel().setFont(statFont);
        
        panel.getDefenceLabel().setForeground(Color.WHITE);
        panel.getDefenceLabel().setFont(statFont);
    }
    
    public JTextArea showCombatLog(){
        JTextArea combatLog = new JTextArea(5,40);
        combatLog.setFont(combatFont);
        combatLog.setEditable(false);
        combatLog.setLineWrap(true);
        combatLog.setWrapStyleWord(true);
        combatLog.setOpaque(false);
        combatLog.setForeground(Color.WHITE);
        return combatLog;
    }
}
