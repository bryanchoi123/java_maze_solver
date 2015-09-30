package heuristics;

import maze.MazeCell;

public class ManhattanTieBreak extends ManhattanDistance
{
	int p = 1/1000;
	
	@Override
	public int distToEnd(MazeCell current, MazeCell end)
	{
		int dr = Math.abs(current.getX() - end.getX());
		int dc = Math.abs(current.getY() - end.getY());
		
		return (int)((dr+dc)*(1+p));
	}
}
