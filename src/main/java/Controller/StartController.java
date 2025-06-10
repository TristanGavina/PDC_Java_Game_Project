///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package Controller;
//import com.mycompany.java_game_project.GameUI.CombatLog;
//import com.mycompany.java_game_project.GameUI.CombatMenu;
//import com.mycompany.java_game_project.GameUI.EncounterUI;
//import com.mycompany.java_game_project.GameUI.EndGameUI;
//import com.mycompany.java_game_project.GameUI.GameDetails;
//import com.mycompany.java_game_project.GameUI.IntroductionMenu;
//import com.mycompany.java_game_project.GameUI.InvalidHandler;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import com.mycompany.java_game_project.GameUI.StartMenu;
//import com.mycompany.java_game_project.Interfaces.ICombatLog;
//import com.mycompany.java_game_project.Interfaces.ICombatMenu;
//import com.mycompany.java_game_project.Interfaces.IEncounterUI;
//import com.mycompany.java_game_project.Interfaces.IEndGame;
//import com.mycompany.java_game_project.Interfaces.IGameDetails;
//import com.mycompany.java_game_project.Interfaces.IIntroMenu;
//import com.mycompany.java_game_project.Interfaces.IInvalidHandler;
//import com.mycompany.java_game_project.Interfaces.IStartMenu;
//import com.mycompany.java_game_project.Interfaces.IUserInputs;
//import com.mycompany.java_game_project.StartGame;
//import com.mycompany.java_game_project.UserInputProvider;
//import java.io.File;
//import javax.swing.JOptionPane;
//
///**
// *
// * @author trist
// */
//public class StartController implements ActionListener{
//    StartMenu startMenu;
//    StartGame startG;
//
//    public StartController(){
//        IUserInputs input = new UserInputProvider();
//        IEndGame eg = new EndGameUI();
//        IStartMenu sm = new StartMenu();
//        IIntroMenu im = new IntroductionMenu();
//        IGameDetails gd = new GameDetails();
//        IInvalidHandler ih = new InvalidHandler();
//        IEncounterUI eui = new EncounterUI();
//        ICombatLog log = new CombatLog();
//        ICombatMenu cm = new CombatMenu();
//        
//        startMenu = new StartMenu(this);
//        
//    }
//    private void eventHandlers() {
//        // Get buttons from the view and add this controller as listener
//        startMenu.getStartButton().addActionListener(this);
//        startMenu.getLoadButton().addActionListener(this);
//        startMenu.getQuitButton().addActionListener(this);
//    }
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        String command = e.getActionCommand();
//        switch (command){
//            case "START GAME" -> startGame();
//            //case "LOAD GAME" -> loadGame();
//            case "QUIT GAME" -> quitGame();
//        }
//    }
//    
//    private void startGame(){
//        // check if save files exist
//        File saveFile = new File("./GameSaves/Game.sav");
//        boolean fileExist = true;
//        if(saveFile.exists()){
//            int choice = JOptionPane.showConfirmDialog(
//            startMenu,
//                    "Save file exists! Would you like to delete and overwrite?",
//                "Existing Save Found",
//                JOptionPane.YES_NO_OPTION
//            );
//            
//            if (choice == JOptionPane.YES_OPTION) {
//                startG.clearPlayerProgress();
//                //promptForPlayerName();
//            }
//        }
//    }
//    
//    private void quitGame(){
//        System.exit(0);
//    }
//    
//}
