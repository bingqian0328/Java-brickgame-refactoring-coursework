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

In this game, two new types of blocks are created,Flash Block and Hide Paddle Block. It adds strategic depth and surprises to the gameplay. These blocks bring unique effects that can significantly impact the course of the game.

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

In this game, additional new level of level 18 is added to increase the difficulty of the game. 

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
  
 - **Not working properly:** Even though the problem can be considered solved, but sometimes when the ball barely hits and slides past the block,it destroys the block but it doesn't create a rebound effect to the ball.

```Java
   public void handleBottomLeftBlockCorner(Ball bball, Paddle paddle) {
        double cornerDistance = Math.sqrt(Math.pow(bball.getXb() - paddle.getX(), 2) + Math.pow(bball.getYb() - paddle.getY() - paddle.getHeight(), 2));
        if (cornerDistance <= bball.getRadius()) {
            bball.bounceHorizontally(); //ball go left
            bball.bounceDown(); //ball go down
        }
    }
```
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





 



