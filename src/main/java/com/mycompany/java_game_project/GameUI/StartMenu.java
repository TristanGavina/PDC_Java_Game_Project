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
import com.mycompany.java_game_project.SaveHandler;
import com.mycompany.java_game_project.StartGame;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

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
    private Font titleFont = new Font("Times New Roman", Font.BOLD, 100);
    private Font buttonFont = new Font("Times New Roman", Font.BOLD, 25);
    private Dimension buttonSize = new Dimension(300, 60);
    private StartGame game;
    
    public StartMenu(StartGame game){
        this.game = game;
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
        
        //TITLE setup
        title = new JLabel("PDC RPG GAME", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(titleFont);
        
        titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
        titlePanel.add(title, BorderLayout.CENTER);        
        add(titlePanel, BorderLayout.NORTH);
        
        //BUTTONS setup
        start = new JButton("START NEW GAME");
        load = new JButton("LOAD GAME");
        quit = new JButton("QUIT GAME");
        
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(150, 100, 150, 100));
        
        

        JButton[] buttons = { start, load, quit };
        for (JButton button : buttons) {
            button.setFont(buttonFont);
            button.setPreferredSize(buttonSize);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(buttonSize);
            button.setFocusPainted(false);
            button.setMargin(new Insets(10, 20, 10, 20));
            buttonPanel.add(Box.createVerticalStrut(25));
            buttonPanel.add(button);
        }
        
        JPanel bottonPosition = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        bottonPosition.setOpaque(false);
        bottonPosition.add(buttonPanel);
        
        add(bottonPosition, BorderLayout.SOUTH);
        
        // Add action listeners
        start.addActionListener(e -> game.checkSaveFile());
        
        load.addActionListener((ActionEvent e) -> {
            SaveHandler.loadGame();
            if(SaveHandler.game != null && SaveHandler.game != game){
                SaveHandler.game.menu();
            } else {
                game.ih.loadError("No save game found! Error loading game.");
            }
        });
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
    
}

