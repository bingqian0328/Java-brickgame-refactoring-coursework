# Refactoring Brick breaker game JAVA(COMP2042_CW_hfybl3)
Name: Lim Bing Qian

Student ID: 20408309

# Compilations Instructions
1) Ensure you have a JAVA IDE installed on your computer (Intellij, Eclipse, Visual Studio Code,etc.)
2) Download the zip file from this repository and unzip the zip file
3) Open the folder unzipped in your JAVA IDE (it is Intellij in my case) by following these steps:
   
    ```File -> Open -> (file saved location)/COMP2042_CW_hfybl3```
4) Ensure that Javafx library is already installed and setup in your JAVA IDE
   
   ```IDE and Project Settings -> Project Structure -> Libraries -> Check if Javafx library is already imported```
5) Run Main Class and the game will start

   ```CourseworkGame-master[LabTest2] -> src -> main -> java -> brickGame -> Main -> Run the program```
   
# Implemented and working properly
### 1. New Block Types: Flash Block and Hide Paddle Block

In this game, two new types of blocks are created in Block class,Flash Block and Hide Paddle Block. It adds strategic depth and surprises to the gameplay. These blocks bring unique effects that can significantly impact the course of the game.

#### Flash Block

- **Description:** Flash blocks provide a temporary 5 second speed boost to the ball when hit and it changes the ball's position. This boost ball feature makes the ball moves twice as fast as its normal speed, making it move faster and potentially increasing the difficulty of the game. Besides that, once the ball hits the flash block, the ball will disappear from its initial position and pops up in a new position on top like a flash. It can be categorised as a reward block because it speeds up the ball's speed and increases the number of blocks hit in a certain amount of time. This flash block is available in all levels. 

- **Activation:**
  - When the ball collides with a Boost Block, the boost and flash effect is triggered.
  - The boost status lasts for 5 seconds.
 
<img src="https://github.com/bingqian0328/COMP2042_CW_hfybl3/blob/main/src/main/resources/flash.png?raw=true" width="230">
 
#### Hide Paddle Block

- **Description:** Hide paddle blocks only appear at the final challenge level 18, this hide paddle feature lets the paddle go invisible for 5 seconds, user will have to predict the position of the paddle and move the paddle to let the ball drops on the paddle, else the ball will drop on the ground and decreases heart by 1. This hide paddle block is categorised as a penalty block as it increases the difficulty of the final level 18.

- **Activation:**
  - When the ball collides with a Hide Paddle Block, the hide paddle effect is triggered and the paddle will go invisible.
  - The hide paddle status lasts for 5 seconds.

<img src="https://github.com/bingqian0328/COMP2042_CW_hfybl3/blob/main/src/main/resources/disappear.png?raw=true" width="250">

### 2. New Level

In this game, additional new level of level 18 is added in Controller class to increase the difficulty of the game. 

#### Level 18
- In this level, a new row of blocks are added and hide paddle blocks are implemented here. A new row of blocks makes the game harder by increasing the chance of the ball hitting the ground and leads to a decrement in heart count. Then, the hide paddle block is introduced above, it makes the paddle invisible for 5 seconds once the hide paddle block is hit, user will have predict the position of the paddle and control paddle to try to not let the ball drops on the ground so the heart count doesn't decrease.

### 3. Pause game function
- In this game, pause game feature is also implemented into the game. Once the key 'P' is pressed, the game will pause, the ball will stop moving and freeze at its initial position until the unpause key 'U' is pressed, only the game will continue running.

- **Activation:**
   - Once the key 'P' is pressed, the pause game effect is trigerred and the game will pause.
   - Once the key 'U' is pressed, the pause game effect will be deactivated and the game will continue.


### 4. Background music
- In this game, background music is implemented into the game. Once the 'Start New Game' button is pressed, the background music will start playing. It is an one hour audio of Wii game theme background song playing.

 - **Activation:**
   - Once 'Start New Game' button is pressed, background music will start playing until game terminates.

[Wii game theme song Youtube link](https://www.youtube.com/watch?v=LYN6DRDQcjI&t=132s)

# Implemented but not working properly

### 1.Changed rebound logic so that the ball rebounds to another direction when it hits the edge of a block

- **Description:** At the beginning, when debugging the problem of the ball not rebounding to another direction when it hits certain positions of the block, most of the positions not giving a rebound effect to the ball were solved by making changes to checkHitToBlock method in Block Class but the problem of the ball not rebounding to another direction when it hits the left bottom edge corner of the block was the most obvious problem as the ball still goes in the same direction after hitting and destroying the left bottom edge corner of the block and it couldn't be solved by just making changes to checkHitToBlock. So, I implemented another handleBottomLeftBlockCorner method in setPhysicstoBall to solve this issue.

```Java
   public void handleBottomLeftBlockCorner(Ball bball, Paddle paddle) {
        double cornerDistance = Math.sqrt(Math.pow(bball.getXb() - paddle.getX(), 2) + Math.pow(bball.getYb() - paddle.getY() - paddle.getHeight(), 2));
        if (cornerDistance <= bball.getRadius()) {
            bball.bounceHorizontally(); //ball go left
            bball.bounceDown(); //ball go down
        }
    }
```
  
 - **Not working properly:** Even though the problem can be considered solved, but sometimes when the ball barely hits and slides past the block,it destroys the block but it doesn't create a rebound effect to the ball.

# Features not implemented 

### 1. Additional ball

A second ball was planned to be added to the final level 18 to increase the difficulty of the game, however it wasn't implemented because of the following reasons:
     
- requires additional logic for collision detection with the paddle, blocks, walls, and potentially other game elements. Managing collisions for multiple balls simultaneously increases the complexity of the collision-handling algorithm.
- adapting to elements such as heart blocks and gold block to work seamlessly with multiple balls requires careful consideration. Ensuring that each ball interacts correctly with these game features adds complexity to the overall game logic.

### 2. multiplayer mode

Multiplayer mode was planned to be added by adding a second paddle as one of the options to play the brickbreaker game. however it wasn't implemented because of the following reasons: 

- Managing latency and ensuring a smooth multiplayer experience, especially in real-time gameplay, is a significant challenge. Minimizing delays between player inputs and game responses is crucial for an enjoyable multiplayer experience.
- It decreases the difficulty of the game because adding a new paddle means the horizontal space available for the ball to drop below the paddle and chances to reach the ground decreases, making it more easy for user to win the game and therefore causes the game to be less interesting.

# New Java Classes

### 1. Ball.java class
A Ball class was created to represent the ball object used in the game. 

- Variables related to the attributes and properties of the ball were created in there such as: 

```Java
    private double xb; //represent x coordinate of the ball
    private double yb; //represent y coordinate of the ball
    private final double radius; //represent radius of the ball
    private double veloX; //represent velocity of ball in x direction
    private double veloY; //represent velocity of ball in y direction
    private boolean goDown; // boolean indicating direction of movement for the ball
    private boolean goRight; //boolean indicating direction of movement for the ball
```

- Constructor was implemented to initialise the ball with certain properties values
- Setters and getters for the variables were also created to provide methods to retrieve and modify the ball's attributes, such as position, velocity, radius, and movement direction.
- Methods like isGoingDown(), isGoingRight(), etc., also provide convenient ways to check the current direction of the ball.
- The method updatePostion() was also created to update the position of the ball based on its current velocity and direction.

### 2. Paddle.java class 
This Paddle class was created to represent the paddle object in the game, it encapsulates the properties and functionality of a paddle, providing methods for its position manipulation, and retrieval of important attributes.

- Variables related to the attributes and properties of the paddle were created in there such as:

```Java
    private double x; // x-coordinate of the paddle
    private double y; // y-coordinate of the paddle
    private int width; //width of the paddle
    private int height; //height of the paddle
    private int halfWidth; //half of paddle's width, used for ball rebounding calculations
```

- The constructor initializes the Paddle object with specified starting coordinates (x and y) and dimensions (width and height). It also calculates and sets halfWidth based on the given width.
- Setters and getters for the variables were also created to provide methods to retrieve and modify the paddle's attributes. 

### 3. Model.java class

To refactor the code into MVC pattern, I first started with Model class which is a class that is mainly responsible for handling the game logic of this brickbreaker game. 

- **Here are briefly what the model class has:**
   - The initBoard method which is responsible for calculating and initializing the game board with blocks.
   - The initBall method that initializes the game ball object.
   - THe initBreak method that initializes the paddle.
   - Methods that relate to setting physics to the ball, collision detection and handling in a game, it collectively handle various collision scenarios in the game, ensuring that the ball behaves appropriately when interacting with the         paddle, walls, and blocks.
   - resetGame method that reset the game parameters when restart button is clicked.
   - loadGameData method that is responsible for loading out the previously saved game parameters and data.
   - The checkhitcode method is responsible for updating collision flags based on a provided hitCode and a Block object,based on the hitCode, it sets the corresponding collision flag to true.
   - Setters and getters for various properties in the class.
 
### 4. View.java class

In MVC, the View class is responsible for displaying the user interface and presenting data to user. UI elements such as:

   - Score, heart, level labels
   - ball and game paddle
   - buttons for load, new Game, Save game and Restart game 
   - hide paddle that makes the paddle invisible when hide block is hit, set ball to goldball when goldblock is hit and setScene that set the game scene methods
   - show score increase, level up, heart count decrease UI elements

are all created here in this View class

### 5. bgsound.java class

This is a class created to handle and manage the background music part of the game

### 6. Controller.java class

Controller class is lastly created as a crucial component responsible for handling user control to the game, updating the model, and coordinating the interaction between the model and the view. 

- **Features of Controller class:**
   - User Input Handling
       - The controller captures and processes user input events, including keyboard presses and other GUI interactions.
   - Model-View Coordination
       - Coordinates updates between the data model and the GUI view, ensuring that changes in one component are appropriately reflected in the other.
   - Level Management
        - Manages the progression of game levels, handling level transitions, and initialization.
   - Game State Handling
      - Controls the game state, including pausing and unpausing, responding to game over conditions, and managing game restarts.
   - Physics and Collision Detection
     - Implements physics-related logic for the game ball, handles collisions with the paddle, walls, and game blocks.
   - Bonus, boost and hide block handling
     - Manages the appearance, movement, and effects of bonus items, such as chocos, gold balls, boosts, and more.
   - Game Save 
     - Stores the game parameters of the progress the user chose to save.

# Modified Java Classes

### 1. GameEngine.java class
- In GameEngine class, instead of using Thread type to run the game engine, Timeline is used instead. By modifying it from Thread to Timeline,it resolved the problem of the game becoming lagged and jammed with bunch of error messages popping up in terminal at higher levels when the ball started moving fast and hitting more blocks at a shorter amount of time. However, the physics of the ball's movement had problem, the blocks are not destroyed when the ball hits the blocks and the ball doesn't rebound when it destroyed a block.
- Pause game and unpause game feature are also added into the GameEngine class to implement the feature of pause and unpause.

### 2. Block.java class
- In this Block class, because problems of the ball doesn't rebound when it hits block and the blocks doesn't get destroyed when ball hits it were found, the chechHitToBlock method is modified to fix these problems by changing the rebounding logic.
- Flash Block and Hide Paddle Block are also added and created here. 


### 3. Bonus.java class
- Changed the images of the bonus dropping chocos.

### 4. Score.java class
- Changed game win display method, created a restart button so user gets an option to restart the game even after they won
- Instead of using 'Main main' for the root type, I changed it to 'Pane root' / 'Controller controller' so the root becomes more accessible in other classes and it is more convenient for refactoring. 

### 5. LoadSave.java class
- Added several more fields to store additional information about the game state for the additional features created, such as boostTime, invisibleTime, isPaddleDisssapeared, isFlashstatus variables.

### 6. Main.java class
- The job Main class was doing is now handed over as Controller class's responsibility, Main class now serves as an entry point for our BrickBreaker Game to execute with less than 10 lines of code

``` Java
package brickGame;


public class Main{

    public static void main(String[] args) {
        javafx.application.Application.launch(Controller.class, args);
    }
}
```

# Unexpected Problems

### 1. Loadsave problem

- **Problem Description:**
  - After saving the game progress and loading it back out, the game parameters and blocks doesn't load out as what's saved. This problem occured after I refactored the loadsave method to model class, this problem was only noticed and realised at the end of refactoring stage.

- **Solution:**
  - Deleted the loadsave method in Model class and redone this part loadsave of code again, it was solved at the end and the cause of this problem was probably due to careless mistakes.


### 2. 'You Win' text doesn't display after the user finished level 18

- **Problem Description:**
  - After the user finished level 18 and won the game, 'You Win' text doesn't display after moving the Pane root to view class from Controller Class and using it in View class.

- **Solution:**
  - Changed the location showWinText is called, instead of calling it in controller class like that:

  ```Java
      private void startgame() {
        if (loadFromSave == false) {
            level++;
            if (level > 1) {
                view.showlevelup();
            }
            if (level == 19) {
                view.showWinText();
                return; 
            }

            bball = model.initBall();
            paddle = model.initBreak();
            model.initBoard(blocks,level,isExistHeartBlock);

            view.createbuttons();

        }
    }
  ```

  I call it here in the View class under the setScene Method

  ```Java
      public void setScene(Controller controller,boolean loadFromSave,Model model, ArrayList<Block> blocks, Stage primaryStage,int score,int level,int heart,Rectangle rect,Circle ball){
        root = new Pane();
        scoreLabel = new Label("Score: " + score);
        levelLabel = new Label("Level: " + level);//need to getlevel from controller afterwards
        levelLabel.setTranslateY(20);
        heartLabel = new Label("Heart : " + heart);
        heartLabel.setTranslateX(500 - 70);
        if (loadFromSave == false && level!=19) {
            root.getChildren().addAll(rect,ball, scoreLabel, heartLabel, levelLabel, newGame, load);
        } else if (level != 19) {
            root.getChildren().addAll(rect,ball, scoreLabel, heartLabel, levelLabel);
        }
        else
        {
            showWinText(controller); //called here 
        }
        for (Block block : blocks) {
            root.getChildren().add(block.rect);
        }

        Scene scene = new Scene(root, 500, 700);
        scene.getStylesheets().add("style.css");
        scene.setOnKeyPressed(controller);

        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
    }
  ```

  ### 3. Ball doesn't rebound when it hits the bottom left corner edge

- **Problem Description:**
  - Even though the block will be destroyed when the ball hits it at its bottom left corner edge, but the it doesn't give the ball a rebounding effect

- **Solution:**
  - Added a new rebounding logic method under setphyicstoball so it detects and create a rebounding effect to the ball when the ball hits the block's bottom left edge corner. 
  


