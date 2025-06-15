/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import com.mycompany.java_game_project.GameUI.WriteFiles;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author trist
 */
public class StartGame implements Serializable{
    private static final long serialVersionUID = 1L;
    private static final String PLAYERRECORD_PATH = "./GameSaves/playerRecord.txt";
    private static final String PLAYERPROGRESS_PATH = "./GameSaves/playerProgress.txt";
    
    Player player;
    Encounter encounter;
    private boolean gameRunning;
    private ICombatLog log;
    private ICombatMenu cm;
    private IEncounterUI eui;
    private IEndGame eg;
    private IGameDetails gd;
    private IIntroMenu im;
    public IInvalidHandler ih;
    private IStartMenu sm;
    final IUserInputs input;
    public static StartGame game;
    private Database db;
    private String currentPlayerName;
    private int currentPlayerScore;
    
    public StartGame(
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
        this.db = new Database();
        this.db.dbsetup();
        
    }
    
    public void menu(){
        while(gameRunning){
            WriteFiles.writeAllMenu(); //writes all missing files when run
            sm.display(); //display start menu
            try{
                int option = Integer.parseInt(input.getInput());
                switch(option){
                case 1 -> { //game start
                    // checks first if existing save fine exist (continue to start game if not available)
                    checkSaveFile();
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

                default -> ih.invalidInput("Only chose 1-3");
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
                Data userData = db.checkName(name, "default");
                if(userData.loginFlag) {
                    this.currentPlayerName = name;
                    this.currentPlayerScore = userData.currentScore;
                    break;
                } else {
                    ih.invalidInput("Database error occurred. Please try again.");
                }
            }
        }
        player = new Player(name); // creates player with name
        im.displayWelcome(name); // welcome player
        gd.displayGameGuide();
        input.getInput();
        encounter = new Encounter(player, input, eg, gd, ih, eui, log, cm);
        encounter.encountered(); // calls the first encounter
        }
    
    public void checkSaveFile(){
        // check if save files exist
        File saveFile = new File("./GameSaves/Game.sav");
        
        boolean fileExist = true;
        if(saveFile.exists()){
            while(fileExist){
                System.out.println("Save file exists! Would you like to delete and overwrite? (y/n) ");
                String overwrite = input.getInput();
                if(overwrite.equalsIgnoreCase("y") || overwrite.equalsIgnoreCase("yes")){
                    System.out.println("Deleting save...");
                    //delete existing save files
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
    
    // for clearing previous progress / record
    public void clearPlayerProgress(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PLAYERRECORD_PATH))) {
            bw.write("");
        } catch (IOException e) {
            System.out.println("Error writing combat log to file: " + e.getMessage());
        }
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PLAYERPROGRESS_PATH))) {
            bw.write("");
        } catch (IOException e) {
            System.out.println("Error writing combat log to file: " + e.getMessage());
        }
        
    }
    
    public void setGame(StartGame game) {
        this.game = game;
    }
    
    public void startGameWithName(String name, int score){
        this.currentPlayerName = name;
        this.currentPlayerScore = score;
        
        player = new Player(name);
        
        im.displayWelcome(name);
        gd.displayGameDetails();
        input.getInput();
        encounter = new Encounter(player, input, eg, gd, ih, eui, log, cm);
        encounter.encountered();
    }
    public void savePlayerScore(int finalScore){
        if(currentPlayerName != null){
            db.quitGame(finalScore, currentPlayerName);
            System.out.println(currentPlayerName +" scored " + finalScore);
        }
    }
    
    public String getCurrentPlayerName(){
        return currentPlayerName;
    }
    
    public int getCurrentPlayerScore(){
        return currentPlayerScore;
    }
}
