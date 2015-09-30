package solver;
import heuristics.Heuristics;

import java.awt.Point;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Stack;

import maze.Maze;
import maze.MazeCell;


public class aStar implements Solver
{
	PriorityQueue<MazeCell> openList;
	Hashtable<Point, MazeCell> closedList;
	
	// default constructor
	public aStar()
	{
		openList = new PriorityQueue<MazeCell>();
		closedList = new Hashtable<Point, MazeCell>();
	}
	
	@Override
	public Stack<MazeCell> solve(Maze maze)
	{		
		MazeCell start = maze.getStart();
		MazeCell end = maze.getEnd();
		
		openList.offer(start);
		
		while(!openList.contains(end) && !openList.isEmpty())
		{
			MazeCell current = openList.poll();
			closedList.put(current.getPosition(), current);
			
			// for all valid neighbors of the current maze cell
			for(MazeCell mc: maze.getNeighbors(current))
			{
				if(!mc.isWalkable() || closedList.containsKey(mc.getPosition()))
					continue;
				
				/*
				 * If neighbor is not on open list:
				 *   add it to open list
				 *   set current cell as its parent
				 *   calculate and set the movement cost and distance to end
				 */
				if(!openList.contains(mc))
				{
					mc.setParent(current);
					
					Heuristics h = maze.getHeuristics();
					mc.setMovementCost(h.moveCost(mc));
					mc.setDistanceToEnd(h.distToEnd(mc, end));
					
					openList.add(mc);
				}
				/*
				 * If neighbor in open list:
				 *    compare the old movement cost to
				 *    the cost of moving to the current square.
				 *    if it's less, set the current square as the parent
				 */
				else
				{
					MazeCell oldParent = mc.getParent();
					int oldMoveCost = mc.getMovementCost();
					
					Heuristics h = maze.getHeuristics();
					mc.setParent(current);
					mc.setMovementCost(h.moveCost(mc));
					
					if(mc.getMovementCost() > oldMoveCost)
					{
						mc.setParent(oldParent);
						mc.setMovementCost(oldMoveCost);
					}
				}
			}
			//end for
		}
		// end while
		
		// converts the minimal path as a stack consisting of maze cells
		Stack<MazeCell> ret = new Stack<MazeCell>();
		MazeCell parent = end.getParent();
		
		while(parent != null)
		{
			ret.push(parent);
			parent = parent.getParent();
		}
		
		return ret;
	}
}
