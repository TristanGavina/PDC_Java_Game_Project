/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */


package com.mycompany.java_game_project;
import com.mycompany.java_game_project.Interfaces.ICombatLog;
import com.mycompany.java_game_project.Interfaces.ICombatMenu;
import com.mycompany.java_game_project.Interfaces.IEncounterUI;
import com.mycompany.java_game_project.Interfaces.IEndGame;
import com.mycompany.java_game_project.Interfaces.IGameDetails;
import com.mycompany.java_game_project.Interfaces.IIntroMenu;
import com.mycompany.java_game_project.Interfaces.IInvalidHandler;
import com.mycompany.java_game_project.Interfaces.IStartMenu;
import com.mycompany.java_game_project.Interfaces.IUserInputs;
import com.mycompany.java_game_project.GameUI.CombatLog;
import com.mycompany.java_game_project.GameUI.CombatMenu;
import com.mycompany.java_game_project.GameUI.EncounterUI;
import com.mycompany.java_game_project.GameUI.EndGameUI;
import com.mycompany.java_game_project.GameUI.GameDetails;
import com.mycompany.java_game_project.GameUI.IntroductionMenu;
import com.mycompany.java_game_project.GameUI.InvalidHandler;
import com.mycompany.java_game_project.GameUI.StartMenu;
import com.mycompany.java_game_project.GameUI.WriteFiles;
import static com.mycompany.java_game_project.StartGame.game;
import java.awt.Color;
import java.io.*;
import javax.swing.JFrame;

/**
 *
 * THIS IS THE MAIN CLASS
 * @author trist
 */
    
public final class Java_Game_Project extends JFrame{  
    public static void main(String[] args) {
        
        IUserInputs userInput = new UserInputProvider();
        IEndGame eg = new EndGameUI();
        //IStartMenu sm = new StartMenu(game);
        IIntroMenu im = new IntroductionMenu();
        IGameDetails gd = new GameDetails();
        IInvalidHandler ih = new InvalidHandler();
        IEncounterUI eui = new EncounterUI();
        ICombatLog log = new CombatLog();
        ICombatMenu cm = new CombatMenu();
        
        //startGame
        StartGame game = new StartGame(userInput, eg, null, im, gd, ih, eui, log, cm);
        SaveHandler.game = game; // Link the SaveHandler to this game instance
        game.menu(); //launches the game
        JFrame frame = new JFrame("Java Game Project");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.black);
        frame.setLocationRelativeTo(null);
        StartMenu start = new StartMenu(game);
        frame.add(start);
        frame.setVisible(true);
    }
    
    
}

