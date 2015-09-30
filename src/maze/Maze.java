package maze;
import heuristics.Heuristics;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Stack;
import java.util.Vector;

import solver.Solver;

public class Maze 
{
	private MazeCell[][] maze;
	private Dimension size;
	private MazeCell start, end;
	private Heuristics heuristics;
	private Solver solver;
	
	
	/**
	 * Constructor
	 * 
	 * @param dim - Dimensions of Maze
	 * @param inter - Interface to interact with
	 * @param heur - Heuristics to use
	 * @param algorithm - Maze Solving Agorithm to use
	 */
	public Maze(Dimension dim, Heuristics heur, Solver algorithm)
	{
		size = dim;
		setUpMaze();
		
		heuristics = heur;
		solver = algorithm;
		
		int width = dim.width;
		int height = dim.height;
		
		start = maze[0][0];
		end = maze[width-1][height-1];
		colorStartEnd();
	}

/*            Methods                   */
	
	/**
	 * Set up maze and fill with blank mazeCells
	 */
	private void setUpMaze()
	{
		int width = size.width;
		int height = size.height;
		
		maze = new MazeCell[width][height];
		
		for(int r = 0; r<width; r++)
			for(int c = 0; c<height; c++)
				maze[r][c] = new MazeCell(new Point(r, c));
	}
	
	/**
	 * Color start and end points
	 */
	private void colorStartEnd()
	{
		start.setColor(Colors.START);
		end.setColor(Colors.END);
	}
	
	/**
	 * Check whether maze is solvable using DFS
	 * 
	 * @return whether maze is solvable or not
	 */
	public boolean solvable()
	{
		boolean[][] checked = new boolean[size.width][size.height];
		
		Stack<MazeCell> search = new Stack<MazeCell>();
		search.push(start);
		
		while(!search.isEmpty())
		{
			MazeCell m = search.pop();
			for(MazeCell mc: getNeighbors(m))
			{
				if(mc == end)
					return true;
				
				int x = mc.getX();
				int y = mc.getY();
				
				if(!checked[x][y])
				{
					search.push(mc);
					checked[x][y] = true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Solves maze by invoking the maze solver's solve method
	 * If no path is found, it is printed out to the interface
	 * otherwise, the path is painted out in red 
	 */
	public Stack<MazeCell> solveMaze()
	{
		return solver.solve(this);
	}
	
	/**
	 * flips square from walkable to unwalkable and vice versa
	 * 
	 * @param square - the MazeCell to flip
	 */
	public void flipCell(MazeCell square)
	{		
		if(square.isWalkable())
			setUnwalkable(square);
		else
			setWalkable(square);
	}
	
	/**
	 * Set's a square as walkable
	 * 
	 * @param square - maze cell to set as walkable
	 */
	public void setWalkable(MazeCell square)
	{
		if(square == start || square == end)
			return;
		
		square.setWalkable(true);
		square.setColor(Colors.WALK);
	}
	
	/**
	 * Sets the specified square as unwalkable
	 * 
	 * @param square - maze cell to set as unwalkable
	 */
	public void setUnwalkable(MazeCell square)
	{
		if(square == start || square == end)
			return;
		
		square.setWalkable(false);
		square.setColor(Colors.UNWALK);
	}
	
	/**
	 * Resets all cells to initial state
	 */
	public void resetCells()
	{
		int width = size.width;
		int height = size.height;
		
		for(int x = 0; x<width; x++)
			for(int y = 0; y<height; y++)
			{
				MazeCell mc = maze[x][y];
				if(mc.getColor() == Colors.PATH)
					mc.setColor(Colors.WALK);
			}
		colorStartEnd();
	}
	
	/**
	 * returns all neighbors of target cell, not including walls
	 * 
	 * @param target, target cell who's neighbors we want
	 * @return list of target's 8 neighbors
	 */
	public Vector<MazeCell> getNeighbors(MazeCell target)
	{
		Vector<MazeCell> neighbors = new Vector<MazeCell>();
		
		int xPos = target.getX();
		int yPos = target.getY();
		
		for(int x = -1; x<2; x++)
			for(int y = -1; y<2; y++)
			{
				if(neighborCheck(xPos+x, yPos+y, target))
					neighbors.add(maze[xPos+x][yPos+y]);
			}
		
		return neighbors;
	}
	
	/**
	 * Checks that target's neighbor at (xPos, yPos) is not a wall and is in maze
	 * 
	 * @param xPos - xPos of neighbor
	 * @param yPos - yPos of neighbor
	 * @param target - target square who's neighbor is at (xPos, yPos)
	 * @return whether target's neighbor at (xPos, yPos) is walkable/valid
	 */
	private boolean neighborCheck(int xPos, int yPos, MazeCell target)
	{
		if(isValid(xPos, yPos) && maze[xPos][yPos] != target)
			if(maze[xPos][yPos].isWalkable())
				return true;
		
		return false;
	}
	
	/**
	 * Checks to see if a square is valid
	 * 
	 * @param x, the x-position of the square
	 * @param y, the y-position of the square
	 * @return boolean, whether the cell is in bounds or not
	 */
	private boolean isValid(int x, int y)
	{
		int width = size.width;
		int height = size.height;
		
		if(x >= 0 && x < width)
			if(y >= 0 && y < height)
				return true;
		return false;
	}
	
/*       Getters and Setters            */	
	
	/**
	 * @return target maze cell
	 */
	public MazeCell getCell(int x, int y)
	{
		if(0 <= x && x < size.width)
			if(0 <= y && y < size.height)	
				return maze[x][y];
		return null;
	}
	
	/**
	 * @return the width of maze 
	 */
	public int getWidth(){return size.width;}
	
	/**
	 * @return the height of maze
	 */
	public int getHeight(){return size.height;}
	
	
	/**
	 * Resizes the array and resets every cell
	 * 
	 * @param width - width of new maze
	 * @param height - height of new maze
	 */
	public void setSize(int width, int height)
	{
		size = new Dimension(width, height);
		
		maze = new MazeCell[width][height];
		for(int x = 0; x<width; x++)
			for(int y = 0; y<height; y++)
				maze[x][y] = new MazeCell(new Point(x, y));
		
		start = maze[0][0];
		end = maze[width-1][height-1];
	}

	/**
	 * @return the start
	 */
	public MazeCell getStart(){return start;}

	/**
	 * @param start, the value to set start to
	 */
	public void setStart(MazeCell start)
	{
		MazeCell oldStart = this.start;
		
		if(getNeighbors(oldStart).size() < 2)
		{
			oldStart.setWalkable(false);
			oldStart.setColor(Colors.UNWALK);
		}
		else
		{
			oldStart.setWalkable(true);
			oldStart.setColor(Colors.WALK);
		}
		
		start.setWalkable(true);
		start.setColor(Colors.START);
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public MazeCell getEnd(){return end;}

	/**
	 * @param end, the value to set end to
	 */
	public void setEnd(MazeCell end)
	{
		MazeCell oldEnd = this.end;
		
		if(getNeighbors(oldEnd).size() < 2)
		{
			oldEnd.setWalkable(false);
			oldEnd.setColor(Colors.UNWALK);
		}
		
		end.setWalkable(true);
		end.setColor(Colors.END);
		this.end = end;
	}
	
	/**
	 * @return heuristics, the heuristics object of this maze
	 */
	public Heuristics getHeuristics(){return heuristics;}
	
	/**
	 * @param h - the heuristics this maze will use
	 */
	public void setHeuristics(Heuristics h){heuristics = h;}
	
	/**
	 * @return the particular solver object this maze is using
	 */
	public Solver getSolver(){return solver;}
	
	/**
	 * @param s - the solver that this maze will use
	 */
	public void setSolver(Solver s){solver = s;}
}
