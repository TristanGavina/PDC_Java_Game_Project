/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project;
import com.mycompany.java_game_project.GameUI.WriteFiles;
import java.io.*;
/**
 *
 * @author trist
 */
public class SaveHandler implements Serializable {
    private static final long serialVersionUID = 1L;
    public static StartGame game;
    
    public static void loadGame(){ //loading game object
        try{
            FileInputStream fis = new FileInputStream("GameSaves/Game.sav");
            ObjectInputStream ois = new ObjectInputStream(fis);
            game = (StartGame) ois.readObject();
            ois.close();
            System.out.println("Game Loaded!");
            resumeGame(game);
        } catch(IOException | ClassNotFoundException e){
            game.ih.loadError("Failed to Load game " + e.getMessage());
            }
    }

    public static void saveGame(){      
        WriteFiles.makeSaveFolder();
        //saving whole game
        try{
            FileOutputStream fos = new FileOutputStream("GameSaves/Game.sav");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(game);
            oos.flush();
            oos.close();
            System.out.println("Saving game...");
        } catch (IOException e){
                game.ih.saveError("Failed to Save game " + e.getMessage());
        }
    }

    // resumes where game left off
    public static void resumeGame(StartGame game) {
        if (game.player != null && game.encounter != null) {
            loadPlayerProgress();
            game.input.getInput();
            game.encounter.encountered();
        } else {
            game.ih.loadError("Could not resume game. Start new game...");
            game.menu();
        }
    }
    private static final String PLAYERRECORD_PATH = "./GameSaves/playerRecord.txt";
    public static void savePlayerRecord(Encounter encounter, Player player) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PLAYERRECORD_PATH))) {
            bw.write("|=============================================|\n");
            bw.write("|                Player Record                \n");
            bw.write("|           Name: " + player.getName() + "\n");
            bw.write("|           HP: " + player.getHealth() + "\n");
            bw.write("|           Defense: " + player.getDefense() + "\n");
            bw.write("|           Attack: " + player.getAttack() + "\n");
            bw.write("|           Current Stage: " + encounter.getStage() + "\n");
            bw.write("|=============================================|\n");
        } catch (IOException e) {
            System.out.println("Player record did not save. " + e.getMessage());
        }
    }

    private static final String PLAYERPROGRESS_PATH = "./GameSaves/playerProgress.txt";
    // show player previous progess
    public static void savePlayerProgress(Encounter encounter, Player player) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PLAYERPROGRESS_PATH))) {
            bw.write("|=============================================|\n");
            bw.write("|                Player Progress             \n");
            bw.write("|           Welcome back: " + player.getName() + "\n");
            bw.write("|           Resuming from stage: " + encounter.getStage() + "\n");
            bw.write("|           Last defeated enemy: " + encounter.getDefeatedLast() + "\n");
            bw.write("|           Number of enemies in this stage: " + encounter.getTotalEnemiesInStage()+ "\n");
            bw.write("""
                     |           === Press ENTER to continue ===   |
                     |=============================================|
                     """);
        } catch (IOException e) {
            System.out.println("Error writing player progress to file: " + e.getMessage());
        }
    }
    
    public static void loadPlayerProgress() {
        try (BufferedReader br = new BufferedReader(new FileReader(PLAYERPROGRESS_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading player records: " + e.getMessage());
        }
    }
}
