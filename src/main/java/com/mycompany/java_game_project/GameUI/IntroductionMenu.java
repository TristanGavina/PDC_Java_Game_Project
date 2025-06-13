/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project.GameUI;

import com.mycompany.java_game_project.Data;
import com.mycompany.java_game_project.Database;
import com.mycompany.java_game_project.Interfaces.IIntroMenu;
import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import com.mycompany.java_game_project.Interfaces.IStartMenu;
import com.mycompany.java_game_project.Java_Game_Project;
import com.mycompany.java_game_project.SaveHandler;
import com.mycompany.java_game_project.StartGame;
import static com.mycompany.java_game_project.StartGame.game;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author trist
 */
public class IntroductionMenu extends JPanel implements Serializable, IIntroMenu {
    private static final long serialVersionUID = 1L;
    private static final String INTRO_PATH = "GameUI_text_files/playerIntro.txt";
    private JLabel intro;
    private JLabel intro2;
    private JPanel introPanel;
    private Image image;
    private JTextField nameField;
    private JButton submitButton;
    private Font introFont = new Font("Times New Roman", Font.BOLD, 50);
    private Font buttonFont = new Font("Times New Roman", Font.BOLD, 25);
    private Dimension buttonSize = new Dimension(300, 60);
    private Java_Game_Project frame;
    public Database db;
    public Data data;
    
    public IntroductionMenu(Java_Game_Project frame){
        this.frame = frame;
        this.db = new Database();
        this.db.dbsetup();
        this.image = new ImageIcon("./Images/startMenu.jpg").getImage();
        showUI();
    }
    
    public void showUI(){
        setLayout(new BorderLayout());
        //TITLE setup
        intro = new JLabel("        Welcome to Tristan PDC project 1!      ", SwingConstants.CENTER);
        intro.setForeground(Color.WHITE);
        intro.setFont(introFont);
        intro2 = new JLabel("A turn-based game to defeat the final boss!", SwingConstants.CENTER);
        intro2.setForeground(Color.WHITE);
        intro2.setFont(introFont);
        
        introPanel = new JPanel();
        introPanel.setLayout(new BoxLayout(introPanel, BoxLayout.Y_AXIS));
        introPanel.setOpaque(false);
        introPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        intro.setAlignmentX(Component.CENTER_ALIGNMENT);
        intro2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        introPanel.add(intro);
        introPanel.add(Box.createVerticalStrut(20));
        introPanel.add(intro2);
        add(introPanel, BorderLayout.NORTH);
        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setOpaque(false);

        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(300, 40));
        nameField.setFont(buttonFont);
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        submitButton = new JButton("Start Game");
        submitButton.setFont(buttonFont);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setMaximumSize(buttonSize);

        inputPanel.add(Box.createVerticalStrut(30));
        inputPanel.add(nameField);
        inputPanel.add(Box.createVerticalStrut(15));
        inputPanel.add(submitButton);

        add(inputPanel, BorderLayout.CENTER);
        
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                if (name.length() < 3 || name.length() > 7) {
                    JOptionPane.showMessageDialog(IntroductionMenu.this, "Name must be 3 to 7 characters long.");
                } else {
                    Database db = new Database();
                    db.dbsetup();
                    Data data = db.checkName(name, "default");
                    
                    if(data.loginFlag){
                        if(data.currentScore > 0) {
                            // Existing user with score
                            int option = JOptionPane.showConfirmDialog(
                                IntroductionMenu.this, 
                                "Welcome back " + name + "! Your current score is " + data.currentScore + 
                                ".\nWould you like to continue with this account or start fresh?",
                                "Existing User Found",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE
                            );

                        if(option == JOptionPane.YES_OPTION) {
                                startGameWithUser(name, data.currentScore);
                            } else {
                                db.quitGame(0, name);
                                startGameWithUser(name, 0);
                            }
                        } else {
                            JOptionPane.showMessageDialog(IntroductionMenu.this, "Welcome " + name + "! Starting your adventure...");
                            startGameWithUser(name, 0);
                        }
                    } else {
                        JOptionPane.showMessageDialog(IntroductionMenu.this, "Database error occurred. Please try again.");
                    }
                }
            }
        });
        nameField.addActionListener(e -> submitButton.doClick());
    }
    
    private void startGameWithUser(String name, int currentScore){
        frame.setVisible(false);
        
        if(game != null){
            game.startGameWithName(name, currentScore);
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, getWidth(), getHeight(), this);
    }
    
    //showing introduction
    @Override
    public void displayIntro() {
        try (BufferedReader br = new BufferedReader(new FileReader(INTRO_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading playerIntro menu: " + e.getMessage());
        }
    }
      
    @Override
    public void displayWelcome(String name) {
        String welcome = " Welcome to the first stage " + name + "!";
        
        //dynamic ui padding
        int boxWidth = 45;
        int totalPadding = boxWidth - welcome.length();
        int leftPadding = totalPadding / 2;
        int rightPadding = totalPadding - leftPadding;
        
        System.out.println("""
                            |=============================================|
                            |                                             |""");
        System.out.println("|" + " ".repeat(leftPadding)+ welcome + " ".repeat(rightPadding)+ "|");
        System.out.println("|                                             |");
    }
}
