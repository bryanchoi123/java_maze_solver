import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import heuristics.ManhattanDistance;

import java.awt.Dimension;
import java.util.Vector;

import maze.Maze;
import maze.MazeCell;

import org.junit.Before;
import org.junit.Test;

import solver.aStar;


public class testMaze
{
	static MazeCell testCell;
	static Maze testMaze, nullMaze;
	static int size;
	
	@Before
	public void initialize()
	{	
		size = 10;
		Dimension dim = new Dimension(size,size);
		ManhattanDistance man = new ManhattanDistance();
		aStar solve = new aStar();
		
		testMaze = new Maze(dim, man, solve);
		testCell = testMaze.getCell(4, 4);
	}
	
	@Test
	public void flipCell()
	{
		boolean stateBefore = testCell.isWalkable();
		
		testMaze.flipCell(testCell);
		
		assertFalse(stateBefore == testCell.isWalkable());
	}
	
	@Test
	public void testReset()
	{
		testMaze.flipCell(testMaze.getCell(1, 0));
		testMaze.flipCell(testMaze.getCell(2, 0));
		testMaze.flipCell(testMaze.getCell(2, 2));
		
		testMaze.resetCells();
		
		assertFalse(testMaze.getCell(1, 0).isWalkable());
		assertFalse(testMaze.getCell(2, 0).isWalkable());
		assertFalse(testMaze.getCell(2, 2).isWalkable());
	}
	
	@Test
	public void testNeighbors()
	{
		Vector<MazeCell> neighbors = testMaze.getNeighbors(testCell);
		
		assertTrue(neighbors.contains(testMaze.getCell(3, 3)));
		assertTrue(neighbors.contains(testMaze.getCell(3, 4)));
		assertTrue(neighbors.contains(testMaze.getCell(3, 5)));
		
		assertTrue(neighbors.contains(testMaze.getCell(4, 3)));
		assertTrue(neighbors.contains(testMaze.getCell(4, 5)));
		
		assertTrue(neighbors.contains(testMaze.getCell(5, 3)));
		assertTrue(neighbors.contains(testMaze.getCell(5, 4)));
		assertTrue(neighbors.contains(testMaze.getCell(5, 5)));
	}
	
}
