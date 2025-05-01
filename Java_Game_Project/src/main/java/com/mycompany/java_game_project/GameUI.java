/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.java_game_project;
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

        StringBuilder playerOutput = new StringBuilder();
        playerOutput.append("\n|=============================================|\n");
        playerOutput.append("|                                             |\n");
        playerOutput.append("|").append(" ".repeat(leftPadding)).append(playerName).append(" ".repeat(rightPadding)).append("|\n");
        playerOutput.append("|                                             |\n");
        System.out.print(playerOutput);

        // Write player name file
        File file = new File("GameUIs/playerName.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(playerOutput.toString());
        } catch (IOException e) {
            System.out.println("Error writing player name to file: " + e.getMessage());
        }
    }
    
    public void gameHelp(){
        File gameHelp = new File("GameUIs/gameHelp.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(gameHelp))) {
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
    
    //Encounter menus
    public void playerContinue() {
        File continueOutput = new File("GameUIs/playerContinue.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(continueOutput))) {
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

    // Resume from where the player left off
    public void playerOldRecord(String name, int currentStage, String defeatedLast, int enemiesLeft) {
       //show player record previous state
        StringBuilder output = new StringBuilder();
            output.append("Welcome back ").append(name);
            output.append("\nResuming from stage ").append(currentStage);
            output.append("\nLast time you have defeated: ").append(defeatedLast);
            output.append("\nEnemies left to defeat: ").append(enemiesLeft);
            output.append("\nPress ENTER to continue");
            System.out.print(output);
        File recordOutput = new File("GameSaves/playerOldRecord.txt");
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(recordOutput))){                
                bw.write(output.toString());
            } catch (IOException e) {
                System.out.println("Error writing player name to file: " + e.getMessage());
            }
    }
    
    public void encounterMessage(String name, EnemyType enemyType) {
        String encounter = name + " has encountered an " + enemyType + "!";
        
        int boxWidth = 45;
        int totalPadding = boxWidth - encounter.length();
        int leftPadding = totalPadding / 2;
        int rightPadding = totalPadding - leftPadding;
        
        StringBuilder encounterOutput = new StringBuilder();
        encounterOutput.append("\n|=============================================|\n");
        encounterOutput.append("|                                             |\n");
        encounterOutput.append("|").append(" ".repeat(leftPadding)).append(encounter).append(" ".repeat(rightPadding)).append("|\n");
        encounterOutput.append("|                                             |");

        System.out.println(encounterOutput);
        
        //Write to encounterFile
        File encounterFile = new File("GameUIs/encounterMessage.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(encounterFile))) {
                bw.write(encounterOutput.toString());
            } catch (IOException e) {
                System.out.println("Error writing player name to file: " + e.getMessage());
            }
    }
    
    
    private final List<String> combatLog = new ArrayList<>();
    public void logTurn(String turn){
        combatLog.add(turn);
    }
    //clear combatLog file and memory
    public void clearCombatLog(){
        combatLog.clear();
        File log = new File("GameUIs/combatLog.txt");
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(log))){
                bw.write("");
            } catch (IOException e){
                System.out.println("Error writing combat log to file: " + e.getMessage());
            }
    }
    public void writeCombatLog(){
        File log = new File("GameUIs/combatLog.txt");
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(log))){
            for(String turn : combatLog){
                bw.write(turn);
                bw.newLine();
            }
            } catch (IOException e){
                System.out.println("Error writing combat log to file: " + e.getMessage());
            }
    }
    public void displayLog(){
        File log = new File("GameUIs/combatLog.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(log))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading combat log: " + e.getMessage());
        }
    }
    
    //Enemy and Player damage taken
    public void playerAttack(String name, int damage){
        String turn = ("> " + name + " attacks for " + damage + " damage!");
        System.out.println(turn);
        logTurn(turn);
        writeCombatLog();
    }
    public void playerHeal(String name){
        String turn = ("> " + name + " heals for 10HP.");
        System.out.println(turn);
        logTurn(turn);
        writeCombatLog();
    }
    public void playerDefend(String name){
        String turn = ("> " + name + " braces for an attack.");
        System.out.println(turn);
        logTurn(turn);
        writeCombatLog();
    }
    public void playerTakeDamage(String name, int damage, int health){
        String turn = (name + " took " + damage + " damage!");
        if(health <= 0){
            System.out.println(name + " has been killed.\nGAME OVER!!!");
            gameOver();
        } else{
            System.out.println(name + " remaining HP: " + health);
        }
        System.out.println(turn);
        logTurn(turn);
        writeCombatLog();
    }
    public void enemyTakeDamage(EnemyType type, int damage, int health){
        String turn = (type + " took " + damage + " damage!" );
        if(health <= 0){
            System.out.println(type + " FELLED");
        } else{
            System.out.println(type + " remaining HP: " + health);
        }
        System.out.println(turn);
        logTurn(turn);
        writeCombatLog();
    }
    public void enemyAttack(EnemyType type, int damage){
        String turn = ("> " + type + " attacks for " + damage + " damage!"); 
        System.out.println(turn);
        logTurn(turn);
        writeCombatLog();
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
            String defDisplay = player.defense + (player.isDefending() ? "*" : "");
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
    
    public void helpFile(){
        File help = new File("GameUIs/help.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(help))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading help file: " + e.getMessage());
        }
    }
    
    public void savePlayerRecord(Encounter encounter, Player player){
        File file = new File("GameSaves/playerRecord.txt");
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write("|=============================================|\n");
                bw.write("|                Player Record                \n");
                bw.write("|           Name: " + player.getName() + "\n");
                bw.write("|           HP: " + player.health + "\n");
                bw.write("|           Defense: " + player.defense + "\n");
                bw.write("|           Attack: " + player.attack + "\n");
                bw.write("|           Current Stage: " + encounter.getStage() + "\n");
                bw.write("|=============================================|\n");
        } catch (IOException e){
            System.out.println("Player record did not save. " + e.getMessage());
        }
    }
    
    public void loadPlayerRecord(){
        File file = new File("GameSaves/playerRecord.txt");
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
