# Maze Solver
Maze Solver made for Programming Studio Course

# Description
The purpose of this assignment was to teach students:
- MVC coding style
- JUnit Testing
- Test Driven Development (TDD)
- Swing library for GUI development in Java

Upon starting the program, the default maze is set in the application. In the default maze, there is a starting point in the top-left corner and an end point at the bottom-right corner. 

To load a new maze from a .bmp image file, such as the example in the repository, click on File > Load Maze, and find the file to load. In the application window, there is a grey scale threshold that determines what colors the solved path can and cannot walk through. Assuming the colors are in an RGB representation, it is determined by this simple formula:

  ```
  if R+G+B > threshold, pixel is unwalkable
  ```

Once the maze has been set, clicking the Maze option in the menu bar will reveal several options to change the behavior of the maze solver.
- **Set Start Location** - Once pressed, the user can click on a location in the maze window to set the location of the start point. It will be signified by a small green pixel.
- **Set End Location** - Much like the Set Start Location button, it will allows the ending location to be set. Once a location has been chosen, it will be signified by a small red pixel
- **Check If Solvable** - Starting from the start point, the application will run a Depth-First Search to check if the maze is solvable notify the user of the results.
- **Solve Maze** - Starting from the start point, the application will run the chosen search algorithm and show the path as a red line between the start point and the end point.
- **Reset Maze** - This option will remove the path, if any is shown, and reset the maze to its initial state.
- **Solvers** - This option allows the user to choose between two search algorithms to solve the maze with: A* and Dijkstra's
- **Heursitics** - This option allows the user to choose betweeen different heuristics that the maze solvers can use.
  + Manhattan - For any two 2D points, the Manhattan distance is defined as the sum of the absolute difference between both x and y values
  + Manattan With Tie Break - Similar to the Manhattan distance, except the values are scaled up slightly to break ties between different paths that have similar path costs
  + Diagonal Distance - Uses an estimation of the diagonal distance between two points. Using the actual diagonal or Euclidean distance involves a costly square root that is not necessary
  
Once a maze has been solved, it can be saved by selecting File > Save Maze.
