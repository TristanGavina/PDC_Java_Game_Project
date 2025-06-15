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
            
            String gamesTable = "GAMESESSIONS";
            if(!checkTableExisting(gamesTable)){
                String createTableSQL = "CREATE TABLE " + gamesTable + " (" +
                        "gameid INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "+
                        "userid VARCHAR(12), "+
                        "currentscore INT, " +
                        "currentstage INT, " +
                        "enemiesdefeated INT, " +
                        "gamestart TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "+
                        "gameend TIMESTAMP, " +
                        "gamestatus VARCHAR(20) DEFAULT 'IN_PROGRESS'" +
                        ")";
                statement.executeUpdate(createTableSQL);
                System.out.println("Table " + gamesTable + " created successfully.");
            } else {
                System.out.println("Table " + gamesTable + " already exists.");
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
    
    //starting new game
    public int startGame(String username){
        String insertSQL = "INSERT INTO GAMESESSIONS (userid, currentscore, currentstage, enemiesdefeated) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)){
            pstmt.setString(1, username);
            pstmt.setInt(2, 0); //score
            pstmt.setInt(3, 1); //stage
            pstmt.setInt(4, 0); //enemies defeated
            
            int rowsAffected = pstmt.executeUpdate();
            
            if(rowsAffected > 0){
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if(generatedKeys.next()){
                    int gameId = generatedKeys.getInt(1);
                    System.out.println("New game started for " + username + " with game ID: " + gameId);
                    return gameId;
                }
            }
        } catch (SQLException ex){
            System.out.println("Error starting game session: " + ex.getMessage());
            ex.printStackTrace();
        }
        return -1;
    }
    
    // updating game in real-time progress
    public void updateGame(String username, int currentScore, int currentStage, int enemiesDefeated) {
        String updateSQL = "UPDATE GAMESESSIONS SET currentscore = ?, currentstage = ?, enemiesdefeated = ? " +
                          "WHERE userid = ? AND gamestatus = 'IN_PROGRESS'";
        try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setInt(1, currentScore);
            pstmt.setInt(2, currentStage);
            pstmt.setInt(3, enemiesDefeated);
            pstmt.setString(4, username);
            
            int rowsUpdated = pstmt.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println("Error updating game: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    //ending game and updating final score
    public void endGame(String username, int finalScore, String gameStatus) {
        String endSQL = "UPDATE GAMESESSIONS SET gameend = CURRENT_TIMESTAMP, gamestatus = ? " +
                              "WHERE userid = ? AND gamestatus = 'IN_PROGRESS'";
        try (PreparedStatement pstmt = conn.prepareStatement(endSQL)) {
            pstmt.setString(1, gameStatus);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
            
            System.out.println("Game session ended for " + username + " with status: " + gameStatus);
            
        } catch (SQLException ex) {
            System.out.println("Error ending game session: " + ex.getMessage());
            ex.printStackTrace();
        }
        
        highScore(username, finalScore);
    }
    
    //updating highscore
    private void highScore(String username, int newScore){
        String highscoreSQL = "SELECT score FROM USERINFO WHERE userid = ?";
        
        try (PreparedStatement hsStmt = conn.prepareStatement(highscoreSQL)){
            hsStmt.setString(1,username);
            ResultSet rs = hsStmt.executeQuery();
            
            if(rs.next()){
                int currentHS = rs.getInt("score");
                
                //will update if new score is higher
                if(newScore > currentHS){
                    String updateSQL = "UPDATE USERINFO SET score = ? WHERE userid = ?";
                    
                    try(PreparedStatement updateStmt = conn.prepareStatement(updateSQL)){
                        updateStmt.setInt(1,newScore);
                        updateStmt.setString(2, username);
                        
                        int rowsUpdated = updateStmt.executeUpdate();
                        
                        if(rowsUpdated > 0){
                            System.out.println("NEW HIGH SCORE! Updated " + username + "'s high score from " + currentHS + " to " + newScore);
                        }
                    }
                } else {
                    System.out.println("Score " + newScore + " did not beat current high score");
                }
            }
            rs.close();
        } catch (SQLException ex){
            System.out.println("Error updating high score: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public void userStats(String username){
        String statsSQL = "SELECT " +
                "COUNT(*) as totalGames, " +
                "MAX(currentscore) as highScore, " +
                "MAX(currentstage) as highestStage, " +
                "SUM(enemiesdefeated) as totalEnemiesDefeated, " +
                "COUNT(CASE WHEN gamestatus = 'COMPLETED' THEN 1 END) as gamesCompleted " +
                "FROM GAMESESSIONS WHERE userid = ?";
        
        try(PreparedStatement pstmt = conn.prepareStatement(statsSQL)){
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                System.out.println("\n=== PLAYER STATISTICS FOR " + username + " ===");
                System.out.println("Total Games Played: " + rs.getInt("totalGames"));
                System.out.println("High Score: " + rs.getInt("highScore"));
                System.out.println("Highest Stage Reached: " + rs.getInt("highestStage"));
                System.out.println("Total Enemies Defeated: " + rs.getInt("totalEnemiesDefeated"));
                System.out.println("Games Completed: " + rs.getInt("gamesCompleted"));
                System.out.println("=======================================\n");
            }
            rs.close();
            
        } catch (SQLException ex) {
            System.out.println("Error getting user stats: " + ex.getMessage());
            ex.printStackTrace();
        }
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