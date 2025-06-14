/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */


package com.mycompany.java_game_project;
import com.mycompany.java_game_project.GameUI.CombatGUI;
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
import java.awt.CardLayout;
import java.awt.Color;
import java.io.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * THIS IS THE MAIN CLASS
 * @author trist
 */
    
public final class Java_Game_Project extends JFrame{  
    
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private StartMenu startMenu;
    private IntroductionMenu introMenu;
    private StartGame game;
    private CombatGUI combatMenu;
    private Player currentPlayer;
    private Encounter currentEncounter;
    
    public Java_Game_Project(){
        IUserInputs userInput = new UserInputProvider();
        IEndGame eg = new EndGameUI();
        IGameDetails gd = new GameDetails();
        IInvalidHandler ih = new InvalidHandler();
        IEncounterUI eui = new EncounterUI();
        ICombatLog log = new CombatLog();
        ICombatMenu cm = new CombatMenu();
        
        startMenu = new StartMenu(this);
        introMenu = new IntroductionMenu(this);
        
        //startGame
        game = new StartGame(userInput, eg, startMenu, introMenu, gd, ih, eui, log, cm);
        SaveHandler.game = game;
        //game.menu(); //launches the game
        
        //cardlayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        startMenu.setGame(game);
        //introMenu = new IntroductionMenu(this);
        
        
        cardPanel.add(startMenu, "StartMenu");
        cardPanel.add((JPanel)introMenu, "IntroMenu");
        
        add(cardPanel);
        setTitle("Java Game Project");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.black);
        setLocationRelativeTo(null);
        setVisible(true);
    }
            
    public void showIntroMenu() {
        cardLayout.show(cardPanel, "IntroMenu");
    }
    
    public void showCombat(){
        cardLayout.show(cardPanel, "Combat");
    }
    
    public void startCombat(Player player, Encounter encounter){
        this.currentPlayer = player;
        this.currentEncounter = encounter;
        
        //remove exisiting combat panel
        if (combatMenu != null) {
            cardPanel.remove(combatMenu);
        }
        
        //combat gui with player and enemy data
        combatMenu = new CombatGUI(this, player, encounter);
        cardPanel.add((JPanel) combatMenu, "Combat");
        
        cardPanel.revalidate();
        cardPanel.repaint();
    }
    
    public void showCombat(Player player, Encounter encounter){
        startCombat(player, encounter);
        showCombat();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Java_Game_Project::new);
    }
    
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    
    public Encounter getCurrentEncounter() {
        return currentEncounter;
    }
    
}

