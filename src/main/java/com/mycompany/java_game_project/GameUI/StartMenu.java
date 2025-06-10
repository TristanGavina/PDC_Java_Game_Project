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
    JLabel title;
    JPanel buttonPanel;
    JButton start;
    JButton load;
    JButton quit;
    
    public StartMenu(){
        showUI();
    }
    
    public void showUI(){
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        
        //creating components
        title = new JLabel("PDC RPG GAME", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        
        start = new JButton("START NEW GAME");
        load = new JButton("LOAD GAME");
        quit = new JButton("QUIT GAME");
        
        add(title, BorderLayout.NORTH);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        
        buttonPanel.add(start);
        buttonPanel.add(load);
        buttonPanel.add(quit);
        
        add(buttonPanel, BorderLayout.CENTER);
        
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
}

