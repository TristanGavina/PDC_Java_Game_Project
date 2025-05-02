/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project.GameUI;

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
public class EncounterUI implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String PLAYERPROGRESS_PATH = "./GameSaves/playerProgress.txt";
    public static void loadPlayerProgress() {
        try (BufferedReader br = new BufferedReader(new FileReader(PLAYERPROGRESS_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading player records: " + e.getMessage());
        }
    }

    //Encounter menus
    public void playerContinue() {
        File continueOutput = new File("GameUIs/playerContinue.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(continueOutput))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading playerContinue menu: " + e.getMessage());
        }
    }

    public void restingMenu() {
        File file = new File("GameUIs/restingMenu.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading resting menu: " + e.getMessage());
        }
    }

    public void encounterMessage(String name, EnemyType enemyType) {
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

    private static final String PLAYERRECORD_PATH = "./GameSaves/playerRecord.txt";
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
