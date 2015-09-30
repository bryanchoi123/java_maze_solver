import static org.junit.Assert.*;

import java.awt.Dimension;
import java.util.Stack;

import heuristics.ManhattanDistance;

import maze.Maze;
import maze.MazeCell;

import org.junit.Before;
import org.junit.Test;

import solver.Dijkstra;
import solver.aStar;


public class testSolvers
{
	Maze testMaze;
	
	@Before
	public void setUp() throws Exception
	{
		testMaze = new Maze(new Dimension(3,3), new ManhattanDistance(), new aStar());
	}

	@Test
	public void testaStar()
	{
		Stack<MazeCell> solution = testMaze.solveMaze();
		
		MazeCell mc = testMaze.getCell(1, 1);
		assertTrue(solution.contains(mc));
	}
	
	@Test
	public void testaStarNoSolution()
	{
		for(int i = 0; i<3; i++)
		{
			MazeCell mc = testMaze.getCell(1, i);
			testMaze.setUnwalkable(mc);
		}
		
		Stack<MazeCell> solution = testMaze.solveMaze();
		assertTrue(solution.isEmpty());
	}
	
	@Test
	public void testDijkstra()
	{
		testMaze.setSolver(new Dijkstra());
		
		Stack<MazeCell> solution = testMaze.solveMaze();
		
		MazeCell mc = testMaze.getCell(1, 1);
		assertTrue(solution.contains(mc));
	}
	
	@Test
	public void testDijkstraNoSolution()
	{
		testMaze.setSolver(new Dijkstra());
		
		for(int i = 0; i<3; i++)
		{
			MazeCell mc = testMaze.getCell(1, i);
			testMaze.setUnwalkable(mc);
		}
		
		Stack<MazeCell> solution = testMaze.solveMaze();
		assertTrue(solution.isEmpty());
	}
}
