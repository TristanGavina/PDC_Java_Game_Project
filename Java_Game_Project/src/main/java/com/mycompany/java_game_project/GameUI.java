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
        File file = new File("GameUIs/startMenu.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading playerIntro menu: " + e.getMessage());
        }
    }
    
    public void playerIntro(){
        File file = new File("GameUIs/playerIntro.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading playerIntro menu: " + e.getMessage());
        }
    }
    
    public void playerName(String name){
            String playerName = " Welcome to the first stage " + name + "!";

            int boxWidth = 45;
            int totalPadding = boxWidth - playerName.length();
            int leftPadding = totalPadding / 2;
            int rightPadding = totalPadding - leftPadding;

            StringBuilder output = new StringBuilder();
            output.append("\n|=============================================|\n");
            output.append("|").append(" ".repeat(leftPadding)).append(playerName)
                  .append(" ".repeat(rightPadding)).append("|\n");
            output.append("|=============================================|\n");

            System.out.print(output);

            // Write to file
            File file = new File("GameUIs/playerName.txt");
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write(output.toString());
            } catch (IOException e) {
                System.out.println("Error writing player name to file: " + e.getMessage());
            }
        }
    
    public void gameHelp(){
        File file = new File("GameUIs/gameHelp.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading gameHelp menu: " + e.getMessage());
        }
    }
    
    
    
    public void invalidInput(String msg){
        System.out.println("Invalid Input: " + msg);
    }
    public void saveError(String save){
        System.out.println("There seems to be a problem saving the game..." + save);
    }
    public void loadError(String load){
        System.out.println("There seems to be a problem loading the game..." + load);
    }
    
    public void quitGame(){
        System.out.println("Closing Game...!");
        System.exit(0);
    }
    
    //Encounter
    public void playerContinue() {
        File file = new File("GameUIs/playerContinue.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading playerContinue menu: " + e.getMessage());
        }
    }
    
    public void restingMenu() {
        File file = new File("GameUIs/restingMenu.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading resting menu: " + e.getMessage());
        }
    }

    
    public void resumeGame(String name, int currentStage, String defeatedLast, int enemiesLeft) {
       // Resume from where the player left off
       //save player's record
        File file = new File("GameSaves/resumeGame.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replace("{name}", name)
                           .replace("{stage}", String.valueOf(currentStage))
                           .replace("{enemiesLeft}", String.valueOf(enemiesLeft));

                if (line.contains("{defeated}")) {
                    String defeatedText = (defeatedLast != null && !defeatedLast.isEmpty())
                        ? "You have defeated: " + defeatedLast + " last time."
                        : "";
                    line = line.replace("{defeated}", defeatedText);
                }

                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error loading resume message: " + e.getMessage());
        }
    }

    
    public void encounterMessage(String name, EnemyType enemyType) {
        String encounter = name + " has encountered an " + enemyType + "!";
        
        int boxWidth = 45;
        int totalPadding = boxWidth - encounter.length();
        int leftPadding = totalPadding / 2;
        int rightPadding = totalPadding - leftPadding;
        
        System.out.println("\n|=============================================|");
        System.out.println("|                                             |");
        System.out.println("|" + " ".repeat(leftPadding) + encounter + " ".repeat(rightPadding) + "|");
        System.out.println("|                                             |");
    }
    
    //Enemy and Player damage taken
    public void playerAttack(String name, int damage){
        System.out.println("> " + name + " attacks for " + damage + " damage!");
    }
    public void playerHeal(String name){
        System.out.println("> " + name + " heals for 10HP.");
    }
    public void playerDefend(String name){
        System.out.println("> " + name + " braces for an attack.");
    }
    public void playerTakeDamage(String name, int damage, int health){
        System.out.println(name + " took " + damage + " damage!");
        if(health <= 0){
            System.out.println(name + " has been killed.\nGAME OVER!!!");
            gameOver();
        } else{
            System.out.println(name + " remaining HP: " + health);
        }
    }
    public void enemyTakeDamage(EnemyType type, int damage, int health){
        System.out.println(type + " took " + damage + " damage!" );
        if(health <= 0){
            System.out.println(type + " FELLED");
        } else{
            System.out.println(type + " remaining HP: " + health);
        }
    }
    public void enemyAttack(EnemyType type, int damage){
        System.out.println("> " + type + " attacks for " + damage + " damage!");     
    }
    
    public void gameOver(){
        System.out.println("""
                                \n|=============================================|
                                |                                             |
                                |                YOU DIED!!!                  |
                                |                                             |
                                |=============================================|
                                |                GAME OVER!!!                 |
                                |                                             |
                                |       You are welcome to try again          |
                                |_____________________________________________|\n
                                   """);
        quitGame();
    }
    
    //Combat menu
    public void combatMenu(Player player, Enemy currentEnemy){
        System.out.print("""
           |=============================================|
           |                                             |
           |              CHOOSE ACTION!!!               |
           |_____________________________________________|
           |                                             |
           |       Press [1] ATTACK                      |
           |                                             |
           |       Press [2] DEFEND (+5 Defense)         |
           |                                             |
           |       Press [3] HEAL                        |
           |                                             |
           |       Press [4] DETAILS                     |
           |_____________________________________________|
           |=============================================|
           """);
        if(player.isDefending()){
            String defDisplay = player.defense + (player.isDefending() ? "*" : "");
            System.out.println("| HP: " + player.getHealth() + " | DEF: " + defDisplay + " | ATTACK: " + player.getAttack());
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
        System.out.println("""
                                You have reached the final stage!
                           
                                CONGRATULATIONS ON BEATING THE GAME!!!!!!
                           """);
        quitGame();
    }
    
    public void helpFile(){
        try (BufferedReader reader = new BufferedReader(new FileReader("help.txt"))) {
            System.out.println("\n=== HELP MENU ===");
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("=================\n");
        } catch (IOException e) {
            System.out.println("Error reading help file: " + e.getMessage());
        }
    }
    
    public void savePlayerRecord(Encounter encounter, Player player){
        try (FileWriter fw = new FileWriter("GameSaves/player_record.txt")) {
            
            fw.write("|=============================================|\n");
            fw.write("|                Player Record                \n");
            fw.write("|           Name: " + player.getName() + "\n");
            fw.write("|           Defense: " + player.defense + "\n");
            fw.write("|           HP: " + player.health + "\n");
            fw.write("|           Current Stage: " + encounter.getStage() + "\n");
            fw.write("|           Enemies Left: " + encounter.getRemainingEnemies() + "\n");
            fw.write("|=============================================|\n");
        } catch (IOException e){
            System.out.println("Player record did not save. " + e.getMessage());
        }
    }
    
    public void loadPlayerRecord(){
        File file = new File("GameSaves/player_record.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading player records: " + e.getMessage());
        }
    }
}
