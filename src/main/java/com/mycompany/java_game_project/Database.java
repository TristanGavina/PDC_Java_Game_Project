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
    String url = "jdbc:derby:GameDB;create=true";
    
    String dbusername = "APP";
    String dbpassword = "";
    
    public void dbsetup(){
        try{
            conn = DriverManager.getConnection(url, dbusername, dbpassword);
            Statement statement = conn.createStatement();
            String tableName = "USERINFO";
            
            if(!checkTableExisting(tableName)){
                String createTableSQL = "CREATE TABLE " + tableName + " (userid VARCHAR(12), password VARCHAR(12), score INT)";
                statement.executeUpdate(createTableSQL);
                System.out.println("Table " + tableName + " created successfully.");
            } else {
                System.out.println("Table " + tableName + " already exists.");
            }
            statement.close();
        } catch (SQLException e){
            System.out.println("Unable to create database table: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public Data checkName(String username, String password) {
        Data data = new Data();
        
        String selectSQL = "SELECT userid, password, score FROM USERINFO WHERE userid = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String pass = rs.getString("password");
                System.out.println("***" + pass);
                System.out.println("found user");
                if (password.equals(pass)) {
                    data.currentScore = rs.getInt("score");
                    data.loginFlag = true;
                } else {
                    data.loginFlag = false;
                }
            } else {
                System.out.println("no such user");
                System.out.println("creating new user");
                
                String insertSQL = "INSERT INTO USERINFO (userid, password, score) VALUES (?, ?, ?)";
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {
                    insertStmt.setString(1, username);
                    insertStmt.setString(2, password);
                    insertStmt.setInt(3, 0);
                    insertStmt.executeUpdate();
                    data.loginFlag = true;
                } catch (SQLException insertEx) {
                    System.out.println("Error creating new user: " + insertEx.getMessage());
                    data.loginFlag = false;
                }
            }
            rs.close();
            
        } catch (SQLException ex) {
            System.out.println("Database error in checkName: " + ex.getMessage());
            ex.printStackTrace();
            Logger.getLogger(Java_Game_Project.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    private boolean checkTableExisting(String newTableName) {
        boolean flag = false;
        try {
            System.out.println("check existing tables.... ");
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rsDBMeta = dbmd.getTables(null, "APP", newTableName.toUpperCase(), new String[]{"TABLE"});
            
            while (rsDBMeta.next()) {
                String tableName = rsDBMeta.getString("TABLE_NAME");
                System.out.println("Found table: " + tableName);
                if (tableName.equalsIgnoreCase(newTableName)) {
                    System.out.println(tableName + " is there");
                    flag = true;
                    break;
                }
            }
            
            if (rsDBMeta != null) {
                rsDBMeta.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error checking table existence: " + ex.getMessage());
            ex.printStackTrace();
        }
        return flag;
    }
    
    public void quitGame(int score, String username){
        String updateSQL = "UPDATE USERINFO SET score = ? WHERE userid = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setInt(1, score);
            pstmt.setString(2, username);
            int rowsUpdated = pstmt.executeUpdate();
            
            if (rowsUpdated > 0) {
                System.out.println("Score updated successfully for " + username + ": " + score);
            } else {
                System.out.println("No user found with username: " + username);
            }
            
        } catch (SQLException ex) {
            System.out.println("Error updating score: " + ex.getMessage());
            ex.printStackTrace();
            Logger.getLogger(Java_Game_Project.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException ex) {
            System.out.println("Error closing database connection: " + ex.getMessage());
        }
    }
    
    //Establish connection
    public void establishConnection() {
        try {
            // Load the Derby client driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            // Establish the connection
            conn = DriverManager.getConnection(url, dbusername, dbpassword);
            System.out.println("Connection established successfully.");

        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database: " + e.getMessage());
        }
    }
    
    public ResultSet queryDB(String sql) {

        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return resultSet;
    }

    public void updateDB(String sql) {

        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}