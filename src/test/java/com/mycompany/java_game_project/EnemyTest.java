/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.java_game_project;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author trist
 */
public class EnemyTest {
    
    Enemy enemy;
    Enemy target;
    
    @Before
    public void setIUp(){
        enemy = new Enemy(EnemyType.ZOMBIE); // 50 HP, 5 DEF, 5 ATK
        target = new Enemy(EnemyType.BOSSBABY); // 100 HP, 10 DEF, 20 ATK
    }

    @Test
    public void testTakeDamage() {
        //testing enemy taking damage
        enemy.takeDamage(10);
        assertEquals(40, enemy.getHealth());
        
        //testing damage that will kill enemy (0 hp)
        enemy.takeDamage(100);
        assertEquals(0, enemy.getHealth());
        
        //testing for enemy taking 0 damage
        enemy = new Enemy(EnemyType.BOSSBABY);
        enemy.takeDamage(0);
        assertEquals(100, enemy.getHealth());
        
        //testing negative damage (should be nothing)
        enemy.takeDamage(-10);
        assertEquals(100, enemy.getHealth());
    }

    @Test
    public void testDraw() {
        //testing drawing enemy stats
        String expected = enemy.draw();
        
        //checking/testing what draw should contain
        assertTrue(expected.contains("Enemy:")); //Draw should contain 'Enemy'
        assertTrue(expected.contains("ZOMBIE")); //Draw should contain enemy type"
        assertTrue(expected.contains("HP:")); // "Draw should contain 'HP:'
        assertTrue(expected.contains("DEF:")); // "Draw should contain 'DEF:'
        assertTrue(expected.contains("ATTACK:")); // "Draw should contain 'ATTACK:'
        assertTrue(expected.contains("50")); // "Draw should contain health value
        assertTrue(expected.contains("5")); // "Draw should contain defense and attack values
        
        //testing after player takes damage
        enemy.takeDamage(10);
        String enemyDamaged = enemy.draw();
        assertTrue(enemyDamaged.contains("40"));
    }

    @Test
    public void testGetType() {
        assertEquals(EnemyType.ZOMBIE, enemy.getType());
        assertEquals(EnemyType.BOSSBABY, target.getType());
    
        //testing with different type
        Enemy slime = new Enemy(EnemyType.SLIME);
        assertEquals(EnemyType.SLIME, slime.getType());
    }

    @Test
    public void testGetHealth() {
        assertEquals(50, enemy.getHealth()); //Should return correct initial health
        assertEquals(100, target.getHealth()); //Should return correct initial health for target
    
        //testing after taking dmg
        enemy.takeDamage(10);
        assertEquals(40, enemy.getHealth());
    
        //testing with different type
        Enemy demon = new Enemy(EnemyType.DEMON);
        assertEquals(100, demon.getHealth());
    }

    @Test
    public void testGetAttack() {
        assertEquals(5, enemy.getAttack());
        
        //testing with DEMON type
        Enemy demon = new Enemy(EnemyType.DEMON);
        assertEquals(20, demon.getAttack()); // should be 20 atk
    }

    @Test
    public void testGetDefence() {
        assertEquals(5, enemy.getDefence()); //should return 5 def
    }

    @Test
    public void testAttack() {
        //testing attacking with 3 defence
        //dmg should be (5 - 3) = 2
        Enemy goblin = new Enemy(EnemyType.GOBLIN);
        int damageDealt = enemy.attack(goblin);
        assertEquals(2, damageDealt);
        assertEquals(18, goblin.getHealth());
        
        int lessDmg = enemy.attack(target);
        assertEquals(1, lessDmg); // should always be 1
        assertEquals(99, target.getHealth());
    }
    
}
