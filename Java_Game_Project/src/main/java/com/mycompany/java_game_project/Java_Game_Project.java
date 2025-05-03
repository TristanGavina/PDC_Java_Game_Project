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
import java.io.*;

/**
 *
 * THIS IS THE MAIN CLASS
 * @author trist
 */
    
public final class Java_Game_Project implements Serializable {
    private static final long serialVersionUID = 1L;
    
    
    Player player;
    Encounter encounter;
    private boolean gameRunning;
    private final ICombatLog log;
    private final ICombatMenu cm;
    private final IEncounterUI eui;
    private final IEndGame eg;
    private final IGameDetails gd;
    private final IIntroMenu im;
    final IInvalidHandler ih;
    private final IStartMenu sm;
    final IUserInputs input;
    static Java_Game_Project game;
    

    public Java_Game_Project(
            IUserInputs input,
            IEndGame eg,
            IStartMenu sm,
            IIntroMenu im,
            IGameDetails gd,
            IInvalidHandler ih, 
            IEncounterUI eui, 
            ICombatLog log, 
            ICombatMenu cm) {
        
        this.input = input;
        this.eg = eg;
        this.sm = sm;
        this.im = im;
        this.gd = gd;
        this.ih = ih;
        this.eui = eui;
        this.log = log;
        this.cm = cm;
        this.gameRunning = true;
    }
    
    public void menu(){
        while(gameRunning){
            WriteFiles.writeAllMenu();
            sm.display();
            try{
                int option = Integer.parseInt(input.getInput());
                switch(option){
                case 1 -> {
                    checkSaveFile();
                    //startGame();//game start
                }
                case 2 -> { //load game
                    SaveHandler.loadGame();
                    if(SaveHandler.game != null && SaveHandler.game != this) {
                        SaveHandler.game.menu();
                        return;
                    } else {
                        ih.loadError("No saved game found. error loading game.");
                    }
                }

                case 3 -> {
                    gameRunning = false;
                    eg.quitGame();// quit game
                }

                default -> ih.invalidInput("Only chose 1-3"); // error message
                }   
            } catch(NumberFormatException e){
                ih.invalidInput(e.getMessage());
            }
        }
    }
        
    private void startGame(){ 
        String name = "";
        
        while(true){
            im.displayIntro();//show intro and ask player name
            name = input.getInput().trim(); // get user input
            if(name.length() < 3 || name.length() > 7){
                ih.invalidInput("Sorry name must be 3 to 7 letters only...");
            } else {
                break;
            }
        }
        player = new Player(name); // store player name
        im.displayWelcome(name); // show player name
        gd.displayGameGuide();
        input.getInput();
        encounter = new Encounter(player, input, eg, gd, ih, eui, log, cm);
        encounter.encountered(); // call encountered method in encounter class
        }
    
    public static void main(String[] args) {
        
        IUserInputs userInput = new UserInputProvider();
        IEndGame eg = new EndGameUI();
        IStartMenu sm = new StartMenu();
        IIntroMenu im = new IntroductionMenu();
        IGameDetails gd = new GameDetails();
        IInvalidHandler ih = new InvalidHandler();
        IEncounterUI eui = new EncounterUI();
        ICombatLog log = new CombatLog();
        ICombatMenu cm = new CombatMenu();
        game = new Java_Game_Project(userInput, eg, sm, im, gd, ih, eui, log, cm);
        SaveHandler.game = game; // Link the SaveHandler to this game instance
        game.menu();
    }
    

    private void checkSaveFile(){
        //check if save files exist
        File saveFile = new File("./GameSaves/Game.sav");
        
        boolean fileExist = true;
        if(saveFile.exists()){
            while(fileExist){
                System.out.println("Save file exists! Would you like to delete and overwrite? (y/n) ");
                String overwrite = input.getInput();
                if(overwrite.equalsIgnoreCase("y") || overwrite.equalsIgnoreCase("yes")){
                    System.out.println("Deleting save...");
                    //delete save files
                    saveFile.delete();
                    clearPlayerProgress();
                    startGame();
                    break;
                } else if(overwrite.equalsIgnoreCase("n") || overwrite.equalsIgnoreCase("no")){
                    System.out.println("Returning to start menu...");
                    break;
                } else{
                    ih.invalidInput("Only y or n or yes or no");
                }
            }
        } else{
            startGame();
            }        
    }
    
    
    public void clearPlayerProgress(){
        File playerRecord = new File("./GameSaves/playerRecord.txt");
        File playerProgress = new File("./GameSaves/playerProgress.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(playerRecord))) {
            bw.write("");
        } catch (IOException e) {
            System.out.println("Error writing combat log to file: " + e.getMessage());
        }
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(playerProgress))) {
            bw.write("");
        } catch (IOException e) {
            System.out.println("Error writing combat log to file: " + e.getMessage());
        }
        
    }
}

