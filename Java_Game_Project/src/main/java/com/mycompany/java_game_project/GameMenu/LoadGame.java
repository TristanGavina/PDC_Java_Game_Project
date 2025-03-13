/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project.GameMenu;

import java.io.FileReader;
import com.mycompany.java_game_project.GameMenu.MainGame;
import com.mycompany.java_game_project.GameObjects;
import com.mycompany.java_game_project.Player;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import java.util.Scanner;

/**
 * 
 * @author trist
 */
//public class LoadGame {
//
////    private static MainGame game;
////
////    static void saveGame(){ //saving game state
////        try{
////            FileOutputStream fos = new FileOutputStream("saved.txt");
////            PrintWriter pw = new PrintWriter(fos);
////            pw.write("Player name: " + player.name);
////            pw.close();
////            System.out.println("Game Saved!");
////        }
////        catch (Exception e){
////            System.out.println("Cant save data. " + e.getClass() + ": " + e.getMessage());
////        }
////    }
////    
////    static void loadGame(){ //loading game state
////        try{
////            FileInputStream fis = new FileInputStream("Saved.sav");
////            ObjectInputStream ois = new ObjectInputStream(fis);
////            game = (MainGame) ois.readObject(); //reconstituting all MainGame contents
////            ois.close();
////            System.out.println("----- Game Loaded! -----");
////        }
////        catch (Exception e){
////            System.out.println("Cant load data. " + e.getClass() + ": " + e.getMessage());
////        }
////    }
////    
////    
//}
