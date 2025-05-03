/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project.GameUI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author trist
 */

/*
This class purpose is to make sure all folders and txt files are provided in case of problem in transferring (can ignore if all is working)
*/
public class WriteFiles {

    //make sure GameSaves saveFolder is created
    public static void makeSaveFolder() {
        File saveFolder = new File("./GameSaves");
        if (!saveFolder.exists()) {
            saveFolder.mkdir();
        }
    }
    
    private static void makeUIFolder(){
        File UIFolder = new File("./GameUI_text_files");
        if(!UIFolder.exists()){
            UIFolder.mkdir();
        }
    }
    
    //writes default UI if its not given
    public static void writeAllMenu(){
        makeUIFolder();
        makeSaveFolder();
        writeStartMenu();
        writeIntro();
        writeHelpMenu();
        writeCombatMenu();
        writeHelpFile();
        writeContinueMenu();
        writeRestMenu();
        writeGameFinish();
        writeGameOver();
    }
    
    /*
    * This method makes sure the current UI txt does not get overwritten
    * this allows users to customize the UI to however they like without it getting overwritten
    */
    private static boolean isFileEmpty(String filePath) {
        File checkEmpty = new File(filePath);
        if(checkEmpty.length() == 0){
            return true;
        }
        //This checks if the "empty: file contains only white spaces: returns as null when it is
        try (BufferedReader reader = new BufferedReader(new FileReader(checkEmpty))) {
            String line = reader.readLine();
            return line == null || line.trim().isEmpty();
        }catch (IOException e) {
            System.out.println("Error checking if file is empty: " + e.getMessage());
            return false;
        }
    }
    
    //Writes the file if said file and content is missing
    private static void writeIfMissing(String filePath, String UI){
        File checkMissing = new File(filePath);
        if (!checkMissing.exists() || isFileEmpty(filePath)) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(checkMissing))) {
                bw.write(UI);
            } catch (IOException e) {
                System.out.println("Could not write file: " + filePath + " - " + e.getMessage());
            }
        }
    }
    
    private static void writeStartMenu(){
        String UI = ("""
                     |=============================================|
                     |                                             |
                     |             PDC CUI RPG GAME                |
                     |_____________________________________________|
                     |                                             |
                     |       Press [1] START NEW GAME              |
                     |                                             |
                     |       Press [2] LOAD GAME                   |
                     |                                             |
                     |       Press [3] QUIT GAME                   |
                     |_____________________________________________|
                     |                  BEWARE!!!                  |
                     |    Starting new game will overwrite save!!! |
                     |=============================================|
                     |           Start your adventure!!!           |
                     |=============================================|
                     |_____________________________________________|
                     """);
        writeIfMissing("./GameUI_text_files/startMenu.txt", UI);
    }
    
    private static void writeIntro(){
        String UI = ("""
                      _____________________________________________
                     |                                             |
                     |=============================================|
                     |      Welcome to Tristan PDC project 1!      |
                     | A turn-based game to defeat the final boss! |
                     |=============================================|
                     |_____________________________________________|
                     |                                             |
                     |   Please enter your name (3 - 7 letters):   |
                     |_____________________________________________|
                     """);
        writeIfMissing("./GameUI_text_files/playerIntro.txt", UI);
    }
    
    private static void writeHelpMenu(){
        String UI = ("""
                     |=============================================|
                     |                                             |
                     |      Press [4] during combat for help and   |
                     |    information about the game and stages.   |  
                     |                                             |
                     |                  BEWARE!!!                  |
                     |    Starting new game will overwrite save!!! |
                     |_____________________________________________|
                     |                                             |
                     |           Press ENTER to continue...        |
                     |_____________________________________________|
                     """);
        writeIfMissing("./GameUI_text_files/gameHelp.txt", UI);
    }
    
    private static void writeCombatMenu(){
        String UI =("""
                     |=============================================|
                     |                                             |
                     |              CHOOSE ACTION!!!               |
                     |_____________________________________________|
                     |                                             |
                     |       Press [1] ATTACK                      |
                     |                                             |
                     |       Press [2] DEFEND (+50 Defense)        |
                     |                                             |
                     |       Press [3] HEAL                        |
                     |                                             |
                     |       Press [4] DETAILS                     |
                     |_____________________________________________|
                     """);
        writeIfMissing("./GameUI_text_files/combatMenu.txt", UI);
    }
    
    private static void writeHelpFile(){ 
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./GameUI_text_files/help.txt"))) {
            bw.write("""
                     === GAME DETAILS AND FEATURES ==
                       - This game is only playable using numbers and the usual yes and no.
                         - During combat invalid inputs results in enemies getting a free move on you.
                       - The game has a save and load feature using serialization saving the game object and state.
                       - The game UI's is all stored in text file, so its highly customizable.
                         - Game UI is customizable but game controls are not, it will still only take numbers (1-4) and y/n.
                       - Every stage and/or enemy defeat the player's record is saved, which is loaded when loading game.
                     
                     == STAGE DETAILS ==
                       - 6 stages that gets progressively harder.
                       - Previous stage enemies will reappear in later stages.
                     
                     CONTROLS/OPTIONS Explained:
                       [START MENU]
                         - [1] to START NEW GAME (will ALWAYS overwrite previous save when quitting)
                         - [2] to LOAD GAME (load previous game state)
                         - [3] to QUIT GAME
                       [COMBAT]
                         - [1] to ATTACK
                         - [2] to DEFEND (+50 defense for 1 turn)
                         - [3] to HEAL (+10 HP)
                         - [4] to VIEW game details
                       [AFTER ENEMY DEFEAT]
                         - [1] to CONTINUE next enemy or stage
                         - [2] to REST
                         - [3] to SAVE AND QUIT
                       [REST OPTIONS]
                         - [1] to CONTINUE
                         - [2] to SHOW PLAYER RECORD (show stat and current floor)
                         - [3] to SAVE AND QUIT
                     
                     === TIPS ===
                       - Resting saves the game.
                       - Enemies takes reduced damage depending on their defence.
                       - Enemies only attack so keep track of your health and plan your moves wisely.
                       - Enemy and Player stats can be shown below combat menu / options.
                       - Defending will increase defence for 1 turn.
                       - Can only heal when below max health.
                       - Healing will not stop enemies attack, use wisely.   
                       - Always REST after battle, give you more time to plan and prepare.
                     
                     === Press ENTER to continue ===
                     
                     """);
    } catch (IOException e){
            System.out.println("Could not write help file. Please contact developer " +e.getMessage());
        }
    }
    
    private static void writeContinueMenu(){
        String UI = ("""
                     |=============================================|
                     |                                             |
                     |        WOULD YOU LIKE TO CONTINUE?          |
                     |_____________________________________________|
                     |                                             |
                     |       Press [1] CONTINUE                    |
                     |                                             |
                     |       Press [2] REST                        |
                     |                                             |
                     |       Press [3] QUIT AND SAVE               |
                     |_____________________________________________|
                     """);
        writeIfMissing("./GameUI_text_files/playerContinue.txt", UI);
    }
    
    private static void writeRestMenu(){
        String UI = ("""
                     |=============================================|
                     |            RESTING TO FULL HP...            |
                     |            Would you like to:               |
                     |_____________________________________________|
                     |                                             |
                     |       Press [1] CONTINUE                    |
                     |                                             |
                     |       Press [2] SHOW RECORD                 |
                     |                                             |
                     |       Press [3] QUIT AND SAVE               |
                     |_____________________________________________|
                     """);
        writeIfMissing("./GameUI_text_files/restingMenu.txt", UI);
    }
    
    private static void writeGameFinish(){
        String UI = ("""
                     |=============================================|
                     |                                             |
                     |          You have reached the end           |
                     |          of your journey, there will        |
                     |          be more to come, but for now       |
                     |              rest adventurer                |
                     |                                             |
                     |=============================================|
                     |                                             |
                     |    CONGRATULATIONS ON BEATING THE GAME!!!   |
                     |                                             |
                     |=============================================|
                     """);
        writeIfMissing("./GameUI_text_files/gameFinish.txt", UI);
    }
    
    private static void writeGameOver(){
        String UI = ("""
                     |=============================================|
                     |                                             |
                     |                YOU DIED!!!                  |
                     |                                             |
                     |=============================================|
                     |                GAME OVER!!!                 |
                     |                                             |
                     |       You are welcome to try again          |
                     |_____________________________________________|
                     """);
        writeIfMissing("./GameUI_text_files/gameOver.txt", UI);
    }
}
