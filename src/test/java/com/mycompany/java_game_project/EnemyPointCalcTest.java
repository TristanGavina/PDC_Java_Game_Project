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
public class EnemyPointCalcTest {
    EnemyPointCalc calculator;
    
    public EnemyPointCalcTest() {
    }

    @Before
    public void setUp(){
        calculator = new EnemyPointCalc();
    }
    
    @Test
    public void testCalcPoints() {
        int slime = calculator.calcPoints(EnemyType.SLIME);
        assertEquals("SLIME should give 5 points", 5, slime);
        
        int goblin = calculator.calcPoints(EnemyType.GOBLIN);
        assertEquals("GOBLIN should give 10 points", 10, goblin);
        
        int zombie = calculator.calcPoints(EnemyType.ZOMBIE);
        assertEquals("ZOMBIE should give 20 points", 20, zombie);
        
        int monkey = calculator.calcPoints(EnemyType.MONKEY);
        assertEquals("MONKEY should give 30 points", 30, monkey);
        
        int lizardman = calculator.calcPoints(EnemyType.LIZARDMAN);
        assertEquals("LIZARDMAN should give 40 points", 40, lizardman);
        
        int demon = calculator.calcPoints(EnemyType.DEMON);
        assertEquals("DEMON should give 5 points", 100, demon);
        
        int bossbaby = calculator.calcPoints(EnemyType.BOSSBABY);
        assertEquals("BOSSBABY should give 100 points", 200, bossbaby);
    }

    @Test
    public void testPointSingleAdded() {
        calculator.pointAdded(50);
        assertEquals("Total should be 50", 50, calculator.getTotalScore());
    }
    
    @Test
    public void testPointMultiAdded() {
        calculator.pointAdded(50);
        calculator.pointAdded(100);
        calculator.pointAdded(50);
        assertEquals("Total should be 200", 200, calculator.getTotalScore());
    }
    
    @Test
    public void testPointZeroAdded() {
        calculator.pointAdded(0);
        assertEquals("Total should still be 0", 0, calculator.getTotalScore());
    }
    
    @Test
    public void testPointNegativeAdded() {
        calculator.pointAdded(100);
        calculator.pointAdded(-10);
        assertEquals("Total should be 90 after adding 100 and reducing 10", 90, calculator.getTotalScore());
    }

    @Test
    public void testGetTotalScore() {
        calculator.pointAdded(100);
        assertEquals("Total score should be 100", 100, calculator.getTotalScore());
    }

    @Test
    public void testSetTotalScore() {
        calculator.setTotalScore(10);
        assertEquals("Total set score should be 10", 10, calculator.getTotalScore());
    }
    
}
