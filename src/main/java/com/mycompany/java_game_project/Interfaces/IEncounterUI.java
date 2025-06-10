/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.java_game_project.Interfaces;

import com.mycompany.java_game_project.EnemyType;

/**
 *
 * @author trist
 */
public interface IEncounterUI {
    void displayPlayerContinue();
    void displayPlayerResting();
    void displayEncounterMessage(String name, EnemyType enemyType);
    //void loadPlayerProgress();
    void loadPlayerRecord();
}
