/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.java_game_project.GameUI;
import java.io.*;
import com.mycompany.java_game_project.Interfaces.IEndGame;
/**
 * This class is for uis for end of the game 
 *
 * @author trist
 */
public class EndGameUI implements Serializable, IEndGame{
    private static final long serialVersionUID = 1L;
    private static final String GAMEOVER_PATH = "GameUI_text_files/gameOver.txt";
    private static final String GAMEFINISH_PATH = "GameUI_text_files/gameFinish.txt";
    
            
    @Override
    public void quitGame(){
        System.out.println("Closing Game...!");
        System.exit(0);
    }    
    
    @Override
    public void displayGameOver(){
        try (BufferedReader br = new BufferedReader(new FileReader(GAMEOVER_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading game over menu: " + e.getMessage());
        }
        quitGame();
    }
    
    @Override
    public void displayGameFinish(){
         try (BufferedReader br = new BufferedReader(new FileReader(GAMEFINISH_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading game finish menu: " + e.getMessage());
        }
        quitGame();
    }
    
    
    
}
