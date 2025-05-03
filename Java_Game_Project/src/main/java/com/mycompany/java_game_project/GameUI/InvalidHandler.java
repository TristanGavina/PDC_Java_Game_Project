/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project.GameUI;

import com.mycompany.java_game_project.Interfaces.IInvalidHandler;
import java.io.Serializable;

/**
 *
 * @author trist
 */
public class InvalidHandler implements Serializable, IInvalidHandler{
    private static final long serialVersionUID = 1L;
    
    @Override
    public void invalidInput(String msg){
        System.out.println("Invalid Input: " + msg);
    }
    @Override
    public void saveError(String save){
        System.out.println("There seems to be a problem saving the game..." + save);
    }
    @Override
    public void loadError(String load){
        System.out.println("There seems to be a problem loading the game..." + load);
    }
    
    
    
}
