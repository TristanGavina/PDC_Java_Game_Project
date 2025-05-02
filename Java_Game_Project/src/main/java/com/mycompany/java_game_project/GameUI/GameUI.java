/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.java_game_project.GameUI;
import com.mycompany.java_game_project.Enemy;
import com.mycompany.java_game_project.EnemyType;
import com.mycompany.java_game_project.Player;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * This class will store and handle the game ui, reading the txt file for each UI
 *
 * @author trist
 */
public class GameUI implements Serializable{
    private static final long serialVersionUID = 1L;
    
            
    public void quitGame(){
        System.out.println("Closing Game...!");
        System.exit(0);
    }    
    
    public void gameOver(){
        File gameOver = new File("GameUIs/gameOver.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(gameOver))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading playerContinue menu: " + e.getMessage());
        }
        quitGame();
    }
    
    //Combat menu
    public void combatMenu(Player player, Enemy currentEnemy){
        File combatMenu = new File("GameUIs/combatMenu.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(combatMenu))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading playerContinue menu: " + e.getMessage());
        }
        if(player.isDefending()){
            String defDisplay = player.getDefense() + (player.isDefending() ? "*" : "");
            System.out.println("| Player: " + player.getName() + "\n| HP: " + player.getHealth() + " | DEF: "+ defDisplay + " | ATTACK: "+ player.getAttack());
            System.out.println("|_____________________________________________|");
        } else {
            System.out.println(player.draw());
            System.out.println("|_____________________________________________|");
        }
        
        if (currentEnemy.getHealth() <= 0) {
            System.out.println("|   ENEMY: " + currentEnemy.type + " DEFEATED!");
        } else {
            System.out.println(currentEnemy.draw());
        }
        System.out.println("""
                           |=============================================|
                           |_____________________________________________|\n
                           """);
    }
    
    public void gameFinish(){
        File gameFinish = new File("GameUIs/gameFinish.txt");
         try (BufferedReader br = new BufferedReader(new FileReader(gameFinish))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading playerContinue menu: " + e.getMessage());
        }
        quitGame();
    }
    
    
    
}
