/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mycompany.java_game_project.GameUI.StartMenu;
import com.mycompany.java_game_project.Java_Game_Project;
import com.mycompany.java_game_project.StartGame;

/**
 *
 * @author trist
 */
public class StartController implements ActionListener{
    StartMenu startMenu;
    Java_Game_Project start;

    public StartController(){
        startMenu = new StartMenu();
        //start = new StartGame(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
}
