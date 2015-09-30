package heuristics;
import maze.MazeCell;


public interface Heuristics
{

	/**
	 * Calculate the cost to move from start cell to current cell
	 * 
	 * @param current - the MazeCell you are currently at
	 * @return movement cost, cost of traveling to current cell
	 */
	public int moveCost(MazeCell current);
	
	/**
	 * Calculates the estimated cost to get to the end
	 * 
	 * @param current - current cell
	 * @param end - end of the maze
	 * @return estimated heuristic cost to the end
	 */
	public int distToEnd(MazeCell current, MazeCell end);
	
}
