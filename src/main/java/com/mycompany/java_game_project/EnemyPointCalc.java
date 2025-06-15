/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project;

import com.mycompany.java_game_project.Interfaces.ICalculatePoint;

/**
 *
 * @author trist
 */
public class EnemyPointCalc implements ICalculatePoint{
    private int totalScore = 0;
    
    @Override
    public int calcPoints(EnemyType enemyType){
        return switch(enemyType){
            case SLIME -> 5;
            case GOBLIN -> 10;
            case ZOMBIE -> 20;
            case MONKEY -> 30;
            case LIZARDMAN -> 40;
            case DEMON -> 100;
            case BOSSBABY -> 200;
        };
    }
    
    @Override
    public void pointAdded(int points){
        totalScore += points;
    }
    
    @Override
    public int getTotalScore(){
        return totalScore;
    }
    
    public void setTotalScore(int score){
        this.totalScore = score;
    }
}
