/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.java_game_project;

import com.mycompany.java_game_project.Interfaces.CombatListeners;
import com.mycompany.java_game_project.Interfaces.CombatListenersTest;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author trist
 */
public class CombatManagerTest {
    
    CombatManager combatManager;
    Player player;
    Enemy enemy;
    CombatListenersTest listener;
    
    @Before
    public void setUp(){
        player = new Player("TestPlayer");
        enemy = new Enemy(EnemyType.ZOMBIE);
        combatManager = new CombatManager(player);
        listener = new CombatListenersTest();
    }

    @Test
    public void testAddCombatListeners() {
        combatManager.addCombatListeners((CombatListeners) listener);
        combatManager.startCombat(enemy);
        combatManager.doAttack();
        
        assertTrue("Listener should receive action notifications", listener.getAction().size() > 0);
    }

    @Test
    public void testStartCombat() {
        assertFalse("Combat should not be first", combatManager.isInCombat());
        assertNull("Should have no current enemy when not in combat", combatManager.getCurrentEnemy());
        
        combatManager.startCombat(enemy);
        assertTrue("Combat should start", combatManager.isInCombat());
        assertEquals("Current enemy should show", enemy, combatManager.getCurrentEnemy());
        
    }

    @Test
    public void testGetCurrentEnemy() {
        assertNull("No enemy when not in combat", combatManager.getCurrentEnemy());
        
        combatManager.startCombat(enemy);
        assertEquals("Current enemy should be matching", enemy, combatManager.getCurrentEnemy());
    }

    @Test
    public void testIsInCombat() {
        assertFalse("Combat should not be first", combatManager.isInCombat());
        
        combatManager.startCombat(enemy);
        assertTrue("Now in combat", combatManager.isInCombat());
    }

    @Test
    public void testDoAttack() {
        combatManager.addCombatListeners(listener);
        combatManager.startCombat(enemy);
        
        int enemyHP = enemy.getHealth();
        int playerHP = player.getHealth();
        
        combatManager.doAttack();
        
        //verifying attack happened
        assertTrue("Enemy take damage", enemy.getHealth() < enemyHP);
        assertTrue("Action is logged", listener.getAction().size() > 0);
        
        //if enemy alive player take damage
        if(enemy.getHealth() > 0){
            assertTrue("Player take damage", player.getHealth() < playerHP);
        }
    }

    @Test
    public void testDoDefend() {
        combatManager.addCombatListeners(listener);
        combatManager.startCombat(enemy);
        int playerHP = player.getHealth();
        
        combatManager.doDefend();
        assertTrue("Defend action should be logged", listener.getAction().stream().anyMatch(action -> action.contains("defensive stance")));
        
        //player should take less damage when defending
        if(enemy.getHealth() > 0){
            assertTrue("Player should take less damage", player.getHealth() < playerHP);
        }
    }

    @Test
    public void testDoHeal() {
        combatManager.addCombatListeners(listener);
        combatManager.startCombat(enemy);
                
        //damaging player
        player.takeDamage(50);
        int playerHP = player.getHealth();

        combatManager.doHeal();
        assertTrue("Player should heal", player.getHealth() > playerHP);
        assertTrue("Heal action is logged", listener.getAction().stream().anyMatch(action -> action.contains("heals")));
    }
    
}
