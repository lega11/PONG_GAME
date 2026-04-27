# PONG_GAME

Pong Game (JavaFX Project)

Overview

This project is a JavaFX-based Pong game developed as part of a software engineering assignment. The game includes real-time gameplay, configurable settings, database saving/loading, serialization, and memory testing.

The aim of the project was to apply object-oriented design principles and software design patterns such as Singleton, DAO, and Builder, while also demonstrating good code structure and documentation.


Features


Core Gameplay
Two-player Pong game

Paddle movement using keyboard controls

Ball collision with paddles and walls

Score tracking system

Winning condition with game-over message


Game Settings
Set player names

Adjust ball speed

Change paddle size (width & height)

Set winning score

Speed increases after X bounces

“Total Mayhem” mode (random paddle + ball changes)
Game Controls

ENTER → Start game

ESC → Pause game

Restart functionality included

Save / Load (Serialization)

Game state can be saved to a file

Game state can be loaded back and resumed

Implemented using a Singleton Serialization Service

Error handling with try-catch and user alerts

Database (JDBC)

Saves game data to MySQL database

Loads the most recent saved game

Stores:

Player names

Scores

Winning score

Paddle size

Design Patterns Used:

Singleton → Database connection

DAO (Data Access Object) → Handles DB operations

Builder Pattern → Reconstructs game objects from DB


 Memory Experiment
 
Heap memory test (creates large objects until crash)

Stack memory test (recursive calls until overflow)
Tracks:

Number of objects created

Time until failure

Performance slowdown (throttling point)

Unit Testing

Unit tests created for collision detection

Ensures ball correctly interacts with paddles

Project Structure

helloworld

 ├── controller    (Game logic & threads)
 
 ├── model         (Game data & state)
 
 ├── view          (UI components)
 
 ├── dao           (Database access)
 
 ├── db            (Database connection - Singleton)
 
 ├── builder       (GameBuilder pattern)
 
 ├── service       (Serialization)
 
 └── memory        (Memory experiment)


Technologies Used
Java 21

JavaFX

Maven

MySQL

JUnit 5


Database Setup

1. Create database:
CREATE DATABASE PongDB;
USE PongDB;

3. Create table:
   
CREATE TABLE Game (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Game_Name VARCHAR(50),
   
    Player_1_Name VARCHAR(50),
   
    Player_2_Name VARCHAR(50),
   
    Player_1_Score INT,
   
    Player_2_Score INT,
   
    Score_Limit INT,
   
    Paddle_Width DOUBLE,
   
    Paddle_Height DOUBLE

);
3. Connection string:

jdbc:mysql://127.0.0.1:3306/PongDB

How to Run
Using Maven:

mvn javafx:run

Or via IntelliJ:

Run HelloWorld.java

Memory Experiment

Run:

MemoryExperiment.main()

You can adjust JVM settings:

-Xmx512m   (heap size)
-Xss1m     (stack size)
