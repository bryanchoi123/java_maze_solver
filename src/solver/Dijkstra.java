package solver;

import java.awt.Point;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Stack;

import maze.Maze;
import maze.MazeCell;


public class Dijkstra implements Solver, Comparator<MazeCell>
{
	PriorityQueue<MazeCell> distances;
	Hashtable<Point, MazeCell> unvisitedSet;
	
	// default constructor
	public Dijkstra()
	{
		distances = new PriorityQueue<MazeCell>(11, this);
		unvisitedSet = new Hashtable<Point, MazeCell>();
	}
	
	@Override
	public Stack<MazeCell> solve(Maze maze)
	{		
		initialize(maze);
		
		MazeCell end = maze.getEnd();
		MazeCell current = distances.poll();
		while(!end.isVisited() && current.getMovementCost() != Integer.MAX_VALUE)
		{
			for(MazeCell mc: maze.getNeighbors(current))
			{
				MazeCell oldParent = mc.getParent();
				mc.setParent(current);
				
				int newCost = calcDist(mc);
				if(newCost < mc.getMovementCost())
				{
					mc.setMovementCost(newCost);
					
					if(distances.remove(mc))
						distances.offer(mc);
				}
				else
					mc.setParent(oldParent);
			}
			
			current.setVisited(true);
			unvisitedSet.remove(current.getPosition());
			
			current = distances.poll();
		}
		
		Stack<MazeCell> path = new Stack<MazeCell>();
		MazeCell parent = end.getParent();
		while(parent != null)
		{
			path.push(parent);
			parent = parent.getParent();
		}
		
		return path;
	}
	
	/**
	 * Initialize data structures for Dijkstra's Algorithm
	 * 
	 * @param maze - the Maze that this Dijkstra is the solver for
	 */
	private void initialize(Maze maze)
	{
		int height = maze.getHeight();
		int width = maze.getWidth();
		
		for(int x = 0; x<width; x++)
			for(int y = 0; y<height; y++)
			{				
				MazeCell square = maze.getCell(x, y);
				square.setMovementCost(Integer.MAX_VALUE);
				unvisitedSet.put(square.getPosition(), square);
				distances.offer(square);
			}
		
		MazeCell start = maze.getStart();
		start.setMovementCost(0);
		
		if(distances.remove(start))
			distances.offer(start);
	}
	
	/**
	 * Calculates the movement cost from start to the current cell
	 * if moving diagonal from current's parent to current, the cost is 141 + the parent's cost
	 * if moving straight up/down or left/right, the cost is 100 + the parent's cost
	 * 
	 * @param current - MazeCell to calculate the distance cost of
	 * @return the distance cost from the start cell to current
	 */
	private int calcDist(MazeCell current)
	{
		MazeCell parent = current.getParent();
		
		int dr = Math.abs(current.getX() - parent.getX());
		int dc = Math.abs(current.getY() - parent.getY());
		
		int cost = parent.getMovementCost();
		
		if(dr == 1 && dc == 1)
			 cost += 141;
		else
			cost += 100;
		
		return cost;
	}

	@Override
	public int compare(MazeCell arg0, MazeCell arg1)
	{
		return arg0.getTotalCost() - arg1.getTotalCost();
	}
}
