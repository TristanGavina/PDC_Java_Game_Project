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
public class PlayerTest {
    
    Player player;
    Player target;
    
    
    @Before
    public void setUp(){
        player = new Player("PlayerTesting");
        target = new Player("Target");
    }
    

    @Test
    public void testGetCurrentScore() {
        //testing default score
        assertEquals(0, player.getCurrentScore());
        
        //testing with player having score
        Player playerScore = new Player("PlayerScore", 50);
        assertEquals(50, playerScore.getCurrentScore());
    }

    @Test
    public void testSetCurrentScore() {
        //testing for setting player with 50 score
        player.setCurrentScore(50);
        assertEquals(50, player.getCurrentScore());
        //testing setting player 0 score
        player.setCurrentScore(0);
        assertEquals(0, player.getCurrentScore());
        //testing setting player score negative
        player.setCurrentScore(-50);
        assertEquals(-50, player.getCurrentScore());
        
    }

    @Test
    public void testGetName() {
        //testing getting player name
        assertEquals("PlayerTesting", player.getName());
        
        Player playerTesting2 = new Player("PlayerTesting2");
        assertEquals("PlayerTesting2", playerTesting2.getName());
    }

    @Test
    public void testGetAttack() {
        //testing player default attack (20 dmg)
        assertEquals(20, player.getAttack());
    }

    @Test
    public void testDraw() {
        //testing drawing player stats
        String expected = "| Player: PlayerTesting\n| HP: 100 | DEF: 10 | ATTACK: 20";
        assertEquals(expected, player.draw());
        
        //testing after player takes damage
        player.takeDamage(50);
        String playerDamaged = "| Player: PlayerTesting\n| HP: 50 | DEF: 10 | ATTACK: 20";
        assertEquals(playerDamaged, player.draw());
    }

    @Test
    public void testTakeDamage() {
        //testing player taking damage
        player.takeDamage(50);
        assertEquals(50, player.getHealth());
        
        //testing damage that will kill player (0 hp)
        player.takeDamage(100);
        assertEquals(0, player.getHealth());
        
        //testing for player taking 0 damage
        player = new Player("ZeroDamagePlayer");
        player.takeDamage(0);
        assertEquals(100, player.getHealth());
        
        //testing negative damage (should be nothing)
        player.takeDamage(-10);
        assertEquals(100, player.getHealth());
    }

    @Test
    public void testAttack() {
        //testing attacking with 5 defence
        // damage should be (20 - 10, 1) = 10
        int damageDealt = player.attack(target);
        assertEquals(10, damageDealt);
        assertEquals(90, target.getHealth());
        
        Player highDef = new Player("Highdef"){
            @Override
            public int getDefense(){
                return 50;
            }
        };
        int lessDamage = player.attack(highDef);
        assertEquals(1, lessDamage); // should be always 1
        assertEquals(99, highDef.getHealth());
    }

    @Test
    public void testDefend() {
        int origDef = player.getDefense();
        
        //testing defend call
        player.defend();
        assertTrue(player.isDefending());
        assertEquals(origDef + 50, player.getDefense());
        
        //testing calling defend again (should not stack)
        player.defend();
        assertEquals(origDef + 50, player.getDefense());
    }

    @Test
    public void testIsDefending() {
        //testing not defending
        assertFalse(player.isDefending());
        
        //testing calling defend
        player.defend();
        assertTrue(player.isDefending());
        
        //testing after calling defend
        player.defendEnd();
        assertFalse(player.isDefending());
    }

    @Test
    public void testDefendEnd() {
        int origDef = player.getDefense();
        
        //testing defendend when not defending (should do nothing)
        player.defendEnd();
        assertEquals(origDef, player.getDefense());
        assertFalse(player.isDefending());
        
        //testing defendend after defending
        player.defend();
        player.defendEnd();
        assertEquals(origDef, player.getDefense());
        assertFalse(player.isDefending());
        
        //testing multiple defendend call
        player.defend();
        player.defendEnd();
        player.defendEnd(); // 2nd call should do nothing
        assertEquals(origDef, player.getDefense());
        assertFalse(player.isDefending());
    }

    @Test
    public void testHeal() {
        //testing healing when hp less than 100
        player.takeDamage(50);
        player.heal();
        assertEquals(60, player.getHealth());
        
        //testing healing when player is full hp (should do nothing)
        player = new Player("FullHP");
        player.heal();
        assertEquals(100, player.getHealth());
        
        //testing for healing to fullhp (via resting)
        player.healToFull();
        assertEquals(100, player.getHealth());
        
        //testing healing when low hp
        player.takeDamage(99);
        player.healToFull();
        assertEquals(100, player.getHealth());
    }

    @Test
    public void testHealToFull() {
        //testing for healing to fullhp (via resting)
        player.healToFull();
        assertEquals(100, player.getHealth());
    }
    
    
}