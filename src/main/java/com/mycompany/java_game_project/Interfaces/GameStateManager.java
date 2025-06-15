/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.java_game_project.Interfaces;

/**
 *
 * @author trist
 */
public interface GameStateManager {
    void nextStage();
    void nextFight();
    void finishGame();
    boolean gameCompleted();
}
