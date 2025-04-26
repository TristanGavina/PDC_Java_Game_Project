/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */


package com.mycompany.java_game_project;
import java.io.*;

/**
 * need to work on implementing FILE I/O
 * 
 * @author trist
 */
    
public final class Java_Game_Project implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final UserInputs input;
    private Player player;
    private Encounter encounter;
    private boolean gameRunning;
    private final GameUI ui;
    static Java_Game_Project game;

    public Java_Game_Project(UserInputs input, GameUI ui) {
        
        this.input = input;
        gameRunning = true;
        this.ui = ui;
        
    }
    
    public void menu(){
        while(gameRunning){
            ui.startMenu();
            
            try{
                int option = Integer.parseInt(input.getInput());
                switch(option){
                case 1 -> startGame(); //game start

                case 2 -> {
                    SaveHandler.loadGame();
                    if(SaveHandler.game != null && SaveHandler.game != this) {
                        SaveHandler.game.menu();
                        return;
                    } else {
                        ui.loadError("No saved game found or error loading game.");
                    }
                }

                case 3 -> {
                    gameRunning = false;
                    ui.quitGame(); // quit game
                }

                default -> ui.invalidInput("Only chose 1-3"); // error message
                }   
            } catch(NumberFormatException e){
                ui.invalidInput(e.getMessage());
            }
        }
    }
        
    private void startGame(){ 
        ui.playerIntro(); //show intro and ask player name
        String name = input.getInput(); // get user input
        player = new Player(name); // store player name
        ui.playerName(name); // show player name
        encounter = new Encounter(player, input, ui); // instantiate encounter class
        encounter.encountered(); // call encountered method in encounter class
        }
    
    public static void main(String[] args) {
        UserInputs userInput = new UserInputProvider();
        GameUI ui = new GameUI();
        game = new Java_Game_Project(userInput, ui); // Initialize the static reference
        SaveHandler.game = game; // Link the SaveHandler to this game instance
        game.menu();
    }
    
    public void resumeGame() {
    // This method will be called when a saved game is loaded
    if (player != null && encounter != null) {
        ui.resumeGame(player, encounter);
        input.getInput();
        encounter.encountered();
    } else {
        ui.loadError("Could not resume game. Start new game...");
        menu();
    }
}
}

