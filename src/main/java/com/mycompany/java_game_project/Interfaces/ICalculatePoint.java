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
public interface ICalculatePoint {
    int calcPoints(EnemyType enemyType);
    void pointAdded(int points);
    int getTotalScore();
}
