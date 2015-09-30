package heuristics;
import maze.MazeCell;


public class ManhattanDistance implements Heuristics
{

	@Override
	public int moveCost(MazeCell current)
	{
		if(current.getParent() == null)
			return 0;
		
		MazeCell parent = current.getParent();
		int dx = Math.abs(current.getX() - parent.getX());
		int dy = Math.abs(current.getY() - parent.getY());
		
		int cost = parent.getMovementCost();
		
		if(dx == 1 && dy == 1)
			cost += 141;
		else
			cost += 100;
		
		return cost;
	}

	@Override
	public int distToEnd(MazeCell current, MazeCell end)
	{
		int dr = Math.abs(current.getX() - end.getX());
		int dc = Math.abs(current.getY() - end.getY());
		
		return dr+dc;
	}
	
}
