package solver;

import java.util.Stack;

import maze.Maze;
import maze.MazeCell;


public interface Solver 
{
	
	/**
	 * Solves maze using specified algorithm
	 * 
	 * @param maze - the maze
	 * @return shortest path end square on bottom of stack
	 */
	public Stack<MazeCell> solve(Maze maze);
}
