/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project.GameUI;

import java.io.BufferedReader;

import java.io.FileReader;

import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author trist
 */
public class IntroductionMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String INTRO_PATH = "GameUIs/playerIntro.txt";
    
    //showing introduction
    public void playerIntro() {
        try (BufferedReader br = new BufferedReader(new FileReader(INTRO_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading playerIntro menu: " + e.getMessage());
        }
    }
      
    public void welcomePlayer(String name) {
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
