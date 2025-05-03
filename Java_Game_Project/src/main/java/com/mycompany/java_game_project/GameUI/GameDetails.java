/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project.GameUI;

import com.mycompany.java_game_project.Interfaces.IGameDetails;
import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author trist
 */
public class GameDetails implements Serializable, IGameDetails {
    private static final long serialVersionUID = 1L;
    private static final String GAMEHELP_PATH = "GameUI_text_files/gameHelp.txt";
    private static final String HELP_PATH = "GameUI_text_files/help.txt";
    
    
    @Override
    public void displayGameGuide() {
        try (BufferedReader br = new BufferedReader(new FileReader(GAMEHELP_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading gameHelp menu: " + e.getMessage());
        }
    }

    @Override
    public void displayGameDetails() {
        try (BufferedReader reader = new BufferedReader(new FileReader(HELP_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading help file: " + e.getMessage());
        }
    }
    
}
