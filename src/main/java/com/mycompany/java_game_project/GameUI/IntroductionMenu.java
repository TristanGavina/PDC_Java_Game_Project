/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project.GameUI;

import com.mycompany.java_game_project.Interfaces.IIntroMenu;
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
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author trist
 */
public class IntroductionMenu extends JFrame implements Serializable, IIntroMenu {
    private static final long serialVersionUID = 1L;
    private static final String INTRO_PATH = "GameUI_text_files/playerIntro.txt";
    
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
