/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project;


/**
 *
 * @author trist
 */
public class Encounter {
    private final Player player;
    private Combat combat;
    private final int stage;
    
    private final Enemy[] stage1Enemies = new Enemy[]{
        new Enemy(EnemyType.SLIME),
    };
    
    private final Enemy[] stage2Enemies = new Enemy[]{
        new Enemy(EnemyType.SLIME),
        new Enemy(EnemyType.GOBLIN),
    };
    
    private final Enemy[] stage3Enemies = new Enemy[]{
        new Enemy(EnemyType.SLIME),
        new Enemy(EnemyType.GOBLIN),
        new Enemy(EnemyType.ZOMBIE)
    };
    
    private final Enemy[] stage4Enemies = new Enemy[]{
        new Enemy(EnemyType.SLIME),
        new Enemy(EnemyType.GOBLIN),
        new Enemy(EnemyType.ZOMBIE),
        new Enemy(EnemyType.MONKEY)
    };
    
    private final Enemy[] stage5Enemies = new Enemy[]{
        new Enemy(EnemyType.SLIME),
        new Enemy(EnemyType.GOBLIN),
        new Enemy(EnemyType.ZOMBIE),
        new Enemy(EnemyType.MONKEY),
        new Enemy(EnemyType.LIZARDMAN),
        new Enemy(EnemyType.DEMON)
        
    };
    
    private final Enemy[] bossStage = new Enemy[]{
        new Enemy(EnemyType.DRAGON)
    };
    
    public Encounter(String name, int stage) {
        player = new Player(name); 
        this.stage = stage;
    }
    
    public void encountered(){
        
        Enemy[] setEnemy = stageEnemy(stage);
        
        for (Enemy enemy : setEnemy){
            combat = new Combat(player, enemy);
            combat.startCombat();
        }
    }
    
    private Enemy[] stageEnemy(int stage) {
        switch (stage) {
            case 1 -> {
                return stage1Enemies;
            }
            case 2 -> {
                return stage2Enemies;
            }
            case 3 -> {
                return stage3Enemies;
            }
            case 4 -> {
                return stage4Enemies;
            }
            case 5 -> {
                return stage5Enemies;
            }
            case 6 -> {
                return bossStage;
            }
            default -> {
                System.out.println("INVALID STAGE!");
                return new Enemy[0];
            }
        }
    }
    
}