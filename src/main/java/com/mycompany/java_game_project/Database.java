/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_game_project;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author trist
 */
public class Database {
    Connection conn = null;
    String url = "jdbc:derby:PlayerDB;create=true";
    
    String dbusername = "game";
    String dbpassword = "game";
    
    public void dbsetup(){
        try{
            conn = DriverManager.getConnection(url, dbusername, dbpassword);
            Statement statement = conn.createStatement();
            String tableName = "UserInfo";
            
            if(!checkTableExisting(tableName)){
                statement.executeUpdate("CREATE TABLE " + tableName + " (userid VARCHAR(12), playerhp INT, playerdef INT, playerattack INT");
            }
            statement.close();
        } catch (Throwable e){
            System.out.println("Unable to create database table");
        }
    }
    
    Data checkName(String username, String password) {
        Data data = new Data();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT userid, password, score FROM UserInfo "
                    + "WHERE userid = '" + username + "'");
            if (rs.next()) {
                String pass = rs.getString("password");
                System.out.println("***" + pass);
                System.out.println("found user");
                if (password.compareTo(pass) == 0) {
                    data.currentScore = rs.getInt("score");
                    data.loginFlag = true;
                } else {
                    data.loginFlag = false;
                }
            } else {
                System.out.println("no such user");
                System.out.println("creating new user");
                statement.executeUpdate("INSERT INTO UserInfo "
                        + "VALUES('" + username + "', '" + password + "', 0)");
                data.loginFlag = true;

            }

        } catch (SQLException ex) {
            Logger.getLogger(Java_Game_Project.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    private boolean checkTableExisting(String newTableName) {
        boolean flag = false;
        try {

            System.out.println("check existing tables.... ");
            String[] types = {"TABLE"};
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rsDBMeta = dbmd.getTables(null, null, null, null);//types);
            //Statement dropStatement=null;
            while (rsDBMeta.next()) {
                String tableName = rsDBMeta.getString("TABLE_NAME");
                if (tableName.compareToIgnoreCase(newTableName) == 0) {
                    System.out.println(tableName + "  is there");
                    flag = true;
                }
            }
            if (rsDBMeta != null) {
                rsDBMeta.close();
            }
        } catch (SQLException ex) {
        }
        return flag;
    }
    
    public void quitGame(int score, String username){
        Statement statement;
        try {
            statement = conn.createStatement();
            statement.executeUpdate("UPDATE UserInfo SET score=" + score + " WHERE userid='" + username + "'");
            System.out.println(username + score);
        } catch (SQLException ex) {
            Logger.getLogger(Java_Game_Project.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
