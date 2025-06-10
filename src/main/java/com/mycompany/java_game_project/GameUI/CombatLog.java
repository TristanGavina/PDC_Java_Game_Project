/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project.GameUI;

import com.mycompany.java_game_project.Interfaces.ICombatLog;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * stores and shows combat log
 * @author trist
 */
public class CombatLog implements Serializable, ICombatLog{
    private static final long serialVersionUID = 1L;
    private final List<String> combatLog = new ArrayList<>();
    
    //file path
    private static final String COMBATLOG_PATH = "./GameUI_text_files/combatLog.txt";
            
    @Override
    public void logTurn(String turn) {
        combatLog.add(turn);
        writeCombatLog();
    }

    //clear combatLog file and memory for every stage
    @Override
    public void clearCombatLog() {
        combatLog.clear();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(COMBATLOG_PATH))) {
            bw.write("");
        } catch (IOException e) {
            System.out.println("Error writing combat log to file: " + e.getMessage());
        }
    }

    @Override
    public void displayLog() {
        try (BufferedReader br = new BufferedReader(new FileReader(COMBATLOG_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading combat log: " + e.getMessage());
        }
    }

    public void writeCombatLog() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(COMBATLOG_PATH))) {
            for (String turn : combatLog) {
                bw.write(turn);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing combat log to file: " + e.getMessage());
        }
    }
}
