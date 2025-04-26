/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project;
import java.io.*;
/**
 *
 * @author trist
 */
public class SaveHandler implements Serializable {
    private static final long serialVersionUID = 1L;
    static Java_Game_Project game;
    
        public static void loadGame(){
            try{
                FileInputStream fis = new FileInputStream("Game.sav");
                ObjectInputStream ois = new ObjectInputStream(fis);
                game = (Java_Game_Project) ois.readObject();
                ois.close();
                System.out.println("Game Loaded!");
                if (game != null) {
                    game.resumeGame();
                }
            } catch(IOException | ClassNotFoundException e){
                System.out.println("Failed to Load game " + e.getMessage());
                }
        }
        
        public static void saveGame(){      
        try{
            FileOutputStream fos = new FileOutputStream("Game.sav");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(game);
            oos.flush();
            oos.close();
            System.out.println("Saving game...");
        } catch (IOException e){
                System.out.println("Failed to Save game " + e.getMessage());
        }
    }
}
