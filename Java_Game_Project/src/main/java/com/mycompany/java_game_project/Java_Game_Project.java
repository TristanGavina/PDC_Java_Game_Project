/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */


package com.mycompany.java_game_project;
import com.mycompany.java_game_project.GameUI.CombatLog;
import com.mycompany.java_game_project.GameUI.GameUI;
import com.mycompany.java_game_project.GameUI.GuideAndDetails;
import com.mycompany.java_game_project.GameUI.IntroductionMenu;
import com.mycompany.java_game_project.GameUI.InvalidHandler;
import com.mycompany.java_game_project.GameUI.StartMenu;
import com.mycompany.java_game_project.GameUI.EncounterUI;
import java.io.*;

/**
 *
 * THIS IS THE MAIN CLASS
 * @author trist
 */
    
public final class Java_Game_Project implements Serializable {
    private static final long serialVersionUID = 1L;
    
    final UserInputs input;
    Player player;
    Encounter encounter;
    private boolean gameRunning;
    final GameUI ui;
    private final StartMenu sm;
    private final IntroductionMenu im;
    private final GuideAndDetails gd;
    final InvalidHandler ih;
    private final EncounterUI encounterUI;
    private final CombatLog log;
    static Java_Game_Project game;
    

    public Java_Game_Project(UserInputs input, GameUI ui, StartMenu sm, IntroductionMenu im, GuideAndDetails gd, InvalidHandler ih, EncounterUI encounterUI, CombatLog log) {
        this.input = input;
        this.gameRunning = true;
        this.ui = ui;
        this.sm = sm;
        this.im = im;
        this.gd = gd;
        this.ih = ih;
        this.encounterUI = encounterUI;
        this.log = log;
    }
    
    public void menu(){
        while(gameRunning){
            sm.startMenu();
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
                    ui.quitGame(); // quit game
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
            im.playerIntro(); //show intro and ask player name
            name = input.getInput().trim(); // get user input
            if(name.length() < 3 || name.length() > 7){
                ih.invalidInput("Sorry name must be 3 to 7 letters only...");
            } else {
                break;
            }
        }
        
        player = new Player(name); // store player name
        im.welcomePlayer(name); // show player name
        gd.gameGuide();
        input.getInput();
        encounter = new Encounter(player, input, ui, gd, ih, encounterUI, log);
        encounter.encountered(); // call encountered method in encounter class
        }
    
    public static void main(String[] args) {
        UserInputs userInput = new UserInputProvider();
        GameUI ui = new GameUI();
        StartMenu sm = new StartMenu();
        IntroductionMenu im = new IntroductionMenu();
        GuideAndDetails gd = new GuideAndDetails();
        InvalidHandler ih = new InvalidHandler();
        EncounterUI encounterUI = new EncounterUI();
        CombatLog log = new CombatLog();
        game = new Java_Game_Project(userInput, ui, sm, im, gd, ih, encounterUI, log);
        SaveHandler.game = game; // Link the SaveHandler to this game instance
        game.menu();
    }
    

    private void checkSaveFile(){
        //check if save files exist
        File saveFile = new File("./GameSaves/Game.sav");
        File playerRecord = new File("./GameSaves/playerRecord.txt");
        File playerProgress = new File("./GameSaves/playerProgress.txt");
        boolean fileExist = true;
        if(saveFile.exists()){
            while(fileExist){
                System.out.println("Save file exists! Would you like to delete and overwrite? (y/n) ");
                String overwrite = input.getInput();
                if(overwrite.equalsIgnoreCase("y") || overwrite.equalsIgnoreCase("yes")){
                    System.out.println("Deleting save...");
                    //delete all related save files
                    saveFile.delete();
                    playerRecord.delete();
                    playerProgress.delete();
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
}

