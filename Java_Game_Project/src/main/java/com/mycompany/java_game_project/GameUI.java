/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.java_game_project;
import java.io.*;
/**
 * This class will store and handle the game ui
 *
 * @author trist
 */
public class GameUI implements Serializable{
    private static final long serialVersionUID = 1L;
    //Start Menu
    public void startMenu(){
        System.out.println("""
                               \n|=============================================|
                               |                                             |
                               |             PDC CUI RPG GAME                |
                               |_____________________________________________|
                               |                                             |
                               |       Press [1] START NEW GAME              |
                               |                                             |
                               |       Press [2] LOAD GAME                   |
                               |                                             |
                               |       Press [3] QUIT GAME                   |
                               |_____________________________________________|
                               |=============================================|
                               |           Start your adventure!!!           |
                               |=============================================|
                               |_____________________________________________|\n
                               """);
    }
    
    public void playerIntro(){
        System.out.println("""
                            _____________________________________________
                           |                                             |
                           |=============================================|
                           |      Welcome to Tristan PDC project 1!      |
                           | A turn-based game to defeat the final boss! |
                           |=============================================|
                           |_____________________________________________|
                           |                                             |
                           |           Please enter your name:           |
                           |_____________________________________________|
                           """);
    }
    
    public void playerName(String name){
        System.out.println("|=============================================|");
        System.out.println("| Welcome to the first stage " + name + ". |");
        System.out.println("|=============================================|");
    }
    
    public void invalidInput(String msg){
        System.out.println("Invalid Input " + msg);
    }
    public void saveError(String save){
        System.out.println("There seems to be a problem saving the game..." + save);
    }
    public void loadError(String load){
        System.out.println("There seems to be a problem loading the game..." + load);
    }
    
    public void quitGame(){
        System.out.println("Saving and Closing Game...!");
        System.exit(0);
    }
    
    //Encounter
    public void playerContinue() {
        System.out.println("""
                                \n|=============================================|
                                |                                             |
                                |        WOULD YOU LIKE TO CONTINUE?          |
                                |_____________________________________________|
                                |                                             |
                                |       Press [1] CONTINUE                    |
                                |                                             |
                                |       Press [2] REST                        |
                                |                                             |
                                |       Press [3] QUIT AND SAVE               |
                                |_____________________________________________|\n
                                   """);
    }

    public void resumeGame(Player player, Encounter encounter) {
    // This method will be called when a saved game is loaded
    if (player != null && encounter != null) {
        // Resume from where the player left off
        System.out.println("Welcome back, " + player.getName() + "!");
        System.out.println("Resuming from stage " + encounter.getStage() + "...");
        EnemyType defeatedLast = encounter.getDefeatedLast();
        if (defeatedLast != null) {
            System.out.println("You have defeated: " + defeatedLast + " last time.");
        }
        System.out.println("There are " + encounter.getRemainingEnemies() + " enemies left to defeat!");
        
        System.out.println("Press enter to continue...");
    }
}
    public void encounterMessage(String playerName, EnemyType enemyType) {
        
        String encounter = playerName + " has encountered an " + enemyType + "!";
        
        int boxWidth = 45;
        int totalPadding = boxWidth - encounter.length();
        int leftPadding = totalPadding / 2;
        int rightPadding = totalPadding - leftPadding;
        
        System.out.println("\n|=============================================|");
        System.out.println("|                                             |");
        System.out.println("|" + " ".repeat(leftPadding) + encounter + " ".repeat(rightPadding) + "|");
    }
    
    //Enemy and Player damage taken
    public void playerTakeDamage(String name, int damage, int health){
        System.out.println(name + " took " + damage + " damage!");
        System.out.println(name + " remaining HP: " + health);
    }
    
    public void enemyTakeDamage(EnemyType type, int damage, int health){
        System.out.println(type + " took " + damage + " damage!\n" + type + " remaining HP: " + health);
    }
    
    public void gameOver(){
        System.out.println("YOU DIED!");
        System.out.println("GAME OVER!");
        System.out.println("you are welcome to try again.");
    }
    
    public void enemyKilled(EnemyType type){
        System.out.println(type + " FELLED!");
    }
    
    //Combat menu
    public void combatMenu(Player player, Enemy currentEnemy){
        System.out.print("""
            _____________________________________________
           |                                             |
           |              CHOOSE ACTION!!!               |
           |_____________________________________________|
           |                                             |
           |       Press [1] ATTACK                      |
           |                                             |
           |       Press [2] DEFEND                      |
           |                                             |
           |       Press [3] ENEMY INFORMATION           |
           |                                             |
           |       Press [4] HELP                        |
           |_____________________________________________|
           |=============================================|
           """);
        if (player.getHealth() <= 0) {
            System.out.println("|       YOU HAVE BEEN KILLED!           |");
        } else {
            System.out.println(player.draw());
        }
        System.out.println("|_____________________________________________|");

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
}
