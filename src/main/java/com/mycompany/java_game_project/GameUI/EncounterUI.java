/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project.GameUI;

import com.mycompany.java_game_project.Interfaces.IEncounterUI;
import com.mycompany.java_game_project.EnemyType;


import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;

import java.io.IOException;
import java.io.Serializable;



/**
 *
 * @author trist
 */
public class EncounterUI implements Serializable, IEncounterUI {
    private static final long serialVersionUID = 1L;
    private static final String PLAYERCONTINUE_PATH = "GameUI_text_files/playerContinue.txt";
    private static final String PLAYERRESTING_PATH = "GameUI_text_files/restingMenu.txt";
    private static final String PLAYERRECORD_PATH = "./GameSaves/playerRecord.txt";

    

    //Encounter menus
    @Override
    public void displayEncounterMessage(String name, EnemyType enemyType) {
        String encounter = name + " has encountered an " + enemyType + "!";
        int boxWidth = 45;
        int totalPadding = boxWidth - encounter.length();
        int leftPadding = totalPadding / 2;
        int rightPadding = totalPadding - leftPadding;
        System.out.println("""
                            |=============================================|
                            |                                             |""");
        System.out.println("|" + " ".repeat(leftPadding)+ encounter + " ".repeat(rightPadding)+ "|");
        System.out.println("|                                             |");
    }

    @Override
    public void displayPlayerContinue() {
        try (BufferedReader br = new BufferedReader(new FileReader(PLAYERCONTINUE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading playerContinue menu: " + e.getMessage());
        }
    }

    @Override
    public void displayPlayerResting() {
        try (BufferedReader br = new BufferedReader(new FileReader(PLAYERRESTING_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading resting menu: " + e.getMessage());
        }
    }
    
    @Override
    public void loadPlayerRecord() {
        try (BufferedReader br = new BufferedReader(new FileReader(PLAYERRECORD_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading player records: " + e.getMessage());
        }
    }
}
