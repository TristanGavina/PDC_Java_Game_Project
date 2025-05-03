/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.java_game_project;

import com.mycompany.java_game_project.Interfaces.IUserInputs;
import java.io.*;
import java.util.Scanner;

/**
 *
 * @author trist
 */
public class UserInputProvider implements IUserInputs, Serializable {
        private static final long serialVersionUID = 1L;

    private transient Scanner scan;
    
    public UserInputProvider(){
        this.scan= new Scanner(System.in);
    }
    
    @Override
    public String getInput(){
        if (scan == null) {
            scan = new Scanner(System.in);
        }
        return scan.nextLine().trim();
    }
}
