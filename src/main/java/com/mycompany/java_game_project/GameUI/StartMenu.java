/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project.GameUI;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import com.mycompany.java_game_project.Interfaces.IStartMenu;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author trist
 */
public class StartMenu extends JPanel implements Serializable, IStartMenu  {
    private static final long serialVersionUID = 1L;
    private static final String FILE_PATH = "./GameUI_text_files/startMenu.txt";

    private JLabel title;
    private JPanel titlePanel;
    private JPanel buttonPanel;
    private Image image;
    private JButton start;
    private JButton load;
    private JButton quit;
    Font titleFont = new Font("Times New Roman", Font.PLAIN, 100);
    
    public StartMenu(){
        this.image = new ImageIcon("./Images/startMenu.jpg").getImage();
        showUI();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, getWidth(), getHeight(), this);
    }
    
    public void showUI(){
        setLayout(new BorderLayout());
        
        //title setup
        title = new JLabel("PDC RPG GAME", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(titleFont);
        
        titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
        titlePanel.add(title, BorderLayout.CENTER);        
        add(titlePanel, BorderLayout.NORTH);
        
        //buttons setup
        start = new JButton("START NEW GAME");
        load = new JButton("LOAD GAME");
        quit = new JButton("QUIT GAME");
        
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(150, 100, 150, 100));
        buttonPanel.add(start);
        buttonPanel.add(load);
        buttonPanel.add(quit);
        
        JPanel botRightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botRightButtons.setOpaque(false);
        botRightButtons.add(buttonPanel);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Add action listeners
        quit.addActionListener(e -> System.exit(0));
    }
    
    @Override
    public void display() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading playerIntro menu: " + e.getMessage());
        }
    }
    
    // Getter methods for controller to access buttons
    public JButton getStartButton() { return start; }
    public JButton getLoadButton() { return load; }
    public JButton getQuitButton() { return quit; }
}

